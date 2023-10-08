package cn.stkit.wxl.blogadmin.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file FileUploadService
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/8/7 2:49 下午
 */

public interface FileUploadService
{
    /**
     * 文件上传处理
     * @param file 上传的文件
     * @param path 存储文件的路径
     * @param newFileName 上传成功后新文件名，如果新文件名与现有文件重复，则自动生成随机文件名。
     * @return 返回文件的相对或者绝对路径
     */
    String upload(MultipartFile file, String path, String newFileName);
}
