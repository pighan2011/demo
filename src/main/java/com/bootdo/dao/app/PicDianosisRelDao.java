package com.bootdo.dao.app;

import com.bootdo.vo.PicDianosisRelDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 图片与就诊人关系表
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-12-03 10:56:38
 */
@Mapper
public interface PicDianosisRelDao {

        PicDianosisRelDO get(Long id);

    List<PicDianosisRelDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(PicDianosisRelDO picDianosisRel);

    int update(PicDianosisRelDO picDianosisRel);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
