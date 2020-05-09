package com.dgut.community.Utils;

import com.github.pagehelper.util.StringUtil;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MdUtil {
    public static String MdToHtml(String MDStr){
        Parser parser=Parser.builder().build();
        Node document=parser.parse(MDStr);
        HtmlRenderer renderer= HtmlRenderer.builder().build();
        String HtmlStr=renderer.render(document);
        return HtmlStr;
    }
    /**
     * 读取文件成字符串在通过MD解析器转换成HTML格式的字符串返回
     * @param url 文件URL
     * @return 返回HTML格式的字符串
     */
    public static String MDFile2Str(String url) {
        String mdStr="";
        if (StringUtil.isNotEmpty(url)) {
            File file = new File(url);
            if (file.exists() && file.isFile()) {
                try {
                    FileInputStream in = new FileInputStream(file);
                    int size = in.available();
                    byte[] buffer = new byte[size];
                    in.read(buffer);
                    in.close();
                    mdStr=new String(buffer,"UTF-8");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return MdUtil.MdToHtml(mdStr);
    }
}
