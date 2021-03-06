package com.nchhr.mall.Controller;


import com.nchhr.mall.Configure.WechatConfig;
import com.nchhr.mall.Entity.MallUserEntity;
import com.nchhr.mall.Service.OrdersService;
import com.nchhr.mall.Service.WeChatUserService;
import com.nchhr.mall.Utils.HttpUtil;
import com.nchhr.mall.Utils.OrderSmsUtils;
import com.nchhr.mall.Utils.WXPayUtil;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/wechat/pay")
public class WechatPayController {

    @Autowired
    WeChatUserService wechatUserService;
    @Autowired
    OrdersService ordersService;

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
            String state = httpSession.getId();
            System.out.println("}}}}}}}}}}}}}}"+state);
            return "redirect:" + wechatUserService.getWeChatRequestURL(redirect_path, state);
        }
        //重定向到支付错误页
        String redirect_path = "/wechat/pay/error";
        return "redirect:" + redirect_path;
    }

    @RequestMapping("/redirect")//请求openid后微信回调于此，在此获取并保存openid
    public String redirect(HttpSession httpSession, String code, String state) {
        System.out.println("}}}}}}}}}}}}}}-"+state);
        httpSession = httpSession.getSessionContext().getSession(state);
        String openid = wechatUserService.getOpenID(code, state);
        System.out.println("------ openid: " + openid);
        httpSession.setAttribute("openid", openid);
        System.out.println("新拿openid:"+openid);
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
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            System.out.println(signMap.toString());
            System.out.println(openid);
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
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");

            return paySignMap;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> paySignMap = new HashMap<>();
            paySignMap.put("error","handle error");
            return paySignMap;
        }
    }

    //支付成功后微信通知于此
    @RequestMapping("/notify")
    @ResponseBody
    public String notifys(HttpServletRequest request, HttpSession session) {

        System.out.println("------------------------------通知完成-------------------------");

        //System.out.println("微信支付成功,微信发送的callback信息,请注意修改订单信息");
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();//获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
            String xml = WXPayUtil.streamToXml(inputStream);
            inputStream.close();
            Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);//将微信发的xml转map
            if(notifyMap.get("return_code").equals("SUCCESS")){
                if(notifyMap.get("result_code").equals("SUCCESS")){
                    String orderId = notifyMap.get("out_trade_no");//商户订单号
                    System.out.println("-----: "+orderId);
                    String orderAmount = notifyMap.get("total_fee");//实际支付的订单金额:单位 分
                    ordersService.setOrderStatus(orderId, "2");
                    String msg = ordersService.orderBonus(orderId);
                    System.out.println(msg);
                    OrderSmsUtils.orderSend(((MallUserEntity)session.getAttribute("MallUserInfo")).getPhone(),"已成功付款");
                }
            }
            //勿需通知
            String xmlResponse = "<xml> <return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
            return xmlResponse;
//            response.getWriter().write(xmlResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return "EXCEPTION";
        }
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
