package com.nchhr.mall.Service;

import com.nchhr.mall.Dao.CouponDao;
import com.nchhr.mall.Entity.CouponEntity;
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

    public CouponEntity getCouponByOfid(String OFid){
        return couponDao.getCouponById(OFid);
    }
}
