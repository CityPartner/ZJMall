package com.nchhr.mall.Controller;

import com.nchhr.mall.Entity.CommodityEntity;
import com.nchhr.mall.Entity.MallUserEntity;
import com.nchhr.mall.Entity.ShopCartEntity;
import com.nchhr.mall.Entity.WeChatUserEntity;
import com.nchhr.mall.Service.CommodityService;
import com.nchhr.mall.Service.ShopCartService;
import com.nchhr.mall.Service.WeChatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
        model.addAttribute("coList",commodityService.findAllCommodity());
        return new ModelAndView("index","coListModel",model);
    }

    @RequestMapping("/classify")
    public ModelAndView toClassfyPage(Model model){
        model.addAttribute("c1",commodityService.findCommodityByYid("001"));
        model.addAttribute("c2",commodityService.findCommodityByYid("002"));
        model.addAttribute("c3",commodityService.findCommodityByYid("003"));
        model.addAttribute("c4",commodityService.findCommodityByYid("004"));
        System.out.println(model.toString());
        return new ModelAndView("classify","classifyMode",model);
    }

    @RequestMapping("/pro_info")
    public ModelAndView toPro_infoPage(Model model, @RequestParam(value="id",required = true,defaultValue = "0001") String id){
        model.addAttribute("commodity",commodityService.findCommodityById(id));
        return new ModelAndView("pro_info","pro_infoModel",model);
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
