package com.dgut.community.mapper;


import com.dgut.community.dto.NoticeDTO;
import com.dgut.community.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoticeMapper {
    int insert(Notice notice);

    List<Notice> findNotice(Integer id);

    int noticecount(Integer id);

    int read(Integer id);
}
