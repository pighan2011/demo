package com.bootdo.dao.app;

import com.bootdo.vo.SdSicknessRelDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 穷举症状详情对应疾病表
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:32:30
 */
@Mapper
public interface SdSicknessRelDao {

        SdSicknessRelDO get(Long id);

    List<SdSicknessRelDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(SdSicknessRelDO sdSicknessRel);

    int update(SdSicknessRelDO sdSicknessRel);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
