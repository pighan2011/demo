package com.bootdo.dao.app;

import com.bootdo.vo.SymptomDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 症状表
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:32:53
 */
@Mapper
public interface SymptomDao {

        SymptomDO get(Long id);

    List<SymptomDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(SymptomDO symptom);

    int update(SymptomDO symptom);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
