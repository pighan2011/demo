package com.bootdo.dao.user;

import com.bootdo.vo.DiagnosisCardDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 就诊卡信息
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:31:22
 */
@Mapper
public interface DiagnosisCardDao {

        DiagnosisCardDO get(Long id);

    List<DiagnosisCardDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(DiagnosisCardDO diagnosisCard);

    int update(DiagnosisCardDO diagnosisCard);

    int remove(Long id);

    int batchRemove(Long[] ids);
    List<DiagnosisCardDO> cardApplyList(Map<String, Object> map);

    int cardApplyListCount(Map<String, Object> map);
}
