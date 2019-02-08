package com.bootdo.controller.system;

import com.bootdo.controller.BaseController;
import com.bootdo.dao.app.UserAppointmentDoctorRelDao;
import com.bootdo.dao.base.TagDao;
import com.bootdo.dao.system.DeptDao;
import com.bootdo.dao.system.SuggestionDao;
import com.bootdo.dao.system.UserDao;
import com.bootdo.dao.user.DiagnosisCardDao;
import com.bootdo.dao.user.InvalidUserDao;
import com.bootdo.dao.user.UserTagDao;
import com.bootdo.util.DistanceUtil;
import com.bootdo.util.JWTUtil;
import com.bootdo.vo.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sys")
public class DeptController extends BaseController {

    @Resource
    DeptDao deptDao;
    @Resource
    DiagnosisCardDao diagnosisCardDao;
    @Resource
    TagDao tagDao;
    @Resource
    UserTagDao userTagDao;
    @Resource
    InvalidUserDao invalidUserDao;
    @Resource
    UserDao userDao;
    @Resource
    UserAppointmentDoctorRelDao userAppointmentDoctorRelDao;
    @Resource
    SuggestionDao suggestionDao;

    //根据地区id获取医院
//    @PostMapping("/getHospitalByAreaId/{areaId}/{lon}/{lat}/{hospitalName}")
    @RequestMapping(value = {"/getHospitalByAreaId/{areaId}/{lon}/{lat}", "/getHospitalByAreaId/{areaId}/{lon}/{lat}/{hospitalName}"}, method = RequestMethod.POST)
    public ResponseDO saveDc(@PathVariable(required = false) String areaId, @PathVariable Double lon, @PathVariable Double lat, @PathVariable(required = false) String hospitalName, HttpServletRequest request) {
        List<DeptDO> list = null;
        try {
            map.clear();
            map.put("areaId", areaId);
            map.put("parentId", 0);
            map.put("flag", 1);
            if (hospitalName != null) {
                map.put("hospitalName", "%" + hospitalName + "%");
            }
            list = deptDao.list(map);
            list.forEach(deptDO -> {
                if (deptDO.getMapCoordinates() != null) {
                    String[] mapcoordinates = deptDO.getMapCoordinates().split(",");
                    double distance = DistanceUtil.getDistance(Double.valueOf(mapcoordinates[0]),
                            Double.valueOf(mapcoordinates[1]),
                            lon, lat);
                    deptDO.setDistance(distance);
                    deptDO.setLon(Double.valueOf(mapcoordinates[0]));
                    deptDO.setLat(Double.valueOf(mapcoordinates[1]));
                }

                if (deptDO.getLogo() != null) {
                    deptDO.setFilesDO(getFilesVo(Long.valueOf(deptDO.getLogo())));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
        map.clear();
        map.put("hosList", list);
        return new ResponseDO(200, "请求成功", map);
    }

    // 获取当前选择所在医院所有一级科室
    @PostMapping("/getSections/{pid}")
    public ResponseDO getSections(@PathVariable String pid, HttpServletRequest request) {

        try {
            map.clear();
            map.put("parentId", pid);
            List<DeptDO> list = deptDao.list(map);
            map.clear();
            map.put("sectionsList", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
        return new ResponseDO(200, "请求成功", map);
    }

    //    获取该科室下所有医生，用于预约医生
    @RequestMapping(value = {"/getDocterBySections/{deptId}", "/getDocterBySections/{deptId}/{appointmentTime}"}, method = RequestMethod.POST)
    public ResponseDO getDocterBySections(@PathVariable String deptId, @PathVariable(required = false) String appointmentTime, HttpServletRequest request) {

        try {
            map.clear();
            map.put("deptId", deptId);
            map.put("appointmentCost", "1");
            map.put("userType", "0");
            List<UserDO> list = userDao.list(map);
            list.forEach(userDO -> {
                map.clear();
                if (appointmentTime == null) {
                    map.put("appointmentTime", getSdf().format(new Date()));
                } else {
                    map.put("appointmentTime", appointmentTime);
                }
                map.put("doctorId", userDO.getUserId());
                if (userAppointmentDoctorRelDao.list(map).size() == userDO.getMaxAppointment()) {
                    userDO.setAppointmentFlag(0);
                } else {
                    userDO.setAppointmentFlag(1);
                }
                if (userDO.getPicId() != null) {
                    userDO.setFilesDO(getFilesVo(Long.valueOf(userDO.getPicId())));
                }
                userDO.setToday(appointmentTime == null ? getSdf().format(new Date()) : appointmentTime);
            });
            map.clear();
            map.put("doctorList", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
        return new ResponseDO(200, "请求成功", map);
    }

    //    获取所有身份为专家的医生
    @PostMapping("/specialDoctorList/{deptId}")
    public ResponseDO specialDoctorList(@PathVariable String deptId, HttpServletRequest request) {
        try {
            map.clear();
            map.put("deptId", deptId);
            map.put("userType", "1");
            List<UserDO> list = userDao.list(map);
            list.forEach(userDO -> {
                if (userDO.getPicId() != null) {
                    userDO.setFilesDO(getFilesVo(Long.valueOf(userDO.getPicId())));

                }
            });
            map.clear();
            map.put("myDoctorList", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
        return new ResponseDO(200, "请求成功", map);
    }

    //    医生详情
    @PostMapping("/doctorDetails/{userId}")
    public ResponseDO doctorDetails(@PathVariable long userId, HttpServletRequest request) {

        try {
            UserDO userDO = userDao.get(userId);
            if (userDO.getPicId() != null) {
                userDO.setFilesDO(getFilesVo(Long.valueOf(userDO.getPicId())));
            }
//            查询用户标签
            map.clear();
            map.put("userId", userDO.getUserId());
            List<UserTagDO> list = userTagDao.list(map);
            if (list.size() > 0) {
                List<String> list1 = new ArrayList<>();
                list.forEach(userTagDO -> list1.add(tagDao.get(userTagDO.getTagId()).getTagName()));
                userDO.setTagsArray(list1);
            }
            map.clear();
            map.put("doctorInfo", userDO);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
        return new ResponseDO(200, "请求成功", map);
    }

    //医院的详细信息
    @PostMapping("/hospitalInfo/{deptId}")
    public ResponseDO hospitalInfo(@PathVariable long deptId, HttpServletRequest request) {

        try {
            DeptDO d = deptDao.get(deptId);
            d.setFilesDO(getFilesVo(Long.valueOf(d.getLogo())));
            map.clear();
            map.put("hospitalInfo", d);
            return new ResponseDO(200, "请求成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }

    //就诊卡的详细信息
    @PostMapping("/diagnosisInfo/{diagnosisId}")
    public ResponseDO diagnosisInfo(@PathVariable long diagnosisId, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            DiagnosisCardDO diagnosisCardDO = diagnosisCardDao.get(diagnosisId);
            map.clear();
            map.put("diagnosisInfo", diagnosisCardDO);
            return new ResponseDO(200, "请求成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }

    // 获取当前选择所在医院所有一级科室和二级科室
    @RequestMapping(value = {"/getAllSections/{pid}", "/getAllSections/{pid}/{deptName}"}, method = RequestMethod.POST)
    public ResponseDO getAllSections(@PathVariable String pid, @PathVariable(required = false) String deptName, HttpServletRequest request) {

        try {
            map.clear();
            map.put("parentId", pid);
            List<DeptDO> list = deptDao.list(map);
            list.forEach(deptDO -> {
                map.clear();
                map.put("parentId", deptDO.getDeptId());
                if (deptName != null) {
                    map.put("hospitalName", "%" + deptName + "%");
                }
                List<DeptDO> list1 = deptDao.list(map);
                deptDO.setSonDeptList(list1);
            });
            map.clear();
//            当前端模糊搜索的时候，便不需要一级类目，拼接所有搜索出来的二级类目
            if (deptName != null) {
                List<DeptDO> list1 = new ArrayList<>();
                list.forEach(deptDO -> {
                    if (deptDO.getSonDeptList().size() > 0) {
                        list1.addAll(deptDO.getSonDeptList());
                    }
                });
                map.put("sectionsList", list1);
            } else {
                map.put("sectionsList", list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
        return new ResponseDO(200, "请求成功", map);
    }

    // 医院详情
    @PostMapping("/hostpitalDetails/{deptId}")
    public ResponseDO hostpitalDetails(@PathVariable Long deptId, HttpServletRequest request) {
        try {
            map.put("hostpitalDetails", deptDao.get(deptId));

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
        return new ResponseDO(200, "请求成功", map);
    }

    // 新增意见反馈
    @PostMapping("/saveSuggestion")
    public ResponseDO saveSuggestion(SuggestionDO suggestionDO, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            suggestionDO.setCreateDate(new Date());
            if (suggestionDao.save(suggestionDO) > 0) {
                return new ResponseDO(200, "请求成功", suggestionDO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
        return new ResponseDO(500, "服务器错误", null);

    }
}
