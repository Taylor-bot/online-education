package com.atguigu.vodTest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;

import java.util.List;

public class TestVod {

    public static void main(String[] args) throws ClientException {
        //初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tQ5CDgATo7WwJBESFUE", "kcl7NaJuRRXETqn3YHLXoG7o0GyJVm");


        //创建获取地址的request和response

        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();


        //向request中添加视频设置id
        request.setVideoId("55030e9010db71ee809c6733a78e0102");

        //调用初始化对象里面的方法，传递request，获取数据
        response = client.getAcsResponse(request);


        //输出请求结果
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");

    }
}
