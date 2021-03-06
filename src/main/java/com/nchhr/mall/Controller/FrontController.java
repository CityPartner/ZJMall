package com.nchhr.mall.Controller;

import com.nchhr.mall.Entity.MallUserEntity;
import com.nchhr.mall.Entity.WeChatUserEntity;
import com.nchhr.mall.EntityVo.CommodityVo;
import com.nchhr.mall.Service.CommodityService;
import com.nchhr.mall.Service.WeChatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("")
public class FrontController {

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private WeChatUserService weChatUserService;


    @RequestMapping("/index")
    public ModelAndView toIndexPage(Model model) {
        List<CommodityVo> allCommodity = commodityService.findAllCommodity();
        model.addAttribute("CVList",allCommodity);
        return new ModelAndView("index","IndexModel",model);
    }

    @RequestMapping("/classify")
    public ModelAndView toClassfyPage(Model model){
        model.addAttribute("c1",commodityService.findCommodityByYid("001"));
        model.addAttribute("c2",commodityService.findCommodityByYid("002"));
        System.out.println(model.toString());
        return new ModelAndView("classify","classifyMode",model);
    }

    @RequestMapping("/pro_info")
    public ModelAndView toPro_infoPage(Model model, @RequestParam(value="id",required = true,defaultValue = "0001") String id){
        model.addAttribute("CV",commodityService.findCommodityVoById(id));
        return new ModelAndView("pro_info","ProModel",model);
    }


    @RequestMapping("/mine")
    public ModelAndView toMinePage(Model model, HttpSession session){
        MallUserEntity mallUserEntity= (MallUserEntity) session.getAttribute("MallUserInfo");
        WeChatUserEntity weChatUserEntity= weChatUserService.getUserByOpenid(mallUserEntity.getOpenid());
        model.addAttribute("WeChatUser",weChatUserEntity);
        model.addAttribute("MallUser",mallUserEntity);
        return new ModelAndView("mine","MineModel",model);
    }

}
