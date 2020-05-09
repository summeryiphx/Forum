package com.dgut.community.controller;

import com.dgut.community.Utils.JsonResult;
import com.dgut.community.Utils.MdUtil;
import com.dgut.community.cache.TagCache;
import com.dgut.community.entity.*;
import com.dgut.community.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class PublishController {
    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    CategoryMapper categoryMapper;

//    @GetMapping("/publish")
//    public ModelAndView publish(String creator, Model model){
//        System.out.println(creator);
//        model.addAttribute("creator",creator);
//        return new ModelAndView("/publish","model",model);
//    }
    @RequestMapping("/publish")
    public String publish(String creator,Model model){
        model.addAttribute("creator",creator);
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @GetMapping("/cque")
    public String CreateQuestion(Question question, HttpSession session){
        User user=(User)session.getAttribute("user");

        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setContent(MdUtil.MdToHtml(question.getContent()));
        questionMapper.CreateQuestion(question);

        Notice notice = new Notice();
        notice.setNotifier(user.getUser_id());
        notice.setNotifier_name(user.getUsername());
        notice.setTitle(question.getTitle());
        User receiver = userMapper.findByName(question.getTeacher());
        notice.setReceiver(receiver.getUser_id());
        notice.setOuterId(question.getId());
        notice.setType(4);
        notice.setGmt_create(System.currentTimeMillis());
        notice.setStatus(0);
        noticeMapper.insert(notice);
        return "redirect:/";
    }

    @RequestMapping("/to_editpublish")
    public String to_editpublish(Integer id, Model model){
        Question question = questionMapper.findById(id);
        String questiontag = questionMapper.findTag(id);
        model.addAttribute("questiontag",questiontag);
        model.addAttribute("tags", TagCache.get());
        model.addAttribute("question",question);
        return "update-publish";
    }

    @RequestMapping("/updatepublish")
    public String updatepublish(Question question, Model model){
        int row = questionMapper.updatepublish(question);
        Question question1 = questionMapper.findById(question.getId());
        User user = userMapper.findByName(question1.getCreator());
        question1.setUser(user);
        model.addAttribute("question",question1);
        return "question-view";
    }

    @GetMapping("/category")
    @ResponseBody
    public JsonResult<Xueke> category(){

        return JsonResult.success(categoryMapper.selectcategory());
    }
    @GetMapping("/teacher")
    @ResponseBody
    public JsonResult<Teacher> teacher(String category){
        return JsonResult.success(teacherMapper.selectBycatgegory(category));
    }
}
