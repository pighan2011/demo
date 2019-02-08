package com.bootdo.dao.app;

import com.bootdo.vo.BedInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 床位信息
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-12-12 10:33:11
 */
@Mapper
public interface BedInfoDao {

        BedInfoDO get(Long id);

    List<BedInfoDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(BedInfoDO bedInfo);

    int update(BedInfoDO bedInfo);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
