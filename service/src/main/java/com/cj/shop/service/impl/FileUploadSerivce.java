package com.cj.shop.service.impl;

import com.cj.shop.api.entity.FileUpload;
import com.cj.shop.dao.mapper.FileUploadMapper;
import com.cj.shop.service.util.ResultMsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文件上传中台服务
 *
 * @author yuchuanWeng
 * @date 2018/6/6
 * @since 1.0
 */
@Transactional
@Service
public class FileUploadSerivce {
    @Autowired
    private FileUploadMapper fileUploadMapper;


    /**
     * 添加文件上传记录
     *
     * @param fileUpload
     */
    public int addRecord(FileUpload fileUpload) {
        return fileUploadMapper.addRecord(fileUpload);
    }

    public FileUpload selectByFileUrl(String url, Long uid) {
        return fileUploadMapper.selectByFileUrl(url, uid);
    }


    public String deleteByFileId(String fileUrl, Long uid) {
        FileUpload fileUpload = selectByFileUrl(fileUrl, uid);
        if (fileUpload == null) {
            return "文件不存在";
        }
        int i = fileUploadMapper.deleteByFileId(fileUpload.getFileId());
        return ResultMsgUtil.dmlResult(i);
    }
}
