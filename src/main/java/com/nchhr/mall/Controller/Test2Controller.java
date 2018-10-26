package com.nchhr.mall.Controller;

import com.nchhr.mall.Entity.TemporaryloginEntity;
import com.nchhr.mall.Service.CookiesService;
import com.nchhr.mall.Utils.PhoneCodeUtils;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class Test2Controller {


    //    CodeUtils codeUtils;
    //
    @Autowired
    CookiesService cookiesService;



    @RequestMapping("/test")
    public boolean index(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        String userPhone = "13330113797";
        String code = "123456";
//     cookiesService.saveCookies("123",response,request);
        PhoneCodeUtils  phoneCodeUtils = new PhoneCodeUtils();
        phoneCodeUtils.save(userPhone,code,session);
//        TemporaryloginEntity temporaryloginEntity = new TemporaryloginEntity();
//        temporaryloginEntity.setCode(code);
//        temporaryloginEntity.setPhone(userPhone);
//        temporaryloginEntity.setPwd("a123456");
//        if (session.getAttribute("temporaryloginEntity") != null){
//            session.removeAttribute("temporaryloginEntity");
//        }
//        session.setAttribute("temporaryloginEntity",temporaryloginEntity);
//     return "redirect:/wechatuser?userPhone="+userPhone+"&code="+code+"&pwd=a123456";
        return true;
    }



//    @RequestMapping("test2")
//    @ResponseBody
//    public String index2(HttpSession session) {
//        MallUser mallUser =
//        return Mid;
//    }
//    @Privilege("查找全部女生")
//    @GetMapping("/loadgirl")
//    @ResponseBody
//    public R_data<List<T_girl>> loadgirl() throws Exception {
//
//        return ResultUtils.success(testServive.loadGirl(), ExceptionEnum.SUCCESS);
//    }
//    //跳转到个人中心界面
//    @Privilege("查找全部女生")
//    @RequestMapping(value = "{pageName2}")
//    public ModelAndView toPage(@PathVariable("pageName2") String pageName2) {
//        ModelAndView mv = new ModelAndView(pageName2);
//        return mv;
//    }


}

