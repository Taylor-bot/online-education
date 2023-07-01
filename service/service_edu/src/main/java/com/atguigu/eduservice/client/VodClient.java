package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.clientImpl.VodClientImpl;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod", fallback = VodClientImpl.class)
@Component
public interface VodClient {


    @DeleteMapping("/vodService/video/{videoId}")
    public R removeVideo(@ApiParam(name = "videoId", value = "云端视频id", required = true)
                         @PathVariable String videoId);

    @DeleteMapping("/vodService/video/video_ids")
    public R deleteVideos(@ApiParam(name = "videoIdList", value = "云端视频id", required = true)
                          @RequestParam("videoList") List<String> videoList);


}
