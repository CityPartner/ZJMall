package com.nchhr.mall.service;

import com.nchhr.mall.dao.CouponDao;
import com.nchhr.mall.entity.CouponEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CouponService {

    @Resource
    private CouponDao couponDao;

    public List<CouponEntity> getCoupons(String userId, String state) {
        return couponDao.getCoupons(userId, state);
    }
}
