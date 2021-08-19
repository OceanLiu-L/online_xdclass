package net.xdclass.online_xdclass.controller;

import net.xdclass.online_xdclass.model.entity.Video;
import net.xdclass.online_xdclass.model.entity.VideoBanner;
import net.xdclass.online_xdclass.service.VideoService;
import net.xdclass.online_xdclass.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/pub/video/")
public class VideoController {
    @Autowired
    private VideoService videoService;


    @RequestMapping("list_banner")
    public JsonData indexBanner(){
        List<VideoBanner> videoBannerList = videoService.listVideoBanner();

        return JsonData.buildSuccess(videoBannerList);
    }

    @RequestMapping("list")
    public JsonData listVideo(){
        List<Video> videoList1111 = videoService.listVideo();
        return JsonData.buildSuccess(videoList1111);
    }


    @RequestMapping("find_detail_by_id")
    public JsonData findDetailById(@RequestParam(value = "video_id",required = true)int videoId){

        Video video = videoService.findDetailById(videoId);

        return JsonData.buildSuccess(video);

    }


}
