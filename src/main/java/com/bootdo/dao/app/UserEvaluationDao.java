package com.bootdo.dao.app;

import com.bootdo.vo.UserEvaluationDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户评价表
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:33:40
 */
@Mapper
public interface UserEvaluationDao {

        UserEvaluationDO get(Long id);

    List<UserEvaluationDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(UserEvaluationDO userEvaluation);

    int update(UserEvaluationDO userEvaluation);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
