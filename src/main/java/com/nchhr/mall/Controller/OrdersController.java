package com.nchhr.mall.Controller;

import com.nchhr.mall.Entity.AddressEntity;
import com.nchhr.mall.Entity.CommodityEntity;
import com.nchhr.mall.Entity.CouponEntity;
import com.nchhr.mall.Entity.MallUserEntity;
import com.nchhr.mall.Service.AddressService;
import com.nchhr.mall.Service.CommodityService;
import com.nchhr.mall.Service.CouponService;
import com.nchhr.mall.Service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CouponService couponService;

    MallUserEntity mallUserEntity=null;

    /*
     *产生订单号
     * 返回1代表成功
     * 2表示没有选择收货人
     * 3订单产生成功，商品购买出错
     * 4订单产生出错
     */

    @RequestMapping("/GenerateOrder")
    @ResponseBody
    public int insertOrder(HttpServletRequest request){
        return ordersService.insertOrder(request);
    }

    @RequestMapping("/saveCommodityData")
    public void saveCommodityDataFun(@RequestParam(value = "CommodityData")String CommodityData,HttpServletRequest request,HttpServletResponse response){
        request.getSession().setAttribute("CommodityData",CommodityData);
        try {
            response.sendRedirect("/mall/orderConfirmPage");
        } catch (IOException e) {
            System.out.println("E:4324  跳转出错");
        }
    }

    @RequestMapping("orderConfirmPage")
    public ModelAndView toOrderPage(HttpServletRequest request, HttpServletResponse response, Model model){
        mallUserEntity= (MallUserEntity) request.getSession().getAttribute("MallUserInfo");
        String CommodityData=request.getSession().getAttribute("CommodityData").toString();

        String M_id=mallUserEntity.getM_id();
//        String M_id="#123";



        if(CommodityData==null){
            try {
                response.sendRedirect("/mall/shopCart");
            } catch (IOException e) {
                System.out.println("E:3213  跳转出错！");
            }
        }
        String[] commoditys=CommodityData.split(",");
        List<CommodityEntity> coms=new ArrayList<>();
        double totalAmount=0.0;
//        String temp="";

//        读取商品信息
        for (String codata:commoditys) {
            if(codata!=null) {
                String[] temp = codata.split(":");
                CommodityEntity commodityById = commodityService.findCommodityById(temp[0]);
                if(((Integer.parseInt(commodityById.getStock())-(Integer.parseInt(temp[1]))))>=0){
                    commodityById.setStock(temp[1]);
                }
                coms.add(commodityById);
                totalAmount += commodityById.getPrice()*(Integer.parseInt(commodityById.getStock()));

            }
        }
//        读取地址信息
        String Re_id=null;
        AddressEntity add=null;
        Object re_id1 = request.getSession().getAttribute("Re_id");
        if(re_id1==null){
            add = addressService.getDafaultAdd(M_id);
        }else{
            add=addressService.getByReid(re_id1.toString());
        }



        if(add==null){
            request.getSession().setAttribute("Re_id",null);
            model.addAttribute("address","NoAdd");
        }else{
            request.getSession().setAttribute("Re_id",add.getRe_id());
            model.addAttribute("address",add);
        }


//        读取优惠券信息
        String OFid=null;
        Object oFid = request.getSession().getAttribute("OFid");
        if(oFid==null){
            model.addAttribute("coupon","NoCoupon");
        }else{
            OFid=oFid.toString();
            CouponEntity coupon = couponService.getCouponByOfid(OFid);

//        使用优惠券
            if(totalAmount >= (Double.parseDouble(coupon.getCondition_use()))){
                if(coupon.getType().equals("1")){
                    totalAmount *= ((Double.parseDouble(coupon.getDiscount()))/10);
                }else if(coupon.getType().equals("2")){
                    totalAmount -= (Double.parseDouble(coupon.getAmount()));
                }else{
                    System.out.println("E:3101 优惠券使用出错");
                }
            }
            model.addAttribute("coupon",coupon);
        }

        request.getSession().setAttribute("totalAmount",totalAmount);
        model.addAttribute("commoditys",coms);
        model.addAttribute("totalAmount",totalAmount);
        return new ModelAndView("order_info2","OrderModel",model);
    }

}
