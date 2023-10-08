package cn.stkit.wxl.blog.util;

import org.pegdown.PegDownProcessor;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file MarkdownToHtml
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/1 5:14 下午
 */

public class MarkdownToHtml
{
    public static String markDownToHtml(String markdownStr)
    {
        PegDownProcessor pegDownProcessor = new PegDownProcessor(Integer.MAX_VALUE);
        return pegDownProcessor.markdownToHtml(markdownStr);
    }
}