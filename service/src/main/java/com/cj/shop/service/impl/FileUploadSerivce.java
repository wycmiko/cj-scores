package com.cj.shop.service.impl;

import com.cj.shop.api.entity.FileUpload;
import com.cj.shop.dao.mapper.FileUploadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文件上传中台服务
 * @author yuchuanWeng
 * @date 2018/6/6
 * @since 1.0
 */
@Service
public class FileUploadSerivce implements FileUploadMapper {
    @Autowired
    private FileUploadMapper fileUploadMapper;


    /**
     * 添加文件上传记录
     *
     * @param fileUpload
     */
    @Override
    public int addRecord(FileUpload fileUpload) {
        return fileUploadMapper.addRecord(fileUpload);
    }
}
