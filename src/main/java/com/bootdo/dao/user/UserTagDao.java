package com.bootdo.dao.user;

import com.bootdo.vo.UserTagDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户标签关系表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-13 21:19:42
 */
@Mapper
public interface UserTagDao {

    UserTagDO get(Long id);

    List<UserTagDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(UserTagDO userTag);

    int update(UserTagDO userTag);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
