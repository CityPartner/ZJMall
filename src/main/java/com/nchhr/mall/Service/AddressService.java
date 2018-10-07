package com.nchhr.mall.Service;
/*
收货人地址服务类
HWG
 */
import com.nchhr.mall.Dao.AddressDao;
import com.nchhr.mall.Entity.AddressEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AddressService {

    @Resource
    private AddressDao addressDao;

//    取得一个用户保存的所有收货地址
    public List<AddressEntity> getAllAddressByMid(String M_id){
        return addressDao.getAllAddressByMid(M_id);
    }
//    取得用户已保存的收货地址的数量
    public int getAddressCountByMid(String M_id){
        return addressDao.getAddressCountByMid(M_id);
    }

//    删除一个收货地址
    public void deleteAddByReid(String Re_id){
        addressDao.deleteAddByReid(Re_id);
    }

//    设置默认地址
    public void setDefaultAdd(String Re_id,String M_id){
        addressDao.setAllNotDefaust(M_id);
        addressDao.setDefaultAdd(Re_id);
    }
    //    取得默认地址
    public AddressEntity getDafaultAdd(String M_id){
        return addressDao.getDefaultAddress(M_id);
    }




//    通过Reid取得一个地址
    public AddressEntity getByReid(String Re_id){
        return addressDao.getByReid(Re_id);
    };
    /*
     *添加一条收货地址
     * 成功返回1
     * 失败返回-1（数量超过12条）
     */
    public int save(AddressEntity addressEntity){
        int num=addressDao.getAddressCountByMid(addressEntity.getM_id());
        if(num>=12){
            return -1;
        }
        if(addressEntity.getAcquiescence().equals("是"))
            addressDao.setAllNotDefaust(addressEntity.getM_id());
        addressDao.addAddress(addressEntity);
        return 1;
    }


//    更新一个地址
    public void upadte(AddressEntity addressEntity){
        if(addressEntity.getAcquiescence().equals("是"))
            addressDao.setAllNotDefaust(addressEntity.getM_id());
        addressDao.updateAddress(addressEntity);
    }


}
