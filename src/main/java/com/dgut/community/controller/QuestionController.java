package com.dgut.community.controller;

import com.dgut.community.dto.LikedCountDTO;
import com.dgut.community.entity.*;
import com.dgut.community.mapper.*;
import com.dgut.community.redis.RedisKeyUtils;
import com.dgut.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class QuestionController {
    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UserLikeMapper userLikeMapper;

    @Autowired
    QuestionCollectMapper questionCollectMapper;

    @RequestMapping("/getquestion")
    public String GetQuestion(Integer id, Model model, HttpSession session){
        Question question=questionMapper.findById(id);
        User q_user = userMapper.findByName(question.getCreator());
        question.setUser(q_user);

        List<Question> relateQuestions = questionService.selectRelate(question);  //查找相关问题

        //查找评论
        List<Comment> commentList = commentMapper.findAllcomments(id);
        List<Comment> commentList1 = new ArrayList<>();
        for (Comment comment : commentList){
            User user=userMapper.findByUserid(comment.getCommentator());
            comment.setUser(user);
            commentList1.add(comment);
        }

        //+阅读数
        User user =(User) session.getAttribute("user");
        if (user != null && !user.getUsername().equals(question.getCreator())){
            questionMapper.incViewcount(id);
        }

        //文章作者ID 文章ID改String类型作为hash key
        User likedUser = userMapper.findByName(question.getCreator());
        int likedUserId = likedUser.getUser_id();
        String likeuserid = String.valueOf(likedUserId);
        String likepostid = String.valueOf(user.getUser_id());
        String qusetion_id = String.valueOf(question.getId());
        //查询是否已经点赞
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        if (cursor.hasNext()){
            while (cursor.hasNext()){
                Map.Entry<Object, Object> entry = cursor.next();
                String key = (String) entry.getKey();
                //分离出 likedUserId，likedPostId
                String[] split = key.split("::");
                String likeduserId = split[0];
                String likedpostId = split[1];
                String Question_ID = split[2];
                Integer value = (Integer) entry.getValue();
                if (likeduserId.equals(likeuserid) && likedpostId.equals(likepostid) && Question_ID.equals(qusetion_id) && value==1){
                    model.addAttribute("likestatus",1); //1为已点赞
                }else {
                    model.addAttribute("likestatus",0); //0为未点赞
                }
            }
        }else {
           UserLike userLike = userLikeMapper.findByLikedUserIdAndLikedPostIdQusetionID(String.valueOf(likedUserId),String.valueOf(user.getUser_id()),question.getId());
           if (!StringUtils.isEmpty(userLike) && userLike.getStatus() == 1){
               model.addAttribute("likestatus",1); //1为已点赞
           }else {
               model.addAttribute("likestatus",0); //0为未点赞
           }
        }

        //收藏状态
        QuestionCollect questionCollect = questionCollectMapper.findByUserIDAndQID(user.getUser_id(),question.getId());
        if (questionCollect == null){
            model.addAttribute("collect",0);
        }else {
            model.addAttribute("collect",1);
        }

        //获取实时likecount
        Cursor<Map.Entry<Object, Object>> cursor_likecount = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_QUESTION_LIKED_COUNT, ScanOptions.NONE);
        if (cursor_likecount.hasNext()){
            while (cursor_likecount.hasNext()){
                Map.Entry<Object, Object> map = cursor_likecount.next();
                String key = (String)map.getKey();
                Integer value = (Integer) map.getValue();
                if (key.equals(qusetion_id)){
                    int likecount = value + question.getLikeCount();
                    model.addAttribute("likecount",likecount);
                }else {
                    model.addAttribute("likecount",question.getLikeCount());
                }
            }
        }else {
            model.addAttribute("likecount",question.getLikeCount());
        }


        model.addAttribute("question",question);
        model.addAttribute("likedUserId",likedUserId);
        model.addAttribute("qusetion_id",qusetion_id);
        model.addAttribute("commentList",commentList1);
        model.addAttribute("relateQuestions",relateQuestions);
        return "question-view";
    }

    @RequestMapping("to_questionview")
    public String to_questionview(){
        return "question-view";
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public int delete(Integer id){
        int row = questionMapper.del(id);
        return row;
    }
}
