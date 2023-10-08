package cn.stkit.wxl.blog.util;

import cn.stkit.wxl.blogadmin.constants.Constants;
import org.springframework.util.ClassUtils;

import java.io.File;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file FileUtil
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/22 12:03 下午
 */

public class FileUtil
{
    /**
     * 获取程序运行时classes处在的目录路径，即：/xxx/xxx/target/classes/ 或者：/xxx/xxx/xxx/WEB-INF/classes/
     * @return
     */
    public static String getClassPath()
    {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        /*try {
            String path2 = ResourceUtils.getURL("classpath:").getPath();
            System.out.println("==path2 :" + path2);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }*/
        return path;
    }

    /**
     * 获取用户头像保存的目录路径
     * @return
     */
    public static String getUploadAvatarPath()
    {
        String path = getClassPath();
        return path + Constants.USER_AVATAR_PATH;
    }

    /**
     * 获取上传图片保存的目录路径
     * @return
     */
    public static String getUploadImagePath()
    {
        return getClassPath() + Constants.UPLOAD_IMAGE_PATH;
    }

    /**
     * 迭代删除文件夹
     * @param dirPath 文件夹路径
     */
    public static void deleteDir(String dirPath)
    {
        //读到
        File file = new File(dirPath);
        if(file.isFile()) {
            //判断是否是文件夹
            file.delete();//删除
        } else {
            //获取文件
            File[] files = file.listFiles();
            if(files == null) {
                file.delete();//删除
            } else {
                for(int i = 0; i < files.length; i++) {
                    deleteDir(files[i].getAbsolutePath());
                }
                file.delete();
            }
        }
    }
}