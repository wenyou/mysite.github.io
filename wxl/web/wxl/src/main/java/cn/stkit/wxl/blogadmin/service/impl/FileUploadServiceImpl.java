package cn.stkit.wxl.blogadmin.service.impl;

import cn.stkit.wxl.blogadmin.service.FileUploadService;
import cn.stkit.wxl.utils.RandomUtil;
import cn.stkit.wxl.utils.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file FileUploadServiceImpl
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/8/7 2:52 下午
 */
@Service
public class FileUploadServiceImpl implements FileUploadService
{
    private static final Logger logger = LoggerFactory.getLogger(FileUploadService.class);

    @Override
    public String upload(MultipartFile file, String path, String newFileName)
    {
        String fileName = file.getOriginalFilename();
        String extensionName = fileName.substring(fileName.lastIndexOf(".")+1);

        String uploadFileName = RandomUtil.getAlphanumeric(6) + "." + extensionName;

        if(!ValidateUtil.isEmptyString(newFileName)) {
            File dupName = new File(path+newFileName);
            if(!dupName.exists()) {//同名文件不存在，则使用自定义的文件名
                uploadFileName = newFileName + "." + extensionName;
            }
        }

        logger.info("开始上传文件，\t文件原名：{}，上传路径：{}，新文件名：{}", fileName, path, uploadFileName);

        File uploadDir = new File(path);
        if(!uploadDir.exists()) {
            logger.info("上传路径不存在，创建路径。");
            uploadDir.setWritable(true);
            boolean createDirResult = uploadDir.mkdirs();
            logger.info("文件的读权限：{}，文件的写权限：{}，创建结果：{}", uploadDir.canRead(), uploadDir.canWrite(),createDirResult);
        }
        File targetFile = new File(uploadDir, uploadFileName);
        try{
            file.transferTo(targetFile);
        } catch (IOException e) {
            logger.error("文件上传异常：", e);
            return null;
        }
        logger.info("文件路径：{}，文件名：{}", targetFile.getAbsolutePath(), targetFile.getName());
        return targetFile.getName();
    }

}