package com.nchhr.mall.Controller;

import com.nchhr.mall.Entity.CommodityEntity;
import com.nchhr.mall.Entity.MallUserEntity;
import com.nchhr.mall.Entity.ShopCartEntity;
import com.nchhr.mall.Service.CommodityService;
import com.nchhr.mall.Service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/shopCart")
public class ShopCartController {
    @Autowired
    private ShopCartService shopCartService;

    @Autowired
    private CommodityService commodityService;

    private MallUserEntity mallUserEntity=null;
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
        mallUserEntity= (MallUserEntity) request.getSession().getAttribute("MallUserInfo");
        int result=1;
        String SC_id=mallUserEntity.getSC_id();
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




    @RequestMapping("")
    public ModelAndView toShopcartPage(Model model, HttpServletRequest request){
//        Object SC_id=request.getSession().getAttribute("SC_id");

//        if(SC_id==null){
//            return new ModelAndView("login","loginModel",model);
//        }
        //测试！
        mallUserEntity= (MallUserEntity) request.getSession().getAttribute("MallUserInfo");

        String SC_id=mallUserEntity.getSC_id();
//        String SC_id="xs01";
//        System.out.println(SC_id);
        CommodityEntity commodityEntity;
//        String temp;
        List<ShopCartEntity> carts=shopCartService.findBySCid(SC_id);
        List<CommodityEntity> commodities=new ArrayList<>();
        for (ShopCartEntity cart : carts) {
            commodityEntity=commodityService.findCommodityById(cart.getC_id());
            commodityEntity.setAdd_time((new Integer(cart.getNumber())).toString());
//            System.out.println(commodityEntity.getAdd_time());
            commodities.add(commodityEntity);
        }
        model.addAttribute("commodities",commodities);
        return new ModelAndView("shopcart","shopcartModel",model);
    }

    @RequestMapping("/removeFromCart")
    @ResponseBody
    public int DeleteCart(@RequestParam(value = "C_id" ,required = true,defaultValue = "0001")String C_id, HttpServletRequest request){
//        String SC_id=request.getSession().getAttribute("SC_id").toString();
        mallUserEntity= (MallUserEntity) request.getSession().getAttribute("MallUserInfo");
        String SC_id= mallUserEntity.getSC_id();
//        System.out.println(
//                C_id
//        );
        shopCartService.deleteBySCidAndCid(SC_id,C_id);

        return 1;
    }

}
