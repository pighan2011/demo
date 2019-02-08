package com.bootdo.dao.app;

import com.bootdo.vo.AppointmentServiceDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 预约服务列表
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-12-12 10:33:01
 */
@Mapper
public interface AppointmentServiceDao {

    AppointmentServiceDO get(Long id);

    List<AppointmentServiceDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(AppointmentServiceDO appointmentService);

    int update(AppointmentServiceDO appointmentService);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<AppointmentServiceDO> asList(Map<String, Object> map);
}
