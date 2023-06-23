package com.atguigu.vod.service.serviceImp;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.utils.StringUtils;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.utils.ConstantProperties;
import com.atguigu.vod.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@Slf4j
public class VideoServiceImp implements VideoService {
    @Override
    public String uploadVideo(MultipartFile file) {

        try {
            InputStream in = file.getInputStream();
            String originname = file.getOriginalFilename();
            String title = originname.substring(0, originname.lastIndexOf("."));
            // TODO: 2023/6/22
            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantProperties.ACCESS_KEY_ID,
                    ConstantProperties.ACCESS_KEY_SECRET,
                    title, originname, in
            );
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                log.warn(errorMessage);
                if(StringUtils.isEmpty(videoId)){
                    throw new GuliException(20001, errorMessage);
                }
            }
            return videoId;

        }catch (Exception e){
            throw new GuliException(20001, "guli vod 服务上传失败");
        }

    }
}
