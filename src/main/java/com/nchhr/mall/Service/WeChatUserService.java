package com.nchhr.mall.Service;


import com.alibaba.fastjson.JSONObject;
import com.nchhr.mall.Configure.WechatConfig;
import com.nchhr.mall.Entity.WeChatUserEntity;
import com.nchhr.mall.Dao.WeChatUserDao;
import com.nchhr.mall.util.JSONUtil;
import com.nchhr.mall.util.WeChatUtil;
import com.nchhr.mall.Entity.WeChatOAuth2Token;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class WeChatUserService {

    @Resource
    private WeChatUserDao weChatUserDao;

    private WeChatOAuth2Token weChatOAuth2Token;

    private WeChatUserEntity weChatUser;

    //获取微信验证请求路径
    public String getWeChatRequestURL() {
        //回调页面路径
        String wechat_redirect_uri = "http://" + WechatConfig.ADN + "/mall/wechatuser/wechat_redirect";

        //授权请求路径
        String request_url = null;
        try {
            request_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WechatConfig.AppID
                    + "&redirect_uri="+ URLEncoder.encode(wechat_redirect_uri, "UTF-8")
                    + "&response_type=code"
                    + "&scope=snsapi_userinfo" //通过snsapi_userinfo必须显式授权，此处snsapi_base为隐式授权
                    + "&state=STATE#wechat_redirect";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return request_url;
    }

    //通过用户授权方式获得token和openid
    public void getWeChatOAuth2Token(String code, String state) {
        String WeChatOAuth2TokenURL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WechatConfig.AppID
                + "&secret=" + WechatConfig.AppSecret
                + "&code=" + code
                + "&grant_type=authorization_code";
        JSONObject WeChatOAuth2TokenJSON = JSONObject.parseObject(JSONUtil.getJSONByURL(WeChatOAuth2TokenURL));
        //实体类保存信息
        weChatOAuth2Token = new WeChatOAuth2Token();
        weChatOAuth2Token.setAccess_token(WeChatOAuth2TokenJSON.getString("access_token"));
        weChatOAuth2Token.setRefresh_token(WeChatOAuth2TokenJSON.getString("refresh_token"));
        weChatOAuth2Token.setOpenid(WeChatOAuth2TokenJSON.getString("openid"));
        weChatOAuth2Token.setScope(WeChatOAuth2TokenJSON.getString("scope"));
        weChatOAuth2Token.setExpires_in(WeChatOAuth2TokenJSON.getString("expires_in"));
        System.out.println("TEST SERVICE token:" + weChatOAuth2Token.toString());
    }

    //获取用户微信openId
    public String getOpenid() {
        return weChatOAuth2Token.getOpenid();
    }

    //获得用户微信信息
    public WeChatUserEntity getUser() {
        //配置token、openid和lang
        String access_token = weChatOAuth2Token.getAccess_token();
        String openid = weChatOAuth2Token.getOpenid();
        String lang = "zh_CN";
        //snsapi_base静默方式也可以拿到用户信息 ?
        String WeChatUserInfoURL = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token
                + "&openid=" + openid
                + "&lang=" + lang;
        JSONObject WeChatUserJSON = JSONObject.parseObject(JSONUtil.getJSONByURL(WeChatUserInfoURL));
        //实体类
        weChatUser = new WeChatUserEntity();
        weChatUser.setOpenid(WeChatUserJSON.getString("openid"));
        weChatUser.setNickname(WeChatUserJSON.getString("nickname"));
        weChatUser.setSex(WeChatUserJSON.getString("sex"));
        weChatUser.setLanguage(WeChatUserJSON.getString("language"));
        weChatUser.setCity(WeChatUserJSON.getString("city"));
        weChatUser.setProvince(WeChatUserJSON.getString("province"));
        weChatUser.setCountry(WeChatUserJSON.getString("country"));
        weChatUser.setHeadimgurl(WeChatUserJSON.getString("headimgurl"));
        weChatUser.setPrivilege(WeChatUserJSON.getString("privilege"));
        System.out.println("TEST SERVICE user:" + weChatUser.toString());
        return weChatUser;
    }

    //通过openid获取微信信息
    public WeChatUserEntity getUserByOpenid(String openid){
        return weChatUserDao.getUserByOpenid(openid);
    }

    //获取微信验证请求路径
    public String getWeChatRequestURL(String redirect_path) {
        //确定回调页面路径
        String wechat_redirect_uri = "http://"+ WechatConfig.ADN + redirect_path;
        //生成授权请求路径
        String request_url = null;
        try {
            request_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WechatConfig.AppID
                    + "&redirect_uri="+ URLEncoder.encode(wechat_redirect_uri, "UTF-8")
                    + "&response_type=code"
                    + "&scope=snsapi_base" //通过snsapi_userinfo必须显式授权，此处snsapi_base为隐式授权
                    + "&state=STATE#wechat_redirect";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return request_url;
    }

    //通过用户授权方式获得token和openid
    public String getOpenID(String code, String state) {
        //回调进来进行处理
        String wechatOAuth2TokenURL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WechatConfig.AppID
                + "&secret=" + WechatConfig.AppSecret
                + "&code=" + code
                + "&grant_type=authorization_code";
        JSONObject wechatOAuth2TokenJSON = JSONObject.parseObject(JSONUtil.getJSONByURL(wechatOAuth2TokenURL));

        //还能拿到的请他信息请参考WechatOAuth2Token实体类，此处只拿openid
        return wechatOAuth2TokenJSON.getString("openid");
    }
}
