package net.xdclass.online_xdclass.service.impl;

import net.xdclass.online_xdclass.exception.XDException;
import net.xdclass.online_xdclass.mapper.EpisodeMapper;
import net.xdclass.online_xdclass.mapper.PlayRecordMapper;
import net.xdclass.online_xdclass.mapper.VideoMapper;
import net.xdclass.online_xdclass.mapper.VideoOrderMapper;
import net.xdclass.online_xdclass.model.entity.Episode;
import net.xdclass.online_xdclass.model.entity.PlayRecord;
import net.xdclass.online_xdclass.model.entity.Video;
import net.xdclass.online_xdclass.model.entity.VideoOrder;
import net.xdclass.online_xdclass.service.VideoOrderService;
import net.xdclass.online_xdclass.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.xa.XAException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class VideoOrderServiceImpl implements VideoOrderService {

    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private EpisodeMapper episodeMapper;

    @Autowired
    private PlayRecordMapper playRecordMapper;


    @Override
    @Transactional
    public int save(int userId, int videoId) {

        VideoOrder videoOrder = videoOrderMapper.findByUserIdAndVideoIdAndState(userId, videoId, 1);
        if (videoOrder != null){return 0;}

        Video video = videoMapper.findById(videoId);

        VideoOrder newVideoOrder = new VideoOrder();

        newVideoOrder.setUserId(userId);
        newVideoOrder.setCreateTime(new Date());
        newVideoOrder.setVideoId(videoId);
        newVideoOrder.setVideoImg(video.getCoverImg());
        newVideoOrder.setVideoTitle(video.getTitle());
        newVideoOrder.setOutTradeNo(UUID.randomUUID().toString());
        newVideoOrder.setState(1);
        newVideoOrder.setTotalFee(video.getPrice());

        int rows = videoOrderMapper.saveOrder(newVideoOrder);


        if (rows == 1){
            Episode episode = episodeMapper.findFirstEpisodeByVideoId(videoId);

            if (episode == null){
                throw new XDException(-1,"视频没有集信息，请运维人员检查");
            }
            PlayRecord playRecord = new PlayRecord();
            playRecord.setCreateTime(new Date());
            playRecord.setVideoId(videoId);
            playRecord.setUserId(userId);
            playRecord.setCurrentNum(episode.getNum());
            playRecord.setEpisodeId(episode.getId());

            playRecordMapper.saveRecord(playRecord);

        }

        return rows;
    }

    @Override
    public List<VideoOrder> listOrderByUserId(Integer userId) {

        return videoOrderMapper.listOrderByUserId(userId);
    }
}
