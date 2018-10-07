package com.nchhr.mall.Controller;

import com.nchhr.mall.Entity.AddressEntity;
import com.nchhr.mall.Entity.MallUserEntity;
import com.nchhr.mall.Service.AddressService;
import com.nchhr.mall.Utils.Generate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.lang.String;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("")
public class AddressController {

    @Autowired
    private AddressService addressService;

    MallUserEntity mallUserEntity=null;

    @RequestMapping("/addressList")
    public ModelAndView toAddressListPage(Model model, HttpServletRequest request){
        mallUserEntity= (MallUserEntity) request.getSession().getAttribute("MallUserInfo");
        String M_id=mallUserEntity.getM_id();
//        String M_id="#123";

        model.addAttribute("AddList",addressService.getAllAddressByMid(M_id));

        return new ModelAndView("address_list","addressListModel",model);
    }

    @RequestMapping("/AddressChoose")
    public ModelAndView toAddressChoosePage(Model model, HttpServletRequest request){
        mallUserEntity= (MallUserEntity) request.getSession().getAttribute("MallUserInfo");
        String M_id=mallUserEntity.getM_id();
//        String M_id="#123";

        model.addAttribute("AddList",addressService.getAllAddressByMid(M_id));

        return new ModelAndView("address_choose","Model",model);
    }


    @RequestMapping("/AddAddress")
    @ResponseBody
    public int addAddress(@RequestParam(value = "name",required = true)String name,
                          @RequestParam(value = "phone",required = true)String phone,
                          @RequestParam(value = "acquiescence",required = true)String acquiescence,
                          @RequestParam(value = "address",required = true)String address,
                          @RequestParam(value = "address_details",required = true)String address_details,
                          HttpServletRequest request){
        mallUserEntity= (MallUserEntity) request.getSession().getAttribute("MallUserInfo");
        String M_id=mallUserEntity.getM_id();
//        String M_id="#123";
        String Re_id= Generate.getTime();
        String[] strings = address.split(" ");
        String province=strings[0];
        String city=strings[1];
        String county=strings[2];
        AddressEntity addressEntity=new AddressEntity(Re_id,name,phone,acquiescence,province,city,county,address_details,M_id);

        return addressService.save(addressEntity);
    }

    @RequestMapping("/EditAddress")
    public ModelAndView toAddressEditPage(Model model,@RequestParam(value = "Re_id",required = true)String Re_id){
        model.addAttribute("Address",addressService.getByReid(Re_id));
        return new ModelAndView("address_edit","EditModel",model);
    }

    @RequestMapping("/UpdateAddress")
    @ResponseBody
    public int updateAddress(@RequestParam(value = "Re_id",required = true)String Re_id,
                             @RequestParam(value = "name",required = true)String name,
                             @RequestParam(value = "phone",required = true)String phone,
                             @RequestParam(value = "acquiescence",required = true)String acquiescence,
                             @RequestParam(value = "address",required = true)String address,
                             @RequestParam(value = "address_details",required = true)String address_details,
                             HttpServletRequest request){
        mallUserEntity= (MallUserEntity) request.getSession().getAttribute("MallUserInfo");
        String M_id=mallUserEntity.getM_id();
//        String M_id="#123";
//        String Re_id= Generate.getTime();
        String[] strings = address.split(" ");
        String province=strings[0];
        String city=strings[1];
        String county=strings[2];
        AddressEntity addressEntity=new AddressEntity(Re_id,name,phone,acquiescence,province,city,county,address_details,M_id);

        addressService.upadte(addressEntity);
        return 1;
    }

    @RequestMapping("/DeleteAddress")
    @ResponseBody
    public int  deleteAddress(@RequestParam(value = "Re_id")String Re_id){
        try
        {
            addressService.deleteAddByReid(Re_id);
        }catch (Exception e){
            return 2;
        }
        return 1;
    }

    @RequestMapping("/SetDefaultAddress")
    @ResponseBody
    public int setDafaultAddressByReid(@RequestParam(value = "Re_id")String Re_id,HttpServletRequest request){
        mallUserEntity= (MallUserEntity) request.getSession().getAttribute("MallUserInfo");
        String M_id=mallUserEntity.getM_id();
//        String M_id="#123";
        addressService.setDefaultAdd(Re_id,M_id);
        return 1;
    }

    @RequestMapping("/SetAddress")
    public void setAddressAndToOrderPage(@RequestParam(value = "Re_id",required = true)String Re_id,
                                         HttpServletRequest request,
                                         HttpServletResponse response){
        request.getSession().setAttribute("Re_id",Re_id);
        try {
            response.sendRedirect("/mall/orderConfirmPage");
        }catch (IOException e){
            System.out.println("E:2202  重定向到order_info2.html失败！");
        }
    }
}
