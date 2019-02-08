package com.bootdo.dao.app;

import com.bootdo.vo.SetmealDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 健康体检-套餐表
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:32:40
 */
@Mapper
public interface SetmealDao {

    SetmealDO get(Long id);

    List<SetmealDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(SetmealDO setmeal);

    int update(SetmealDO setmeal);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
