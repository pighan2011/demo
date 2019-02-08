package com.bootdo.dao.app;

import com.bootdo.vo.BannerGroupDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 主页轮播图组
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-14 16:39:12
 */
@Mapper
public interface BannerGroupDao {

    BannerGroupDO get(Long id);

    List<BannerGroupDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(BannerGroupDO bannerGroup);

    int update(BannerGroupDO bannerGroup);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
