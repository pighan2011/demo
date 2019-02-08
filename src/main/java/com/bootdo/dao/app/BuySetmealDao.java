package com.bootdo.dao.app;

import com.bootdo.vo.BuySetmealDO;
import com.bootdo.vo.SetmealDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 购买套餐预约表
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-12-19 11:04:43
 */
@Mapper
public interface BuySetmealDao {

        BuySetmealDO get(Long id);

    List<BuySetmealDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(BuySetmealDO buySetmeal);

    int update(BuySetmealDO buySetmeal);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<SetmealDO> setmealList(Map<String, Object> map);
}
