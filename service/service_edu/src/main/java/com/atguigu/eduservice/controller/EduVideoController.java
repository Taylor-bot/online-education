package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.VideoForm;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
@RestController
@CrossOrigin
@Api(description = "课时管理")
@RequestMapping("/admin/edu/video")
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @ApiOperation("获取课时列表")
    @GetMapping
    public R getVideos() {
        List<EduVideo> videos = eduVideoService.list(null);

        return R.ok().data("videos", videos);
    }

    @ApiOperation("根据章节后期课时信息")
    @GetMapping("{chapterId}")
    public R getVideosByCourseId(
            @ApiParam(name = "chapterId", value = "章节id", required = true)
            @PathVariable String chapterId
    ) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        List<EduVideo> eduVideos = eduVideoService.list(wrapper);

        return R.ok().data("videos", eduVideos);
    }

    @ApiOperation("新增课时")
    @PostMapping("save-video")
    public R saveVideo(
            @ApiParam(name = "videoForm" ,value = "课时对象" , required = true)
            @RequestBody VideoForm videoForm
            ){
        eduVideoService.saveVideoInfo(videoForm);
        return R.ok();

    }


    @ApiOperation("更新课时")
    @PutMapping("update-video/{id}")
    public R updateVideo(
            @ApiParam(name = "videoForm" ,value = "课时对象" , required = true)
            @RequestBody VideoForm videoForm,
            @ApiParam(name = "id" ,value = "课时id" , required = true)
            @PathVariable String id
    ){
        eduVideoService.updateVideo(videoForm);
        return R.ok();

    }


    @ApiOperation(value = "根据ID查询课时")
    @GetMapping("video-info/{id}")
    public R getVideInfoById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){

        VideoForm videoForm = eduVideoService.getVideoInfoFormById(id);
        return R.ok().data("item", videoForm);
    }


    @ApiOperation(value = "根据ID删除课时")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){

        boolean result = eduVideoService.removeVideoById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }

}

