package com.bootdo.dao.base;

import com.bootdo.vo.AreaDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-13 11:19:00
 */
@Mapper
public interface AreaDao {

    AreaDO get(Integer id);

    List<AreaDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);


}
