package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.VideoForm;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Override
    public boolean getCountByChapterId(String id) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", id);
        Integer count = baseMapper.selectCount(wrapper);
        return null!=count &&count >0;
    }

    @Override
    public void saveVideoInfo(VideoForm videoForm) {
        EduVideo video= new EduVideo();
        BeanUtils.copyProperties(videoForm, video);
        boolean result = this.save(video);
        if (!result) {
            throw new GuliException(20009, "课时信息保存失败");
        }
    }

    @Override
    public void updateVideo(VideoForm videoForm) {
        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(videoForm, video);
        boolean result = this.updateById(video);
        if (!result) {
            throw new GuliException(20009, "修改失败");
        }
    }

    @Override
    public VideoForm getVideoInfoFormById(String id) {
            //从video表中取数据
            EduVideo video = this.getById(id);
            if(video == null){
                throw new GuliException(20001, "数据不存在");
            }

            //创建videoInfoForm对象
            VideoForm videoInfoForm = new VideoForm();
            BeanUtils.copyProperties(video, videoInfoForm);

            return videoInfoForm;
    }

    @Override
    public boolean removeVideoById(String id) {

        //删除视频资源 TODO

        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;    }

    @Override
    public boolean removeByCourseId(String id) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        Integer result = baseMapper.delete(wrapper);
        return null != result && result > 0;
    }

}
