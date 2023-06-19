package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
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


@Api(description="课程章节管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/edu/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @ApiOperation(value = "嵌套章节数据列表")
    @GetMapping("nest_list/{courseId}")
    public R nestListByCourseId(
            @ApiParam(name = "courseId" ,value = "课程ID", required = true)
            @PathVariable String courseId
    ){
        List<ChapterVo> chapterVos = eduChapterService.nestListByCourseId(courseId);
        return R.ok().data("item", chapterVos);
    }

    @ApiOperation(value = "获取所有章节信息")
    @GetMapping("list")
    public R listAllCourse(){

        List<EduChapter> eduChapters = eduChapterService.list(null);
        return R.ok().data("courseList", eduChapters);
    }

    @ApiOperation(value = "新增章节")
    @PostMapping
    public R save(
            @ApiParam(name = "chapterVo", value = "章节对象", required = true)
            @RequestBody EduChapter chapter
    ){

        eduChapterService.save(chapter);
        return R.ok();

    }


    @ApiOperation(value = "根据ID查询章节")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id){

        EduChapter chapter = eduChapterService.getById(id);
        return R.ok().data("item", chapter);
    }


    @ApiOperation(value = "根据ID修改章节")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id,

            @ApiParam(name = "chapter", value = "章节对象", required = true)
            @RequestBody EduChapter chapter){

        chapter.setId(id);
        eduChapterService.updateById(chapter);
        return R.ok();
    }


    @ApiOperation(value = "根据ID删除章节")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id){

        boolean result = eduChapterService.removeChapterById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("该章节下有小节，或视频，不允许删除");
        }
    }
}

