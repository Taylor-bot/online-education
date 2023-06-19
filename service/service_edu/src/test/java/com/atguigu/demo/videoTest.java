package com.atguigu.demo;

import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class videoTest {

    @Autowired
    private EduVideoMapper eduVideoMapper;

    @Test
    public void selectCountByChapterId(){
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", "32");
        try{
            Integer count = eduVideoMapper.selectCount(wrapper);
            System.out.println(count==null);
        }catch (Exception e) {

        }
    }
}
