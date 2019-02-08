package com.bootdo.dao.system;

import com.bootdo.vo.SuggestionDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 意见反馈
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-12-18 16:25:25
 */
@Mapper
public interface SuggestionDao {

        SuggestionDO get(Long id);

    List<SuggestionDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(SuggestionDO suggestion);

    int update(SuggestionDO suggestion);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
