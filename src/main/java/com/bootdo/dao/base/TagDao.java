package com.bootdo.dao.base;

import com.bootdo.vo.TagDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 标签信息表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-13 21:18:55
 */
@Mapper
public interface TagDao {

    TagDO get(Long id);

    List<TagDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(TagDO tag);

    int update(TagDO tag);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
