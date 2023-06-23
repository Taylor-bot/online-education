package com.atguigu.vod.service;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    String uploadVideo(MultipartFile file);

    void removeVideo(String videoId);

    void deleteVideos(List<String> videoList);
}
