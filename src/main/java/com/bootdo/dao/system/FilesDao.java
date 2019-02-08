package com.bootdo.dao.system;

import com.bootdo.vo.FilesDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 文件表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-13 15:55:24
 */
@Mapper
public interface FilesDao {

    FilesDO get(Long id);

    List<FilesDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(FilesDO files);

    int update(FilesDO files);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
