package cn.stkit.wxl.blogadmin.entity;

import cn.stkit.wxl.blogadmin.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file Pages
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/2 6:17 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pages<T>
{
    //页码
    Integer pageNumber;

    //分页索引
    Integer page;

    //每页数量
    Integer pageSize;

    public static Pages defaultPages(Integer pageNumber)
    {
        if(pageNumber == null)
            pageNumber = 1;

        if(pageNumber < 1)
            pageNumber = 1;

        //return new Pages(pageNumber, (pageNumber - 1) * Constants.defaultPageSize, Constants.defaultPageSize);
        return new Pages(pageNumber, pageNumber - 1, Constants.defaultPageSize);
    }

    public static Pages defaultPages(Integer pageNumber, Integer pageSize)
    {
        if(pageNumber == null)
            pageNumber = 1;

        if(pageNumber < 1)
            pageNumber = 1;

        if(pageSize == null)
            pageSize = Constants.defaultPageSize;

        //return new Pages(pageNumber, (pageNumber - 1) * pageSize, pageSize);
        return new Pages(pageNumber, pageNumber - 1, pageSize);
    }
}