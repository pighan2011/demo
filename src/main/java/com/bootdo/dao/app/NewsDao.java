package com.bootdo.dao.app;

import com.bootdo.vo.NewsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 医院咨询
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:31:54
 */
@Mapper
public interface NewsDao {

        NewsDO get(Long id);

    List<NewsDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(NewsDO news);

    int update(NewsDO news);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
