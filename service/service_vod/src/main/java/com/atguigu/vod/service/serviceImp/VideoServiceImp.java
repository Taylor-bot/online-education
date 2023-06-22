package com.atguigu.vod.service.serviceImp;

import com.atguigu.vod.service.VideoService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class VideoServiceImp implements VideoService {
    @Override
    public String uploadVideo(MultipartFile file) {

        try {
            InputStream in = file.getInputStream();
            String originname = file.getOriginalFilename();
            String title = originname.substring(0, originname.lastIndexOf("."));
            // TODO: 2023/6/22


        }catch (Exception e){

        }

        return null;
    }
}
