package com.dgut.community.dto;

import lombok.Data;

@Data
public class FileDTO {

    /**
     * success : 0 | 1
     * Message : 提示的信息，上传成功或上传失败及错误信息等。
     * url : 图片地址
     */

    private int success;
    private String message;
    private String url;

}
