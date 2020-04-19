package com.dgut.community.cache;

import com.dgut.community.dto.TagDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO Chinese = new TagDTO();
        Chinese.setCategoryName("语文");
        Chinese.setTags(Arrays.asList("作文","阅读","古文教育","音韵学","听","翻译","编著"));
        tagDTOS.add(Chinese);

        TagDTO Math = new TagDTO();
        Math.setCategoryName("数学");
        Math.setTags(Arrays.asList("数学史","数理逻辑与数学基础","数论","代数学","几何学","拓扑学","数学分析","常微分方程","偏微分方程","计算数学","概率论","数理统计学","应用统计数学","量子数学"));
        tagDTOS.add(Math);

        TagDTO English = new TagDTO();
        English.setCategoryName("英语");
        English.setTags(Arrays.asList("语法","阅读","写作","读音","听写","翻译","大学英语四级","大学英语六级","专四","专八"));
        tagDTOS.add(English);

        TagDTO Computer = new TagDTO();
        Computer.setCategoryName("计算机");
        Computer.setTags(Arrays.asList("前端","后端","移动端","数据库","运维","人工智能","工具","javascript","vue.js","css","html5","react.js","jquery","bootstrap","php","Java","Spring","SpringBoot","SpringMVC","django","c#","ruby","android","ios","算法","linux","ubuntu","服务器","github","eclipse","intellij-idea"));
        tagDTOS.add(Computer);

        TagDTO Philosophy = new TagDTO();
        Philosophy.setCategoryName("哲学");
        Philosophy.setTags(Arrays.asList("马克思主义哲学","中国哲学","外国哲学","逻辑学","伦理学","美学","宗教学","科技哲学"));
        tagDTOS.add(Philosophy);

        TagDTO Economics = new TagDTO();
        Economics.setCategoryName("经济学");
        Economics.setTags(Arrays.asList("理论经济学","应用经济学","微观经济学","宏观经济学","产业经济学","区域经济学","财政学","金融学"));
        tagDTOS.add(Economics);

        TagDTO Law = new TagDTO();
        Law.setCategoryName("法学");
        Law.setTags(Arrays.asList("理论法学","应用法学","历史法学","综合法学","国内法学","国际法学","法律史学","法社会学"));
        tagDTOS.add(Law);

        TagDTO Education = new TagDTO();
        Education.setCategoryName("教育学");
        Education.setTags(Arrays.asList("教育学原理","课程与教学论","高等教育","比较教育","学前教育","教育史","特殊教育","教育技术"));
        tagDTOS.add(Education);

        TagDTO literature = new TagDTO();
        literature.setCategoryName("文学");
        literature.setTags(Arrays.asList("中国文学","外国文学","古代文学","近现代文学","当代文学"));
        tagDTOS.add(literature);

        TagDTO history = new TagDTO();
        history.setCategoryName("历史学");
        history.setTags(Arrays.asList("历史地理学","古典学","考古学","社会史","经济史","环境史","口述史学","现代历史科学","博物馆学","民族学"));
        tagDTOS.add(history);

        TagDTO Art  = new TagDTO();
        Art.setCategoryName("艺术学");
        Art.setTags(Arrays.asList("美术学","电影学","语言艺术","形体艺术","影视艺术"));
        tagDTOS.add(Art);

        return tagDTOS;
    }
}
