package com.bootdo.dao.app;

import com.bootdo.vo.ApplyServiceDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 申请会诊表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-12-12 10:33:20
 */
@Mapper
public interface ApplyServiceDao {

     ApplyServiceDO get(Long id);

    List< ApplyServiceDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(ApplyServiceDO applyService);

    int update(ApplyServiceDO applyService);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
