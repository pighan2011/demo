package com.bootdo.dao.app;

import com.bootdo.vo.SymptonDetailsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 症状描述表
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:33:02
 */
@Mapper
public interface SymptonDetailsDao {

        SymptonDetailsDO get(Long id);

    List<SymptonDetailsDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(SymptonDetailsDO symptonDetails);

    int update(SymptonDetailsDO symptonDetails);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
