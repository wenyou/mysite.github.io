package cn.stkit.wxl.blogadmin.controller;

import cn.stkit.wxl.blog.util.FileUtil;
import cn.stkit.wxl.blogadmin.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file UploadController
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/8/6 11:40 上午
 */
@Controller
@RequestMapping("/blogAdmin/upload")
public class UploadController
{
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/singleImage")
    @ResponseBody
    public Map<String, String> singleImage(MultipartFile file, HttpServletRequest request)
    {
        String path = FileUtil.getUploadImagePath();
        logger.info("文件存储路径：{}", path);
        String imageUrl = fileUploadService.upload(file, path, null);

        Map<String, String> map = new HashMap<>();
        map.put("location", imageUrl);
        return map;
    }

    @PostMapping("/multiImages")
    @ResponseBody
    public String multiImages()
    {
        System.out.println("====multiImages===");
        return null;
    }
}
