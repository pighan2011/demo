package com.bootdo.dao.app;

import com.bootdo.vo.HospitalizationAccompanyDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 陪诊人
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-23 10:34:18
 */
@Mapper
public interface HospitalizationAccompanyDao {

        HospitalizationAccompanyDO get(Long id);

    List<HospitalizationAccompanyDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(HospitalizationAccompanyDO hospitalizationAccompany);

    int update(HospitalizationAccompanyDO hospitalizationAccompany);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
