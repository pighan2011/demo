package com.bootdo.dao.app;

import com.bootdo.vo.HospitalizationApplyDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 申请住院
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-23 10:20:41
 */
@Mapper
public interface HospitalizationApplyDao {

        HospitalizationApplyDO get(Long id);

    List<HospitalizationApplyDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(HospitalizationApplyDO hospitalizationApply);

    int update(HospitalizationApplyDO hospitalizationApply);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<HospitalizationApplyDO> hosApplyList(Map<String, Object> map);

    int hosApplyListCount(Map<String, Object> map);
}
