package com.bootdo.dao.app;

import com.bootdo.vo.PartOfBodyDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 部位表
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:32:08
 */
@Mapper
public interface PartOfBodyDao {

        PartOfBodyDO get(Long id);

    List<PartOfBodyDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(PartOfBodyDO partOfBody);

    int update(PartOfBodyDO partOfBody);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
