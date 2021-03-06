package com.dgut.community.controller;

import com.dgut.community.dto.NoticeDTO;
import com.dgut.community.entity.Notice;
import com.dgut.community.entity.Question;
import com.dgut.community.entity.QuestionCollect;
import com.dgut.community.entity.User;
import com.dgut.community.mapper.NoticeMapper;
import com.dgut.community.mapper.QuestionCollectMapper;
import com.dgut.community.mapper.QuestionMapper;
import com.dgut.community.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PersonalController {
    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionCollectMapper questionCollectMapper;

    @RequestMapping("/personal/{action}")
    public String Personal(@PathVariable(name = "action") String action, Model model,HttpSession session,@RequestParam(required = false,defaultValue = "1") Integer pageNum){
        if ("question".equals(action)){
            model.addAttribute("option", "question");
            model.addAttribute("optionName", "我的提问");
            User user = (User) session.getAttribute("user");
            PageHelper.startPage(pageNum,10);
            List<Question> questionList = questionMapper.findByName(user.getUsername());
            PageInfo pageInfo = new PageInfo<>(questionList,5);
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("questionList", questionList);
            return "personal";
        }
        if ("reply".equals(action)){
            model.addAttribute("option","reply");
            model.addAttribute("optionName","回复");
            User user = (User) session.getAttribute("user");

            PageHelper.startPage(pageNum,5);
            List<Notice> noticeList = noticeMapper.findNotice(user.getUser_id());
            PageInfo pageInfo = new PageInfo<>(noticeList,5);

            List<Notice> noticDTOList = new ArrayList<>();
            for ( Notice notice : noticeList){
                User user1 = userMapper.findByUserid(notice.getNotifier());
                if (notice.getOuterId() !=0){
                    Question question = questionMapper.findById(notice.getOuterId());
                    notice.setQuestion(question);
                }
                notice.setUser(user1);
                noticDTOList.add(notice);
            }

            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("noticeList", noticeList);
            model.addAttribute("noticDTOList",noticDTOList);

            return "personal";
        }
        if ("collect".equals(action)){
            model.addAttribute("option","collect");
            model.addAttribute("optionName","我的收藏");
            User user = (User) session.getAttribute("user");

            PageHelper.startPage(pageNum,10);
            List<QuestionCollect> questionCollects = questionCollectMapper.findByUserID(user.getUser_id());
            PageInfo pageInfo = new PageInfo<>(questionCollects,5);

            //List<Question> questionList = new ArrayList<>();
            for (QuestionCollect questionCollect : questionCollects){
                Question question = questionMapper.findById(questionCollect.getQuestion_id());
                questionCollect.setQuestion(question);
                //questionList.add(question);
            }
            model.addAttribute("pageInfo",pageInfo);
            //model.addAttribute("questiocollectnList",questionList);
            model.addAttribute("CollectList",questionCollects);
        }
        if ("myreply".equals(action)){
            model.addAttribute("option","myreply");
            model.addAttribute("optionName","我的回复");
            User user = (User) session.getAttribute("user");

            PageHelper.startPage(pageNum,5);
            List<Notice> noticeList = noticeMapper.findMyNotice(user.getUser_id());
            PageInfo pageInfo = new PageInfo<>(noticeList,5);

            List<Notice> noticDTOList = new ArrayList<>();
            for ( Notice notice : noticeList){
                if (notice.getOuterId() !=0 && notice.getType()!=4){
                    Question question = questionMapper.findById(notice.getOuterId());
                    notice.setQuestion(question);
                    noticDTOList.add(notice);
                }
            }

            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("noticeList", noticeList);
            model.addAttribute("noticDTOList",noticDTOList);

            return "personal";
        }
        return "personal";
    }

    @RequestMapping("/updateinfo")
    @ResponseBody
    @Transactional
    public String UpdateInfo(@RequestParam("id") Integer id,@RequestParam("username") String username,@RequestParam("mail") String mail,HttpSession session){
        User user = userMapper.findByUserid(id);
       int row = userMapper.updateinfo(id,username,mail);
       if (row>0){
           int q = questionMapper.updateUsername(user.getUsername(),username);
           int n = noticeMapper.updateNoticerName(user.getUsername(),username);
           if (q>0 && n>0){
               User user1 = userMapper.findByUserid(id);
               session.setAttribute("user",user1);
               return "修改成功";
           }
           return "发生一点小错误，请稍后再试";
       }else {
           return "发生一点小错误，请稍后再试";
       }
    }
}
