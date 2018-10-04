package com.nchhr.mall.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class Test2Controller {


    //    CodeUtils codeUtils;
    //
    @RequestMapping("test")
    @ResponseBody
    public String index(HttpSession session) {
        System.out.println("123");
      return "123";
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

