package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.Dto.CourseQuery;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.javafx.iio.gif.GIFImageLoaderFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    //添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        //返回添加之后课程id，为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    @ApiOperation(value = "根据ID获取课程信息")
    @GetMapping("course-info/{id}")
    public R getCourseInfo(
            @ApiParam(name = "id", value = "courseId")
            @PathVariable String id
    ){
        EduCourse educourse = courseService.getById(id);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(educourse, courseInfoVo);
        return R.ok().data("item", courseInfoVo);


    }

    @ApiOperation(value = "根据ID获取课程发布信息")
    @GetMapping("course-publish-info/{id}")
    public R getCoursePublishVoById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){

        CoursePublishVo courseInfoForm = courseService.getCoursePublishVoById(id);
        return R.ok().data("item", courseInfoForm);
    }


    @ApiOperation(value = "根据id发布课程")
    @PutMapping("publish-course/{id}")
    public R publishCourseById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){
        courseService.publishCourseById(id);
        return R.ok().data("couseInfo", courseService.getById(id));
    }


    @ApiOperation(value = "分页课程列表")
    @GetMapping("queryCourse/{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            CourseQuery courseQuery){
        Page<EduCourse> coursePage = new Page<>(page, limit);
        courseService.pageQuery(coursePage, courseQuery);
        List<EduCourse> records = coursePage.getRecords();
        long total = coursePage.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    //delete course
    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "逻辑删除course")
    public R deleteCourse(
            @ApiParam(name = "courseId", value = "id", required = true)
            @PathVariable String id
            ) {
        boolean result = courseService.deleteById(id);
        if (result){
            return R.ok();
        }else {
            return R.error().message("failed to delete");
        }

    }

}

