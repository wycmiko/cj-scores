package com.cj.shop.api.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件上传实体
 * @author yuchuanWeng
 * @date 2018/6/5
 * @since 1.0
 */
@Data
public class FileUpload implements Serializable {
    private Long id;
    private Long uid;
    private String fileType;
    private String fileName;
    private String fileId;
    private String fileUrl;
    private Long fileSize;
    private String reqIp;
    private Short role;
    private String postTime;
}
