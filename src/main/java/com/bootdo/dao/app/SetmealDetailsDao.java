package com.bootdo.dao.app;

import com.bootdo.vo.SetmealDetailsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 套餐详细项目
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:32:46
 */
@Mapper
public interface SetmealDetailsDao {

    SetmealDetailsDO get(Long id);

    List<SetmealDetailsDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(SetmealDetailsDO setmealDetails);

    int update(SetmealDetailsDO setmealDetails);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
