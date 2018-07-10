package com.cj.shop.web.utils;

import com.cj.shop.web.consts.ResultConsts;
import com.cj.shop.web.dto.Result;
import org.lokra.seaweedfs.core.FileSource;
import org.lokra.seaweedfs.core.FileTemplate;
import org.lokra.seaweedfs.core.file.FileHandleStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * SeaweedFS 上传工具类
 *
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/4/3
 * @since 1.0
 */
@Component
public class SeaweedFSUtil {
    /**
     * DFS IP:PORT
     */
    @Value("${seaweed.ip}")
    private String ip;
    @Value("${seaweed.port}")
    private Integer port;

    private static FileSource fileSource;
    private static final String FILE_URL = "file_url";
    private static final String FILE_ID = "file_id";
    private static final int MAX_NAME_LENGTH = 32;
    public static final long FILE_SIZE_4M = 1048576 << 2;
    public static final long FILE_SIZE_8M = 1048576 << 3;
    private static final Logger logger = LoggerFactory.getLogger(SeaweedFSUtil.class);


    private void initialConfig() throws IOException {
        fileSource.setHost(ip);
        fileSource.setPort(port);
        fileSource.startup();
    }

    public FileSource initialFileSource() throws IOException {
        if (fileSource == null) {
            fileSource = new FileSource();
            initialConfig();
        }
        return fileSource;
    }

    /**
     * 上传文件流到服务器
     *
     * @param fileName
     * @param in
     * @throws IOException
     */
    public Map<String, String> uploadFile(String fileName, InputStream in) throws IOException {
        initialFileSource();
        if (fileName.length() > MAX_NAME_LENGTH) {
            String suffix = fileName.substring(fileName.lastIndexOf(",") + 1);
            fileName = fileName.substring(0, MAX_NAME_LENGTH) + suffix;
        }
        Map<String, String> resultMap = new HashMap<>();
        fileName = fileName.replaceAll(" ", "");
        FileTemplate template = new FileTemplate(fileSource.getConnection());
        FileHandleStatus fileHandleStatus = template.saveFileByStream(fileName, in);
        String fileUrl = template.getFileUrl(fileHandleStatus.getFileId());
        logger.debug("SeaweedFSUtil.uploadFile FILE_URL={}", fileUrl);
        resultMap.put(this.FILE_URL, fileUrl.replace(",", "/") + "/" + fileName);
        resultMap.put(this.FILE_ID, fileHandleStatus.getFileId());
        return resultMap;
    }


    public void deleteFile(String fileId) throws IOException {
        FileTemplate template = new FileTemplate(fileSource.getConnection());
        template.deleteFile(fileId);
    }


    /**
     * 不合法的文件格式返回Result
     *
     * @return
     */
    public Result invaildFileFormatResult() {
        Result result = new Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.UPLOAD_FAILURE);
        result.setData(ResultConsts.UPLOAD_TYPE_FAILURE);
        return result;
    }


}
