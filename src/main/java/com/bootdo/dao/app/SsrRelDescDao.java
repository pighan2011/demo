package com.bootdo.dao.app;

import com.bootdo.vo.SsrRelDescDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 穷举疾病详细关系说明表
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-26 21:04:31
 */
@Mapper
public interface SsrRelDescDao {

        SsrRelDescDO get(Long id);

    List<SsrRelDescDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(SsrRelDescDO ssrRelDesc);

    int update(SsrRelDescDO ssrRelDesc);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<SsrRelDescDO> srdList(Map<String, Object> map);

    int srdListCount(Map<String, Object> map);

}
