package cn.stkit.wxl.blogadmin.controller;

import cn.stkit.wxl.blog.util.FileUtil;
import cn.stkit.wxl.blogadmin.constants.Constants;
import cn.stkit.wxl.blogadmin.service.FileUploadService;
import cn.stkit.wxl.utils.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file ResourceController
 * @desc 资源管理
 * @website http://www.stkit.cn/
 * @date 2020/8/13 5:25 下午
 */
@Controller
@RequestMapping("/blogAdmin/resource")
public class ResourceController
{
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 图片文件列表页
     * @param folder 文件夹
     * @param model
     * @return
     */
    @GetMapping("/imgs/list")
    public String imagesList(String folder, Model model)
    {
        String path = FileUtil.getUploadImagePath();
        if(!ValidateUtil.isEmptyString(folder)) {

            String lastChar = folder.substring(folder.length()-1);
            if(lastChar.equals( "/")) {
                folder = folder.substring(0, folder.length()-1);
            }

            String[] folderArr = folder.split("/");
            folder = folder+"/";
            path = path+folder;

            String[] folderUriList = new String[folderArr.length];
            for(int i = 0; i < folderArr.length; i++) {
                if(i == 0) {
                    folderUriList[i] = folderArr[i]+"/";
                } else {
                    folderUriList[i] = folderUriList[i-1] +folderArr[i]+ "/";
                }
            }

            model.addAttribute("folderArr", folderArr);
            model.addAttribute("folderUriList", folderUriList);
        } else {
            folder = "";
            model.addAttribute("folderArr", new String[0]);
            model.addAttribute("folderUriList", new String[0]);
        }
        File fileDir = new File(path);
        ArrayList<File> imageFileList = new ArrayList<>();
        ArrayList<File> imageDirectoryList = new ArrayList<>();

        if(fileDir.exists()) {
            /*String[] files = fileDir.list();
            System.out.println("===files amount:" + files.length);
            for(String fileName : files) {
                System.out.println("====file name: " + fileName);
            }*/
            File[] files = fileDir.listFiles();
            for(File file : files) {
                if(file.isFile()) {
                    if(file.isHidden()) {
                        System.out.println("===Hidden file name:" + file.getName());
                    } else {
                        imageFileList.add(file);
                    }
                } else if(file.isDirectory()) {
                    imageDirectoryList.add(file);
                }
            }
        }

        if(imageFileList.size() == 0 && imageDirectoryList.size() == 0) {
            model.addAttribute("fileSize", 0);
        } else {
            model.addAttribute("fileSize",imageFileList.size() + imageDirectoryList.size());
        }

        model.addAttribute("folder",folder);
        model.addAttribute("imageFileList", imageFileList);
        model.addAttribute("imageDirectoryList", imageDirectoryList);
        model.addAttribute("menuFlag", Constants.RESOURCE_MENU_FLAG);
        model.addAttribute("title", "图片管理");
        return "blog/admin/imagesList";
    }

    /**
     * 删除单个文件或者一个空目录
     * @param imgName
     */
    @PostMapping("/imgs/delete")
    @ResponseBody
    public void imageDelete(String imgName)
    {
        String path = FileUtil.getUploadImagePath();
        File file = new File(path+imgName);
        file.delete();
    }

    /**
     * 上传单个图片文件
     * @param imgFile 图片文件
     * @param imgName 自定义的文件名
     * @param folder  存放文件夹
     */
    @PostMapping("/imgs/upload")
    @ResponseBody
    public void imageUpload(MultipartFile imgFile, String imgName, String folder)
    {
        String path = FileUtil.getUploadImagePath();
        String imageUrl = fileUploadService.upload(imgFile, path+folder, imgName);
        System.out.println("===uploaded imageUrl:"+imageUrl);
    }

    /**
     * 创建文件夹
     * @param folder 父文件夹
     * @param folderName 新建的文件夹名
     */
    @PostMapping("/imgs/createFolder")
    @ResponseBody
    public void createFolder(String folder,String folderName)
    {
        if(!ValidateUtil.isEmptyString(folderName)) {
            String path = FileUtil.getUploadImagePath();
            File fileDir = new File(path + folder + folderName);
            if (!fileDir.exists()) {
                logger.info("文件夹不存在，创建文件夹。");
                fileDir.setWritable(true);
                boolean createDirResult = fileDir.mkdirs();
                logger.info("文件夹的读权限：{}，文件夹的写权限：{}，创建结果：{}", fileDir.canRead(), fileDir.canWrite(), createDirResult);
            }
        }
    }
}
