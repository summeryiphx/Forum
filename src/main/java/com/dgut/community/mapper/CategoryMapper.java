package com.dgut.community.mapper;

import com.dgut.community.entity.Xueke;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoryMapper {
    List<Xueke> selectcategory();
}
