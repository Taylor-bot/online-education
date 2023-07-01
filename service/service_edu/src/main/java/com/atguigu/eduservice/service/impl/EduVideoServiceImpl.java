package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.event.AbstractIgnoreExceptionReadListener;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.VideoForm;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired
    VodClient vodClient;
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

        //删除视频资源
        EduVideo video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            R r = vodClient.removeVideo(videoSourceId);
            if (r.getCode() ==20001){
                throw new GuliException(20009, "熔断器。。。");
            }
        }

        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;    }

    @Override
    public boolean removeByCourseId(String id) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        wrapper.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(wrapper);

        //得到所有视频列表的云端原始视频id
        List<String> videoSourceList = new ArrayList<>();
        for (EduVideo video : videoList) {
            if (!StringUtils.isEmpty(video)){
                videoSourceList.add(video.getVideoSourceId());

            }
        }
        //调用vod服务删除远程视频
        //得到所有视频列表的云端原始视频id
        if (videoSourceList.size() > 0) {
            vodClient.deleteVideos(videoSourceList);
        }

        QueryWrapper<EduVideo> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id", id);
        Integer result = baseMapper.delete(wrapper1);
        return null != result && result > 0;
    }

}
