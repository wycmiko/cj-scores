package com.cj.shop.web.controller;

import com.cj.shop.api.entity.FileUpload;
import com.cj.shop.service.impl.FileUploadSerivce;
import com.cj.shop.web.consts.ResultConsts;
import com.cj.shop.web.dto.Result;
import com.cj.shop.web.utils.IPAddressUtil;
import com.cj.shop.web.utils.SeaweedFSUtil;
import com.cj.shop.web.validator.CommandValidator;
import com.cj.shop.web.validator.TokenValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 文件上传Controller
 *
 * @author yuchuanWeng
 * @date 2018/4/4
 * @since 1.0
 */
@Api(tags = "文件上传服务")
@Slf4j
@RestController
@RequestMapping({"/v1/mall/manage", "/v1/mall/json"})
public class FileUploadController {
    @Autowired
    private SeaweedFSUtil seaweedFSUtil;
    @Autowired
    private TokenValidator tokenValidator;
    @Autowired
    private FileUploadSerivce fileUploadSerivce;


    @ApiOperation(value = "文件上传服务", notes = "Post请求 form-data，文件上传，需要token校验 返回上传后的文件路径", response = Map.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件上传框"),
            @ApiImplicitParam(name = "token", value = "校验的token")
    })
    @PostMapping("/uploadFile")
    public Result uploadFile(@RequestParam("file") MultipartFile file, String token, HttpServletRequest request) {
        //构造返回对象
        Result result = null;
        FileUpload fileUpload = null;
        try {
            log.info("FileUploadController.uploadFile begin");
            if (CommandValidator.isEmpty(token)) {
                return CommandValidator.paramEmptyResult();
            }
            //token校验
            if (!tokenValidator.checkToken(token)) {
                log.info("FileUploadController.uploadFile 【Invaild token!】");
                return tokenValidator.invaildTokenFailedResult();
            }
            log.info("fileSize={}", file.getSize());
            if (file.getSize() >= SeaweedFSUtil.FILE_SIZE_8M) {
                return new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.UPLOAD_SIZE_FAILURE);
            }
            if (file != null) {
                String fileName = file.getOriginalFilename();
                String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                boolean validFormat = !"".equals(suffix) && (ResultConsts.UPLOAD_JPG.equals(suffix) || ResultConsts.UPLOAD_JPEG.equals(suffix)
                        || ResultConsts.UPLOAD_PNG.equals(suffix) || ResultConsts.UPLOAD_GIF.equals(suffix));
                if (!validFormat) {
                    return seaweedFSUtil.invaildFileFormatResult();
                }
                Map<String, String> fileNameMap = seaweedFSUtil.uploadFile(fileName, file.getInputStream());
                log.debug("FileUploadController.uploadFile fileName:{} suffix:{}", fileName, suffix);
                //封装返回数据
                result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.UPLOAD_SUCCESS);
                result.setData(fileNameMap);
                insertRecord(fileUpload, fileName, suffix, token, request, fileNameMap, 1, file.getSize());
                log.info("FileUploadController.uploadFile end");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //异常情况
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.UPLOAD_FAILURE);
            result.setData(ResultConsts.UPLOAD_SIZE_FAILURE);
            log.error("FileUploadController.uploadFile error:{}", e.getMessage());
        }
        return result;
    }


    @ApiOperation(value = "文件上传服务-PC", notes = "Post请求 form-data，文件上传PC端，返回上传后的文件路径", response = Map.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件上传框")
    })
    @PostMapping("/uploadFilePc")
    public Result uploadFilePc(@RequestParam("file") MultipartFile file,
                               HttpServletRequest request) {
        //构造返回对象
        Result result = null;
        FileUpload fileUpload = null;
        try {

            log.info("FileUploadController.uploadFilePc begin");
            log.info("fileSize={}", file.getSize());
            if (file.getSize() >= SeaweedFSUtil.FILE_SIZE_8M) {
                return new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.UPLOAD_SIZE_FAILURE);
            }
            if (file != null) {
                String fileName = file.getOriginalFilename();
                String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                boolean validFormat = !"".equals(suffix) && (ResultConsts.UPLOAD_JPG.equals(suffix) || ResultConsts.UPLOAD_JPEG.equals(suffix)
                        || ResultConsts.UPLOAD_PNG.equals(suffix) || ResultConsts.UPLOAD_GIF.equals(suffix) || ResultConsts.UPLOAD_TXT.equals(suffix));
                if (!validFormat) {
                    return seaweedFSUtil.invaildFileFormatResult();
                }
                log.debug("FileUploadController.uploadFilePc fileName:{} suffix:{}", fileName, suffix);
                Map<String, String> fileNameMap = seaweedFSUtil.uploadFile(fileName, file.getInputStream());
                //封装返回数据
                result = new Result(ResultConsts.REQUEST_SUCCEED_STATUS, ResultConsts.UPLOAD_SUCCESS);
                result.setData(fileNameMap);
                insertRecord(fileUpload, fileName, suffix, null, request, fileNameMap, 2, file.getSize());
                log.info("FileUploadController.uploadFilePc end");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //异常情况
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.UPLOAD_FAILURE);
            result.setData(e.getMessage());
            log.error("FileUploadController.uploadFilePc error:{}", e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/deleteFile")
    public Result deleteFile(String fileId) {
        //构造返回对象
        Result result = null;
        FileUpload fileUpload = null;
        try {
            log.info("deleteFile.uploadFilePc begin");
            seaweedFSUtil.deleteFile(fileId);
        } catch (Exception e) {
            e.printStackTrace();
            //异常情况
            result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.UPLOAD_FAILURE);
            result.setData("文件不存在");
            log.error("FileUploadController.uploadFilePc error:{}", e.getMessage());
        }
        return result;
    }

    private int insertRecord(FileUpload fileUpload, String fileName, String suffix, String token, HttpServletRequest request
            , Map<String, String> fileNameMap, int role, long fileSize) throws Exception {
        if (fileUpload == null) fileUpload = new FileUpload();

        fileUpload.setFileName(fileName);
        fileUpload.setFileType(suffix);
        if (!StringUtils.isEmpty(token)) {
            fileUpload.setUid(tokenValidator.getUidByToken(token));
        }
        fileUpload.setReqIp(IPAddressUtil.getIpAddressNotInProxy(request));
        fileUpload.setRole((short) role);
        fileUpload.setFileUrl(fileNameMap.get("file_url"));
        fileUpload.setFileId(fileNameMap.get("file_id"));
        fileUpload.setFileSize(fileSize);
        int i = fileUploadSerivce.addRecord(fileUpload);
        log.info("insert into fileupload table {}", i > 0);
        return i;
    }
}
