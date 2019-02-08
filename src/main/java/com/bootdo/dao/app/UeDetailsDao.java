package com.bootdo.dao.app;

import com.bootdo.vo.UeDetailsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 评价详情列表
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:33:10
 */
@Mapper
public interface UeDetailsDao {

        UeDetailsDO get(Long id);

    List<UeDetailsDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(UeDetailsDO ueDetails);

    int update(UeDetailsDO ueDetails);

    int remove(Long id);

    int batchRemove(Long[] ids);

}
