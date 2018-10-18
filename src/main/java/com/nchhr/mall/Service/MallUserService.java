package com.nchhr.mall.Service;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.nchhr.mall.Dao.MallUserDao;
import com.nchhr.mall.Dao.WeChatUserDao;
import com.nchhr.mall.Entity.MallUserEntity;
import com.nchhr.mall.Entity.PhoneCodeEntity;
import com.nchhr.mall.Entity.WeChatUserEntity;
import com.nchhr.mall.Enum.CodeEnum;
import com.nchhr.mall.Enum.ExceptionEnum;
import com.nchhr.mall.Exception.MDException;
import com.nchhr.mall.Utils.CodeUtils;
import com.nchhr.mall.Utils.MD5Utils;
import com.nchhr.mall.Utils.PhoneCodeUtils;
import com.nchhr.mall.Utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class MallUserService {

    @Resource
    MallUserDao mallUserDao;

    @Resource
    WeChatUserDao weChatUserDao;

    @Autowired
    CookiesService cookiesService;

    public String getCode(String phone, HttpSession session) {
        MallUserEntity mallUser = mallUserDao.loadByID(phone);
        if (mallUser == null) {
            //获取随机验证码，保存在session中
            CodeUtils codeUtils = new CodeUtils();
            PhoneCodeUtils phoneCodeUtils = new PhoneCodeUtils();

            //设置超时时间-可自行调整
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            //初始化ascClient需要的几个参数
            final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
            final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
            //替换成你的AK
            final String accessKeyId = CodeEnum.AccessId.getValue();//你的accessKeyId,参考本文档步骤2
            final String accessKeySecret = CodeEnum.AccessKeySecre.getValue();//你的accessKeySecret，参考本文档步骤2
            //获得随机验证码
            String usercode = codeUtils.createRandom(true, 6);
            //保存phone code到session中
            if (phoneCodeUtils.save(phone, usercode, session) == false) {
                //4代表获取验证码频繁
                return "4";
            }
            //初始化ascClient,暂时不支持多region（请勿修改）
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", CodeEnum.AccessId.getValue(),
                    CodeEnum.AccessKeySecre.getValue());
            try {
                DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            } catch (ClientException e) {
                e.printStackTrace();
            }
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(CodeEnum.SignName.getValue());
            //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
            request.setTemplateCode(CodeEnum.SMSTemplateCode.getValue());
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            request.setTemplateParam("{\"code\":\"" + usercode + "\"}");
            //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            // request.setOutId("yourOutId");
            //请求失败这里会抛ClientException异常
            SendSmsResponse sendSmsResponse = null;
            try {
                sendSmsResponse = acsClient.getAcsResponse(request);
            } catch (ClientException e) {
                e.printStackTrace();
                //3未知错误
                return "3";
            }
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                //请求成功
                System.out.println("验证码发送成功！");
                return "1";
            }


        }
        if (mallUser != null) {
            //2代表已注册
            return "2";
        }

        return "3";
    }

    /**
     * 登录与注册
     *
     * @param userPhone
     * @param code
     * @param pwd
     * @param session
     * @param response
     * @param request
     * @return
     */
    @Transactional
    public String RegistLogin(String userPhone, String code, String pwd, HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        MD5Utils md5Utils = new MD5Utils();
        //再次判断注册手机号已被注册（是否）
        MallUserEntity loadByPhone = mallUserDao.loadByID(userPhone);
        if (loadByPhone == null) {
            PhoneCodeEntity phoneCode = (PhoneCodeEntity) session.getAttribute(userPhone);
            if (phoneCode == null) {
                //4其他错误
                return "5";
            }
            if (userPhone.equals(phoneCode.getPhone())) {
                String mdCode;
                try {
                    mdCode = md5Utils.MD5Encode(code, "utf8");

                } catch (Exception e) {
                    throw new MDException(ExceptionEnum.MD5);

                }
                if (mdCode.equals(phoneCode.getCode())) {
                    //1成功
                    //添加商城用户
                    CodeUtils codeUtils = new CodeUtils();
                    //获取微信id
                    WeChatUserEntity weChatUserEntity = (WeChatUserEntity)session.getAttribute("weChatUser");

                    weChatUserDao.addWeCharUser(weChatUserEntity);

                    String Mid = "M" + codeUtils.createRandom(false, 16);
                    System.out.println(Mid);

                    boolean b = mallUserDao.RegistLogin(Mid, userPhone, pwd,TimeUtils.getTime(),weChatUserEntity.getOpenid());
                    System.out.println(b);
                    if (b == true) {
                        String Sid = "S" + codeUtils.createRandom(false, 12);

                        if (mallUserDao.addShop(Sid)) {
                            if (mallUserDao.updateSCat(Mid, Sid)) {
                                MallUserEntity mallUser = mallUserDao.loadByMid(Mid);
                                //保存cookies
                                cookiesService.saveCookies(Mid,response,request);
                                session.setAttribute("MallUserInfo", mallUser);
                                //1代表成功
                                return "1";
                            } else {
                                //4其他错误
                                return "4";
                            }
                        } else {
                            //4其他错误
                            return "4";
                        }

                    } else {
                        //4其他错误
                        return "4";
                    }

                } else {
                    //3验证码错误
                    return "3";
                }
            } else {
                //2手机号错误
                return "2";
            }
        } else {
            return "6";
        }


    }

    /**
     * 删除code
     *
     * @param phone
     * @param session
     * @return
     */
    public String deleteCode(String phone, HttpSession session) {
        PhoneCodeEntity phonecode = (PhoneCodeEntity) session.getAttribute(phone);
        if (phonecode == null) {
            //清除成功
            System.out.println("清除成功");
            return "1";
        } else {
            try {
                session.removeAttribute(phone);
                return "1";
            } catch (Exception e) {
                //清除失败
                System.out.println("清除失败");
                System.out.println(e.getMessage());
                return "2";

            }

        }
    }
    //重置密码获取验证码

    public String ResetGetCode(String phone, HttpSession session) {
        MD5Utils md5Utils = new MD5Utils();
        MallUserEntity loadByPhone = new MallUserEntity();
        //再次判断注册手机号已被注册（是否）
        try {
            loadByPhone = mallUserDao.loadByID(phone);
        } catch (Exception e) {
            return "3";
        }

        if (loadByPhone == null) {
            return "2";
        } else {
            //获取随机验证码，保存在session中
            CodeUtils codeUtils = new CodeUtils();
            PhoneCodeUtils phoneCodeUtils = new PhoneCodeUtils();

            //设置超时时间-可自行调整
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            //初始化ascClient需要的几个参数
            final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
            final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
            //替换成你的AK
            final String accessKeyId = CodeEnum.AccessId.getValue();//你的accessKeyId,参考本文档步骤2
            final String accessKeySecret = CodeEnum.AccessKeySecre.getValue();//你的accessKeySecret，参考本文档步骤2
            //获得随机验证码
            String usercode = codeUtils.createRandom(true, 6);
            //保存phone code到session中
            if (phoneCodeUtils.save(phone, usercode, session) == false) {
                //4代表获取验证码频繁
                return "4";
            }
            //初始化ascClient,暂时不支持多region（请勿修改）
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                    accessKeySecret);
            try {
                DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            } catch (ClientException e) {
                e.printStackTrace();
            }
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(CodeEnum.SignName.getValue());
            //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
            request.setTemplateCode(CodeEnum.SMSTemplateCode.getValue());
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            request.setTemplateParam("{\"code\":\"" + usercode + "\"}");
            //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            // request.setOutId("yourOutId");
            //请求失败这里会抛ClientException异常
            SendSmsResponse sendSmsResponse = null;
            try {
                sendSmsResponse = acsClient.getAcsResponse(request);
            } catch (ClientException e) {
                e.printStackTrace();
                //3未知错误
                return "3";
            }
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                //请求成功
                System.out.println("验证码发送成功！");
                return "1";
            }
        }
        return "3";
    }

    @Transactional
    public String ChangePassword(String newpwd, String code, String newphone, HttpSession session) {
        System.out.println("开始修改密码！");
        MD5Utils md5Utils = new MD5Utils();
        //再次判断注册手机号已被注册（是否）
        MallUserEntity loadByPhone = mallUserDao.loadByID(newphone);
        if (loadByPhone == null) {
            //2代表手机未被注册注册
            return "2";
        } else {

            PhoneCodeEntity phoneCode = (PhoneCodeEntity) session.getAttribute(newphone);
            if (phoneCode == null) {
                //4系统异常
                return "5";
            }
//            System.out.println("phoneCode"+phoneCode);
//            System.out.println(newphone.equals(phoneCode.getPhone()));
            if (newphone.equals(phoneCode.getPhone()) == false) {
                //手机输入错误！
                return "6";
            }


            if (phoneCode.getCode() != null) {
                String mdCode;
                try {
                    mdCode = md5Utils.MD5Encode(code, "utf8");

                } catch (Exception e) {
                    throw new MDException(ExceptionEnum.MD5);

                }
                if (mdCode.equals(phoneCode.getCode())) {
                    if (mallUserDao.updatePwd(loadByPhone.getM_id(), newpwd)) {
                        //1成功
                        return "1";
                    }
                    return "4";
                } else {
                    //验证码错误
                    return "3";
                }
            } else {
                //4系统异常
                return "4";
            }
        }
    }


}
