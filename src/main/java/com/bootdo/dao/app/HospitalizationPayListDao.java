package com.bootdo.dao.app;

import com.bootdo.vo.HospitalizationPayListDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 住院缴费清单
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-26 16:52:02
 */
@Mapper
public interface HospitalizationPayListDao {

        HospitalizationPayListDO get(Long id);

    List<HospitalizationPayListDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(HospitalizationPayListDO hospitalizationPayList);

    int update(HospitalizationPayListDO hospitalizationPayList);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
