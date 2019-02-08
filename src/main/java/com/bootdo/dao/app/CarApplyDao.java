package com.bootdo.dao.app;

import com.bootdo.vo.CarApplyDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用车申请
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-12-25 10:34:09
 */
@Mapper
public interface CarApplyDao {

    CarApplyDO get(Long id);

    List<CarApplyDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(CarApplyDO carApply);

    int update(CarApplyDO carApply);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
