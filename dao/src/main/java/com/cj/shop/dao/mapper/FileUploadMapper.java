package com.cj.shop.dao.mapper;


import com.cj.shop.api.entity.FileUpload;
import org.apache.ibatis.annotations.Mapper;

/**
 * mybatis自动生成Mapper
 *
 * @author yuchuanWeng
 * @version 1.0
 * @date 2018-06-02
 */
@Mapper
public interface FileUploadMapper {
    /**
     * 添加文件上传记录
     */
    int addRecord(FileUpload fileUpload);

}