package net.xdclass.online_xdclass.service.impl;

import net.xdclass.online_xdclass.config.CacheKeyManager;
import net.xdclass.online_xdclass.model.entity.Video;
import net.xdclass.online_xdclass.model.entity.VideoBanner;
import net.xdclass.online_xdclass.mapper.VideoMapper;
import net.xdclass.online_xdclass.service.VideoService;
import net.xdclass.online_xdclass.utils.BaseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private BaseCache baseCache;

    @Override
    public List<Video> listVideo() {

        try {
            Object cacheObj = baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_VIDEO_LIST, () -> {
                List<Video> videoList = videoMapper.listVideo();
                return videoMapper.listVideo();

            });

            if (cacheObj instanceof List){
                List<Video> videoList = (List<Video>) cacheObj;
                return videoList;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public List<VideoBanner> listVideoBanner() {
        try {
            Object cacheObj = baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_BANNER_KEY, () -> {
                List<VideoBanner> videoBannersList = videoMapper.listVideoBanner();
                //videoMapper.listVideoBanner()
                return videoBannersList;
            });
            if (cacheObj instanceof List){
                List<VideoBanner> videoBannersList = (List<VideoBanner>) cacheObj;
                return videoBannersList;
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
//        return videoMapper.listVideoBanner();
    }

    @Override
    public Video findDetailById(int videoId) {

        try {
            String videoCacheKey = String.format(CacheKeyManager.VIDEO_DETAIL,videoId);

            Object cacheObj = baseCache.getOneHourCache().get(videoCacheKey, () -> {

                Video video = videoMapper.findDetailById(videoId);

                return video;

            });

            if (cacheObj instanceof Video){

                Video video = (Video) cacheObj;

                return video;
            }

        } catch (ExecutionException e) {

            e.printStackTrace();
        }


        return null;
    }
}
