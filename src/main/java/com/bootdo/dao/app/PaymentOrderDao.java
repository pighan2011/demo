package com.bootdo.dao.app;

import com.bootdo.vo.PaymentOrderDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 门诊缴费单
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:32:15
 */
@Mapper
public interface PaymentOrderDao {

        PaymentOrderDO get(Long id);

    List<PaymentOrderDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(PaymentOrderDO paymentOrder);

    int update(PaymentOrderDO paymentOrder);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<PaymentOrderDO> orderList(Map<String, Object> map);

    int orderListCount(Map<String, Object> map);

}
