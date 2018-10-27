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
import com.nchhr.mall.Entity.TemporaryloginEntity;
import com.nchhr.mall.Entity.WeChatUserEntity;
import com.nchhr.mall.Enum.CodeEnum;
import com.nchhr.mall.Enum.ExceptionEnum;
import com.nchhr.mall.Exception.MDException;
import com.nchhr.mall.Utils.*;
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
           return GetCodeUtils.getCode(phone,session,CodeEnum.AccessId,CodeEnum.AccessKeySecre,CodeEnum.SignName,CodeEnum.SMSTemplateCode);

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
                    if (weChatUserEntity == null){
                        System.out.println("weChatUserEntity == null");
                        //注册失败
                        return "4";

                    }
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
            //该手机号已被注册
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
            return GetCodeUtils.getCode(phone,session,CodeEnum.AccessId,CodeEnum.AccessKeySecre,CodeEnum.SignName,CodeEnum.SMSTemplateCode);
        }

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
