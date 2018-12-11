package com.nchhr.mall.Controller;


import com.nchhr.mall.Configure.WechatConfig;
import com.nchhr.mall.Service.WechatUserService;
import com.nchhr.mall.Utils.HttpUtil;
import com.nchhr.mall.Utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/wechat/pay")
public class WechatPayController {

    @Autowired
    WechatUserService wechatUserService;

    /**
     * 微信支付入口
     *
     * 1.判断是否有orderId，没有则重定向到支付错误页
     * 2.判断是否有openid，没有则重定向到微信
     * 3.都有，重定向到支付加载页
     *
     * @param httpSession 会话
     * @return 重定向网页
     */
    @RequestMapping("")
    public String pay(HttpSession httpSession) {
        System.out.println("---------------------------开始支付---------------------");
        if (httpSession.getAttribute("orderId") != null) {//
            if (httpSession.getAttribute("openid") != null) {
                //重定向到支付加载页
                String redirect_path = "/wechat/pay/loading";
                return "redirect:" + redirect_path;//支付加载页
            }
            // 重定向到微信
            String redirect_path = "/mall/wechat/pay/redirect";
            return "redirect:" + wechatUserService.getWeChatRequestURL(redirect_path);
        }
        //重定向到支付错误页
        String redirect_path = "/wechat/pay/error";
        return "redirect:" + redirect_path;
    }

    @RequestMapping("/redirect")//请求openid后微信回调于此，在此获取并保存openid
    public String redirect(HttpSession httpSession, String code, String state) {
        String openid = wechatUserService.getOpenID(code, state);
        System.out.println("------ openid: " + openid);
        httpSession.setAttribute("openid", openid);
        //重定向到支付加载页
        String redirect_path = "/wechat/pay/loading";
        return "redirect:" + redirect_path;//支付加载页
    }

    @RequestMapping("/handle")//统一下单+返回JSAPI支付参数
    @ResponseBody
    public Map handle(HttpSession httpSession) {
        System.out.println("-----------------支付下单----------------");
        try {
            //组装统一下单参数（必填项）
            String appid = WechatConfig.AppID;
            String mch_id = WechatConfig.MCH_ID;
            String device_info = "WEB";
            String nonce_str = WXPayUtil.generateNonceStr();
            String sign;
            String body = "酒水商城";
            String out_trade_no = (String) httpSession.getAttribute("orderId");
            String total_fee = (String) httpSession.getAttribute("orderFee"); //一分钱
            String notify_url = "http://" + WechatConfig.ADN  + "/mall/wechat/pay/notify";
            String trade_type = "JSAPI";
            String openid = (String) httpSession.getAttribute("openid");
            //按序装填
            Map<String, String> signMap = new HashMap<>();
            signMap.put("appid", appid);
            signMap.put("mch_id", mch_id);
            signMap.put("device_info", device_info);
            signMap.put("body", body);
            signMap.put("trade_type", trade_type);
            signMap.put("nonce_str", nonce_str);
            signMap.put("notify_url", notify_url);
            signMap.put("out_trade_no", out_trade_no);
            signMap.put("total_fee", total_fee);
            signMap.put("openid", openid);
            //通过以上信息获取签名
            sign = WXPayUtil.generateSignature(signMap, WechatConfig.APIKey);
            System.out.println("sign: " + sign);
            signMap.put("sign", sign);
            //获取prepay_id：
            String UnifiedOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
            Map unifiedOrderResult = WXPayUtil.xmlToMap(HttpUtil.sendPost(UnifiedOrderURL, WXPayUtil.mapToXml(signMap)));
            String prepay_id = (String) unifiedOrderResult.get("prepay_id");
            System.out.println("prepay_id: " + prepay_id);

            //组装JS参数
            String appId = WechatConfig.AppID;
            String timeStamp = String.valueOf(WXPayUtil.getCurrentTimestamp());
            String nonceStr = WXPayUtil.generateNonceStr();
            String packages = "prepay_id=" + prepay_id;
            String signType = "MD5";
            String paySign;
            //按序装填生成sign并装填
            Map<String, String> paySignMap = new HashMap<>();
            paySignMap.put("appId", appId);
            paySignMap.put("timeStamp", timeStamp);
            paySignMap.put("nonceStr", nonceStr);
            paySignMap.put("package", packages);
            paySignMap.put("signType", signType);
            paySign = WXPayUtil.generateSignature(paySignMap, WechatConfig.APIKey);
            paySignMap.put("paySign", paySign);

            return paySignMap;
        } catch (Exception e) {
            Map<String, String> paySignMap = new HashMap<>();
            paySignMap.put("error","handle error");
            return paySignMap;
        }
    }

    //支付成功后微信通知于此
    @RequestMapping("/notify")
    @ResponseBody
    public void notifys() {
        System.out.println("------------------------------通知");

    }

    @RequestMapping("/loading") //支付加载页
    public String loading() {
        return "payloading";
    }

    @RequestMapping("/success") //支付成功页
    public String success() {
        return "paysuccess";
    }

    @RequestMapping("/error") //支付出错页
    public String error() {
        return "payerror";
    }
}
