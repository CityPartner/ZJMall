package com.nchhr.mall.controller;

import com.nchhr.mall.entity.CommodityEntity;
import com.nchhr.mall.entity.ShopCartEntity;
import com.nchhr.mall.service.CommodityService;
import com.nchhr.mall.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class TestController {

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private ShopCartService shopCartService;

    @RequestMapping("/index")
    @ResponseBody
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

    /*
     *添加购物车
     * 如果购物车已存在该商品，则更新数量
     * 不存在则添加一条新纪录
     */
    @RequestMapping("/addToCart")
    @ResponseBody
    public int addToCart(@RequestParam(value = "BuyCount",required = true,defaultValue = "1") int BuyCount,
                         @RequestParam(value = "C_id",required = true,defaultValue = "0001") String C_id,
                         HttpServletRequest request){
//        HttpSession session=request.getSession();
////        String SC_id=session.getAttribute("U_id").toString();
        int result=1;
        String SC_id="xs01";
        ShopCartEntity newCart=new ShopCartEntity();
        newCart.setSC_id(SC_id);
        newCart.setC_id(C_id);
        newCart.setNumber(BuyCount);
        newCart.setAdd_time(null);
        ShopCartEntity oldCart=shopCartService.findBySCidAndCid(SC_id,C_id);
//        shoppingcart_commodity shoppingcart_commodity=cartDao.findById(shopCartKey).get();
        if(null==oldCart){
            shopCartService.save(newCart);
        }else
        {
            newCart.setNumber(oldCart.getNumber()+newCart.getNumber());
            shopCartService.update(newCart);
        }

        return result;
    }

    @RequestMapping("/shopcart")
    public ModelAndView toShopcartPage(Model model, HttpServletRequest request){
//        Object SC_id=request.getSession().getAttribute("SC_id");

//        if(SC_id==null){
//            return new ModelAndView("login","loginModel",model);
//        }
        //测试！
        String SC_id="xs01";
//        System.out.println(SC_id);
        CommodityEntity commodityEntity;
//        String temp;
        List<ShopCartEntity> carts=shopCartService.findBySCid(SC_id);
        List<CommodityEntity> commodities=new ArrayList<>();
        for (ShopCartEntity cart : carts) {
            commodityEntity=commodityService.findCommodityById(cart.getC_id());
            commodityEntity.setAdd_time((new Integer(cart.getNumber())).toString());
            commodities.add(commodityService.findCommodityById(cart.getC_id()));
        }
        model.addAttribute("commodities",commodities);
        return new ModelAndView("shopcart","shopcartModel",model);
    }




    // TODO 此控制类仅用作测试静态资源 切勿在此添加代码逻辑



    @RequestMapping(value = "/{resourseName}")
    public ModelAndView index(@PathVariable("resourseName") String resourseName) {
        ModelAndView modelAndView = new ModelAndView(resourseName);
        return modelAndView;
    }

}
