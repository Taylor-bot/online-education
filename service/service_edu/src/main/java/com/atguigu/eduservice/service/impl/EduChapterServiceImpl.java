package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.atguigu.eduservice.entity.vo.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;
    @Override
    public List<ChapterVo> nestListByCourseId(String courseId) {

        /*
        * 最终要的数据表
        * */
        ArrayList<ChapterVo> chapterVos = new ArrayList<>();

        //获取章节信息
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.orderByAsc("sort", "id");
        List<EduChapter> chapters = baseMapper.selectList(wrapper);


        //获取课时信息
        QueryWrapper<EduVideo> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id", courseId);
        wrapper1.orderByDesc("sort", "id");
        List<EduVideo> videos = videoService.list(wrapper1);

        //填充vo
        int count = chapters.size();
        for (int i = 0; i < count; i++) {
            EduChapter eduChapter = chapters.get(i);
            //创建章节vo对象
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);


            //填充课时vo数据
            ArrayList<VideoVo> videoVos = new ArrayList<>();
            int count2 = videos.size();
            for (int j = 0; j < count2; j++) {
                EduVideo eduVideo = videos.get(j);
                if (eduChapter.getId().equals(eduVideo.getChapterId())) {
                    //创建课时vo对象
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVos.add(videoVo);

                }
            }
            chapterVo.setChildren(videoVos);
            chapterVos.add(chapterVo);
        }


        return chapterVos;
    }

    @Override
    public boolean removeChapterById(String id) {
        //根据id查询是否有课时视频
        System.out.println(videoService.getCountByChapterId(id));
        if (videoService.getCountByChapterId(id)) {
//            throw new GuliException(20009, "该章节下有课时，不允许删除");
            return false;
        }else {
            Integer i = baseMapper.deleteById(id);
            return null != i && i>0;
        }

    }

    @Override
    public boolean removeByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        Integer result = baseMapper.delete(wrapper);
        return null != result && result > 0;


    }
}
