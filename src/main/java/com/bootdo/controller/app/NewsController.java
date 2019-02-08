package com.bootdo.controller.app;

import com.bootdo.controller.BaseController;
import com.bootdo.dao.app.NewsDao;
import com.bootdo.vo.NewsDO;
import com.bootdo.vo.ResponseDO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController extends BaseController {


    @Resource
    NewsDao newsDao;


    //    获取新闻列表
    @PostMapping("/newsList/{deptId}")
    public ResponseDO newsList(@PathVariable Long deptId, HttpServletRequest request) {

        try {
            map.clear();
            map.put("deptId",deptId);
            List<NewsDO> list = newsDao.list(map);
            list.forEach(newsDO -> newsDO.setFilesDO(getFilesVo(newsDO.getHomePage())));
            map.clear();
            map.put("newsList", list);
            return new ResponseDO(200, "请求成功", map);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }

    //    新闻详情
    @PostMapping("/newsDetails/{newsId}")
    public ResponseDO newsDetails(@PathVariable Long newsId, HttpServletRequest request) {

        try {
            NewsDO newsDO = newsDao.get(newsId);
            newsDO.setFilesDO(getFilesVo(newsDO.getHomePage()));
            map.clear();
            map.put("newsDetails", newsDO);
            return new ResponseDO(200, "请求成功", map);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }
}
