package com.bootdo.dao.app;

import com.bootdo.vo.AskDiagnoseDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 问诊表
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2019-01-08 15:56:06
 */
@Mapper
public interface AskDiagnoseDao {

        AskDiagnoseDO get(Long id);

    List<AskDiagnoseDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(AskDiagnoseDO askDiagnose);

    int update(AskDiagnoseDO askDiagnose);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
