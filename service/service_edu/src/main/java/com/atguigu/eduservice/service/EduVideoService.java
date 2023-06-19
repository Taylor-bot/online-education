package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.VideoForm;
import com.baomidou.mybatisplus.extension.service.IService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
public interface EduVideoService extends IService<EduVideo> {

    boolean getCountByChapterId(String id);

    void saveVideoInfo(VideoForm videoForm);

    void updateVideo(VideoForm videoForm);

    VideoForm getVideoInfoFormById(String id);

    boolean removeVideoById(String id);

    boolean removeByCourseId(String id);
}
