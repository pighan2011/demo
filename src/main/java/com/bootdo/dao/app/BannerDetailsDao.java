package com.bootdo.dao.app;

import com.bootdo.vo.BannerDetailsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 轮播图详情
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-14 16:39:10
 */
@Mapper
public interface BannerDetailsDao {

    BannerDetailsDO get(Long id);

    List<BannerDetailsDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(BannerDetailsDO bannerDetails);

    int update(BannerDetailsDO bannerDetails);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
