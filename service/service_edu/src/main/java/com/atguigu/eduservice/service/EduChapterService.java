package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> nestListByCourseId(String courseId);

    boolean removeChapterById(String id);

    boolean removeByCourseId(String id);


}
