package com.dgut.community.controller;

import com.dgut.community.cache.TagCache;
import com.dgut.community.entity.Question;
import com.dgut.community.entity.User;
import com.dgut.community.mapper.NoticeMapper;
import com.dgut.community.mapper.QuestionMapper;
import com.dgut.community.mapper.UserMapper;
import com.dgut.community.redis.RedisKeyUtils;
import com.dgut.community.service.QuestionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/")
    public String index(HttpSession session){
        User user=(User)session.getAttribute("user");
        if (user == null){
            return "index";
        }else {
            return "redirect:/home";
        }

    }

    @RequestMapping("/updateAva")
    public String updateAva(HttpSession session){
        User user=(User)session.getAttribute("user");
        User user1 = userMapper.findByName(user.getUsername());
        session.setAttribute("user",user1);
        return "redirect:/home";
    }

    @RequestMapping("/to_categories")
    public String to_categories(Model model){
        model.addAttribute("tags", TagCache.get());
        return "page-categories";
    }

    @RequestMapping("/home")
    public String Index( HttpSession session,Model model,@RequestParam(required = false,defaultValue = "1") Integer pageNum){
//        question1是带有User question2是不带有User
        PageHelper.startPage(pageNum,15);
        List<Question> questionList2=questionMapper.findAll();
        PageInfo pageInfo = new PageInfo<>(questionList2,5);

        List<Question> questionList1=new ArrayList<>();
        for (Question question:questionList2){
            User user=userMapper.findByName(question.getCreator());
            question.setUser(user);
            //获取实时likecount
            Cursor<Map.Entry<Object, Object>> cursor_likecount = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_QUESTION_LIKED_COUNT, ScanOptions.NONE);
            if (cursor_likecount.hasNext()){
                while (cursor_likecount.hasNext()){
                    Map.Entry<Object, Object> map = cursor_likecount.next();
                    String key = (String)map.getKey();
                    Integer value = (Integer) map.getValue();
                    if (key.equals(String.valueOf(question.getId()))){
                        int likecount = value + question.getLikeCount();
                        question.setLikeCount(likecount);
                    }
                }
            }
            questionList1.add(question);
        }


        model.addAttribute("question2",questionList2);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("question1",questionList1);
        return "home";
    }

    @RequestMapping("/search")
    public String Search(String search,Model model,@RequestParam(required = false,defaultValue = "1") Integer pageNum){
        if (search.isEmpty()){
            return "redirect:/";
        }
        if (!search.contains(" ") || (search.substring(search.length() - 1).equals(" ") && !search.substring(0,search.length()-1).contains(" "))){
            if (search.substring(search.length() - 1).equals(" ")){
                search = search.substring(0,search.length()-1);
            }
            PageHelper.startPage(pageNum,15);
            List<Question> questionList = questionMapper.search1(search);
            PageInfo pageInfo = new PageInfo<>(questionList,5);
            if (questionList.isEmpty()){
                return "findnothing";
            }
            model.addAttribute("question2",questionList);
            model.addAttribute("pageInfo",pageInfo);
            return "home";
        }else {
            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
            PageHelper.startPage(pageNum,15);
            List<Question> questionList = questionMapper.search2(search);
            PageInfo pageInfo = new PageInfo<>(questionList,5);

            if (questionList.isEmpty()){
                return "findnothing";
            }
            model.addAttribute("question2",questionList);
            model.addAttribute("pageInfo",pageInfo);
            return "home";
        }

    }

}
