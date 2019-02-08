package com.bootdo.dao.app;

import com.bootdo.vo.TodayEnjoinDO;
import com.bootdo.vo.UserAppointmentDoctorRelDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 今日医嘱
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-12-21 14:33:29
 */
@Mapper
public interface TodayEnjoinDao {

    TodayEnjoinDO get(Long id);

    List<TodayEnjoinDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(TodayEnjoinDO todayEnjoin);

    int update(TodayEnjoinDO todayEnjoin);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<UserAppointmentDoctorRelDO> diagonsisList(Map<String, Object> map);

    int diagonsisListCount(Map<String, Object> map);

}
