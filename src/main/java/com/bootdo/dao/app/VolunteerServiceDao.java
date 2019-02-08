package com.bootdo.dao.app;

import com.bootdo.vo.VolunteerServiceDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 上门服务
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-12-12 10:32:39
 */
@Mapper
public interface VolunteerServiceDao {

        VolunteerServiceDO get(Long id);

    List<VolunteerServiceDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(VolunteerServiceDO volunteerService);

    int update(VolunteerServiceDO volunteerService);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
