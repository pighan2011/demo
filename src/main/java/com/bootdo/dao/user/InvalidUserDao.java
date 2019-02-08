package com.bootdo.dao.user;

import com.bootdo.vo.InvalidUserDO;
import com.bootdo.vo.UserAppointmentDoctorRelDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * app患者信息表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:46:35
 */
@Mapper
public interface InvalidUserDao {

    InvalidUserDO get(Long id);

    InvalidUserDO getUserByPhone(String phone);

    InvalidUserDO getUserByOpenId(String OpenId);

    List<InvalidUserDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(InvalidUserDO invalidUser);

    int update(InvalidUserDO invalidUser);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<UserAppointmentDoctorRelDO> myDoctorList(Map<String, Object> map);
}
