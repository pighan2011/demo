package com.bootdo.dao.app;

import com.bootdo.vo.UserAppointmentDoctorRelDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 患者预约医生表
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 18:00:12
 */
@Mapper
public interface UserAppointmentDoctorRelDao {

        UserAppointmentDoctorRelDO get(Long id);

    List<UserAppointmentDoctorRelDO> list(Map<String, Object> map);
    int count(Map<String, Object> map);

    int save(UserAppointmentDoctorRelDO userAppointmentDoctorRel);

    int update(UserAppointmentDoctorRelDO userAppointmentDoctorRel);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<UserAppointmentDoctorRelDO> waitUserList(Map<String, Object> map);

    int waitUserListCount(Map<String, Object> map);
}
