package com.bootdo.dao.app;

import com.bootdo.vo.QuestionDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 满意度项配置
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:32:23
 */
@Mapper
public interface QuestionDao {

    QuestionDO get(Long id);

    List<QuestionDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(QuestionDO question);

    int update(QuestionDO question);

    int remove(Long id);

    int batchRemove(Long[] ids);

    @Update("update app_question set ${column}=${column}+1 where id=${id}")
    void modfiyCount(Map<String, Object> map);

}
