package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.Dto.CourseQuery;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程基本信息的方法
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getCoursePublishVoById(String id);

    void publishCourseById(String id);

    void pageQuery(Page<EduCourse> coursePage, CourseQuery courseQuery);

    boolean deleteById(String id);
}
