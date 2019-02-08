package com.bootdo.dao.app;

import com.bootdo.vo.OutHospitalDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 出院
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2019-01-16 11:25:03
 */
@Mapper
public interface OutHospitalDao {

        OutHospitalDO get(Long id);

    List<OutHospitalDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(OutHospitalDO outHospital);

    int update(OutHospitalDO outHospital);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
