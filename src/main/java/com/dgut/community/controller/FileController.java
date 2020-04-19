package com.dgut.community.controller;

import com.dgut.community.Provider.UCloudProvider;
import com.dgut.community.dto.FileDTO;
import com.dgut.community.entity.User;
import com.dgut.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class FileController {
    @Autowired
    UCloudProvider uCloudProvider;
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        try {
            String fileName = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(fileName);
            return fileDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("../static/images/touxiang.jpg");
        return fileDTO;
    }

    @RequestMapping("/file/avatar")
    @ResponseBody
    public int uploadAvatar(@RequestParam(value = "file")MultipartFile file, HttpServletRequest request, HttpSession session){
        User user =(User) session.getAttribute("user");
        try {
            String fileName=uCloudProvider.upload(file.getInputStream(),file.getContentType(),file.getOriginalFilename());
                System.out.println(fileName);
                if (fileName!=null){
                    userMapper.updateAvatar(user.getUser_id(),fileName);
                    return 1;
                }else {
                    return 0;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
