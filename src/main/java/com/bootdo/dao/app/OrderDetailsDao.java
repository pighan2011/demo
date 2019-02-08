package com.bootdo.dao.app;

import com.bootdo.vo.OrderDetailsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 门诊缴费单详情
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:32:01
 */
@Mapper
public interface OrderDetailsDao {

        OrderDetailsDO get(Long id);

    List<OrderDetailsDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(OrderDetailsDO orderDetails);

    int update(OrderDetailsDO orderDetails);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
