package net.xdclass.online_xdclass.service.impl;

import io.jsonwebtoken.Jwts;
import net.xdclass.online_xdclass.model.entity.User;
import net.xdclass.online_xdclass.mapper.UserMapper;
import net.xdclass.online_xdclass.service.UserService;
import net.xdclass.online_xdclass.utils.CommonUtils;
import net.xdclass.online_xdclass.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int save(Map<String, String> userInfo) {

        User user = parseToUser(userInfo);

        if (user != null){
           return userMapper.save(user);
        }else {
            return -1;
        }

    }

    @Override
    public String findByPhoneAndPwd(String phone, String pwd) {
        User user = userMapper.findByPhoneAndPwd(phone,CommonUtils.MD5(pwd));

        if (user == null){
            return null;
        }else {
            String token = JWTUtils.geneJsonWebToken(user);
            return token;
        }

    }

    @Override
    public User findByUserId(Integer userId) {

        User user = userMapper.findByUserId(userId);

        return user;
    }


    private User parseToUser(Map<String, String> userInfo) {

        if (userInfo.containsKey("phone") && userInfo.containsKey("name") && userInfo.containsKey("pwd")) {
            User user = new User();

            user.setName(userInfo.get("name"));
            user.setHeadImg(getRandomImg());
            user.setPhone(userInfo.get("phone"));
            user.setCreateTime(new Date());

           String pwd = userInfo.get("pwd");

            user.setPwd(CommonUtils.MD5(pwd));

            return user;

        } else {
            return null;
        }
    }

    private static final String [] headImg = {
//            "https://xd-video-pc-img.oss-cnbeijing.aliyuncs.com/xdclass_pro/default/head_img/12.jpeg",
//            "https://xd-video-pc-img.oss-cnbeijing.aliyuncs.com/xdclass_pro/default/head_img/11.jpeg",
//            "https://xd-video-pc-img.oss-cnbeijing.aliyuncs.com/xdclass_pro/default/head_img/13.jpeg",
//            "https://xd-video-pc-img.oss-cnbeijing.aliyuncs.com/xdclass_pro/default/head_img/14.jpeg",
//            "https://xd-video-pc-img.oss-cnbeijing.aliyuncs.com/xdclass_pro/default/head_img/15.jpeg"
//            "C:/Users/Administrator/Desktop/head_img/1.jpg",
//            "C:/Users/Administrator/Desktop/head_img/2.jpg",
//            "C:/Users/Administrator/Desktop/head_img/3.jpg",
//            "C:/Users/Administrator/Desktop/head_img/4.jpg",
//            "C:/Users/Administrator/Desktop/head_img/5.jpg",
//            "C:/Users/Administrator/Desktop/head_img/6.jpg",
//            "C:/Users/Administrator/Desktop/head_img/7.jpg",
//            "C:/Users/Administrator/Desktop/head_img/8.jpg",
//            "C:/Users/Administrator/Desktop/head_img/9.jpg",
//            "C:/Users/Administrator/Desktop/head_img/10.jpg",
//            "C:/Users/Administrator/Desktop/head_img/11.jpg",
//            "C:/Users/Administrator/Desktop/head_img/12.jpg",
//            "C:/Users/Administrator/Desktop/head_img/13.jpg",
            "http://img.jj20.com/up/allimg/tx29/07241139285188006.jpg",
            "http://img.jj20.com/up/allimg/tx29/07241139285188007.jpg",
            "http://img.jj20.com/up/allimg/tx29/07241139295188008.png",
            "http://img.jj20.com/up/allimg/tx29/07241139295188009.png",
            "http://img.jj20.com/up/allimg/tx29/07241139295188010.png",
            "http://img.jj20.com/up/allimg/tx29/07241139295188011.png",
            "http://img.duoziwang.com/2021/03/1623076228417795.jpg",
            "http://img.duoziwang.com/2021/03/1623076228417795.jpg",
            "http://img.duoziwang.com/2021/03/1623076228385952.jpg"
    };

        public String getRandomImg(){
            int size = headImg.length;
            Random random = new Random();
            int index = random.nextInt(size);
            return headImg[index];
        }
}
