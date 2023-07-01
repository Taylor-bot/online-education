package com.atguigu.eduservice.client.clientImpl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodClientImpl implements VodClient {


    @Override
    public R removeVideo(String videoId) {
        return R.error().message("Read time out");
    }

    @Override
    public R deleteVideos(List<String> videoList) {
        return R.error().message("Read time out");

    }
}
