package com.spring_kajarta_frontstage.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.spring_kajarta_frontstage.service.CarService;
import com.spring_kajarta_frontstage.service.EmployeeService;
import com.spring_kajarta_frontstage.service.KpiService;
import com.spring_kajarta_frontstage.service.ViewCarService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kajarta.demo.enums.ViewTimeSectionEnum;
import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Kpi;
import com.kajarta.demo.model.ViewCar;
import com.kajarta.demo.vo.EmployeeVO;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

@RestController
@RequestMapping("/front/viewCar")
@CrossOrigin
public class ViewCarController {
    @Autowired
    private ViewCarService viewCarService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CarService carService;

    @Autowired
    private KpiService kpiService;

// 找最新的ID
    @GetMapping("/latestId")
    public String findLatestViewCarId() {
        JSONObject responseBody = new JSONObject();
        ViewCar latestViewCar = viewCarService.findLatestViewCar();
        if (latestViewCar != null) {
            responseBody.put("success", true);
            responseBody.put("latestId", latestViewCar.getId());
        } else {
            responseBody.put("success", false);
            responseBody.put("message", "No ViewCar records found.");
        }
        return responseBody.toString();
    }


    // KPI運算
    @GetMapping("/KPI")
    public String updateKPI() {
        List<EmployeeVO> employeeVOsList = employeeService.findAll();
        JSONObject kpiFindJson = new JSONObject();
        JSONObject kpiModifyJson = new JSONObject();
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        Integer employeeId;
        Integer employeeType;
        Integer viewcarId;
        Integer salesScore;
        Integer totalScore = 0;
        Integer salesAvgScore;
        String result;
        Kpi kpiModify = new Kpi();
        for (EmployeeVO employeeVO : employeeVOsList) {
            employeeId = employeeVO.getId();
            employeeType = employeeVO.getAccountType();
            if (employeeType == 3) {
                List<ViewCar> viewcarList = viewCarService.findViewCarByEmployeeId(employeeId);
                Integer salesCount = 0;
                for (ViewCar viewcar : viewcarList) {
                    viewcarId = viewcar.getId();
                    salesScore = viewcar.getSalesScore();// 只會有大於等於0
                    totalScore = totalScore + salesScore;// 計算總分
                    salesCount++;// 計算共幾台
                    // System.out.println("銷售員:" + employeeId + "負責car:" + viewcar.getCar().getId()
                    // + "分數:" + salesScore + "共負責:" + salesCount + "台");
                }
                if (salesCount != 0) {
                    salesAvgScore = totalScore / salesCount;// 計算員工平均
                    // System.out.println("總分" + salesAvgScore);
                    System.out.println("員工ID:" + employeeId + "總分:" + salesAvgScore);
                    kpiFindJson.put("selectStrDay", "2024-07-01")
                            .put("selectEndDay", "2024-09-30")
                            .put("employeeId", employeeId)
                            .put("isPage", 0)
                            .put("dir", true)
                            .put("order", "id")
                            .put("max", 5);
                    Page<Kpi> kpiPage = kpiService.findByHQL(kpiFindJson.toString());
                    if (kpiPage.isEmpty()) {
                        return result = "page輸入錯誤";
                    }
                    List<Kpi> kpiList = kpiPage.getContent();
                    for (Kpi kpi : kpiList) {
                        Integer kpiId = kpi.getId();
                        Integer kpiTeamLeaderRating = kpi.getTeamLeaderRating();
                        BigDecimal kpiTotalScoreBD = new BigDecimal(salesAvgScore)
                                .add(new BigDecimal(kpiTeamLeaderRating));
                        if (kpiTotalScoreBD != BigDecimal.ZERO) {
                            BigDecimal avgKpiTotalScoreBD = kpiTotalScoreBD.divide(BigDecimal.valueOf(2),
                                    2,
                                    RoundingMode.HALF_UP);
                            kpiModifyJson.put("id", kpiId)
                                    .put("salesScore", salesAvgScore)
                                    .put("teamLeaderRating", kpiTeamLeaderRating)
                                    .put("totalScore", avgKpiTotalScoreBD);
                            System.out.println("員工ID:" + employeeId + " 平均分數為:" + avgKpiTotalScoreBD);
                            System.out.println("=========================");
                            kpiModify = kpiService.modify(kpiModifyJson.toString());
                            // responseBody.put("員工Id:", employeeId)
                            // .put("銷售平均分數:", salesAvgScore)
                            // .put("總平均分數:", avgKpiTotalScoreBD);
                            // array = array.put(responseBody);
                        } else {
                            kpiModify = null;
                            return result = "修改失敗";
                            // responseBody.put("false", "沒有成功修改");
                        }
                    }
                }
                // 重置totalScore
                totalScore = 0;

                // 重置salesCount
                salesCount = 0;
            }
        }
        // return responseBody.put("list", array).toString();
        return result = "成功";
    }

    // 計算數量
    @GetMapping("/count")
    public long count() {
        return viewCarService.count();
    }

    // 新增
    @PostMapping("/create")
    public String create(@RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        JSONObject obj = new JSONObject(body);
        ViewCar card = viewCarService.create(body);
        if (card == null) {
            responseBody.put("success", false);
            responseBody.put("message", "新增失敗");
        } else {
            responseBody.put("success", true);
            responseBody.put("message", "新增成功");
        }
        return responseBody.toString();
    }

    // 修改

    @PutMapping("/update/{id}")
    public String modify(@PathVariable Integer id, @RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        if (id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "Id是必要欄位");
        } else {
            if (!viewCarService.exists(id)) {
                responseBody.put("success", false);
                responseBody.put("message", "Id不存在");
            } else {
                ViewCar product = viewCarService.modify(body);
                if (product == null) {
                    responseBody.put("success", false);
                    responseBody.put("message", "修改失敗");
                } else {
                    responseBody.put("success", true);
                    responseBody.put("message", "修改成功");
                }
            }
        }
        return responseBody.toString();
    }

    // 查一
    @GetMapping("/select/{pk}")
    public String findById(@PathVariable(name = "pk") Integer id) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();

        ViewCar viewCar = viewCarService.findById(id);
        if (viewCar != null) {
            String viewCarDate = DatetimeConverter.toString(viewCar.getViewCarDate(), "yyyy-MM-dd");
            String createTime = DatetimeConverter.toString(viewCar.getCreateTime(), "yyyy-MM-dd");
            String updateTime = DatetimeConverter.toString(viewCar.getUpdateTime(), "yyyy-MM-dd");

            JSONObject obj = new JSONObject()

                    .put("id", viewCar.getId())
                    // .put("viewTimeSection", viewCar.getViewTimeSection())
                    .put("viewTimeSection", ViewTimeSectionEnum.getByCode(viewCar.getViewTimeSection()).getTimeRange())
                    .put("viewTimeSectionNb", viewCar.getViewTimeSection())
                    .put("car", viewCar.getCar().getId())
                    .put("salesScore", viewCar.getSalesScore())
                    .put("factoryScore", viewCar.getFactoryScore())
                    .put("viewCarDate", viewCarDate)
                    .put("carScore", viewCar.getCarScore())
                    .put("deal", viewCar.getDeal())
                    .put("customer", viewCar.getCustomer().getId())
                    .put("createTime", createTime)
                    .put("updateTime", updateTime)
                    .put("viewCarStatus", viewCar.getViewCarStatus());
            array = array.put(obj);
        }

        responseBody.put("list", array);
        return responseBody.toString();
    }

    // 查全
    @GetMapping("/selectAll")
    public String findByPage(@RequestParam Integer pageNumber, @RequestParam Integer max) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        Page<ViewCar> page = viewCarService.findByPage(pageNumber, max);
        List<ViewCar> viewCars = page.getContent();
        for (ViewCar viewCar : viewCars) {
            String viewCarDate = DatetimeConverter.toString(viewCar.getViewCarDate(), "yyyy-MM-dd");
            String createTime = DatetimeConverter.toString(viewCar.getCreateTime(), "yyyy-MM-dd");
            String updateTime = DatetimeConverter.toString(viewCar.getUpdateTime(), "yyyy-MM-dd");
            JSONObject obj = new JSONObject()
                    .put("id", viewCar.getId())
                    .put("viewTimeSection", ViewTimeSectionEnum.getByCode(viewCar.getViewTimeSection()).getTimeRange())
                    .put("car", viewCar.getCar().getId())
                    .put("modelName", viewCar.getCar().getCarinfo().getModelName())
                    .put("branch", viewCar.getCar().getBranch())
                    .put("salesScore", viewCar.getSalesScore())
                    .put("factoryScore", viewCar.getFactoryScore())
                    .put("viewCarDate", viewCarDate)
                    .put("carScore", viewCar.getCarScore())
                    .put("deal", viewCar.getDeal())
                    .put("customer", viewCar.getCustomer().getId())
                    .put("customerName", viewCar.getCustomer().getName())
                    .put("tel", viewCar.getCustomer().getTel())
                    .put("createTime", createTime)
                    .put("updateTime", updateTime)
                    .put("viewTimeSectionNb", viewCar.getViewTimeSection())
                    .put("viewCarStatus", viewCar.getViewCarStatus());
            array.put(obj);
        }
        responseBody.put("list", array);
        responseBody.put("totalPages", page.getTotalPages());
        responseBody.put("totalElements", page.getTotalElements());
        responseBody.put("currentPage", page.getNumber() + 1); // Page numbers are 0-based, so we add 1
        return responseBody.toString();
    }

    @GetMapping("/findPageByCustomerId")
    public String findPageByCustomerId(@RequestParam Integer customerId, @RequestParam Integer pageNumber,
            @RequestParam Integer max) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        Page<ViewCar> page = viewCarService.findPageByCustomerId(customerId, pageNumber, max);
        List<ViewCar> viewCars = page.getContent();
        for (ViewCar viewCar : viewCars) {
            String viewCarDate = DatetimeConverter.toString(viewCar.getViewCarDate(), "yyyy-MM-dd");
            String createTime = DatetimeConverter.toString(viewCar.getCreateTime(), "yyyy-MM-dd");
            String updateTime = DatetimeConverter.toString(viewCar.getUpdateTime(), "yyyy-MM-dd");
            JSONObject obj = new JSONObject()
                    .put("id", viewCar.getId())
                    .put("viewTimeSection", ViewTimeSectionEnum.getByCode(viewCar.getViewTimeSection()).getTimeRange())
                    .put("car", viewCar.getCar().getId())
                    .put("modelName", viewCar.getCar().getCarinfo().getModelName())
                    .put("branch", viewCar.getCar().getBranch())
                    .put("salesScore", viewCar.getSalesScore())
                    .put("factoryScore", viewCar.getFactoryScore())
                    .put("viewCarDate", viewCarDate)
                    .put("carScore", viewCar.getCarScore())
                    .put("deal", viewCar.getDeal())
                    .put("createTime", createTime)
                    .put("updateTime", updateTime)
                    .put("viewTimeSectionNb", viewCar.getViewTimeSection())
                    .put("viewCarStatus", viewCar.getViewCarStatus());
            array.put(obj);
        }
        responseBody.put("list", array);
        responseBody.put("totalPages", page.getTotalPages());
        responseBody.put("totalElements", page.getTotalElements());
        responseBody.put("currentPage", page.getNumber() + 1); // Page numbers are 0-based, so we add 1
        return responseBody.toString();
    }

    // 根据 customerId 查找所有 ViewCar
    @GetMapping("/findByCustomer/{customerId}")
    public String findByCustomerId(@PathVariable Integer customerId) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        List<ViewCar> viewCars = viewCarService.findByCustomerId(customerId);

        for (ViewCar viewCar : viewCars) {
            String viewCarDate = DatetimeConverter.toString(viewCar.getViewCarDate(), "yyyy-MM-dd");
            String createTime = DatetimeConverter.toString(viewCar.getCreateTime(), "yyyy-MM-dd");
            String updateTime = DatetimeConverter.toString(viewCar.getUpdateTime(), "yyyy-MM-dd");

            JSONObject obj = new JSONObject()
                    .put("id", viewCar.getId())
                    .put("viewTimeSection", ViewTimeSectionEnum.getByCode(viewCar.getViewTimeSection()).getTimeRange())
                    .put("car", viewCar.getCar().getId())
                    .put("modelName", viewCar.getCar().getCarinfo().getModelName())
                    .put("branch", viewCar.getCar().getBranch())
                    .put("salesScore", viewCar.getSalesScore())
                    .put("factoryScore", viewCar.getFactoryScore())
                    .put("viewCarDate", viewCarDate)
                    .put("carScore", viewCar.getCarScore())
                    .put("deal", viewCar.getDeal())
                    .put("customerName", viewCar.getCustomer().getName())
                    .put("tel", viewCar.getCustomer().getTel())
                    .put("createTime", createTime)
                    .put("updateTime", updateTime)
                    .put("viewCarStatus", viewCar.getViewCarStatus());
            array.put(obj);
        }
        responseBody.put("list", array);
        return responseBody.toString();
    }

    // 刪除
    @DeleteMapping("/delete/{id}")
    public String remove(@PathVariable Integer id) {
        JSONObject responseBody = new JSONObject();
        if (id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "Id是必要欄位");
        } else {
            if (!viewCarService.exists(id)) {
                responseBody.put("success", false);
                responseBody.put("message", "Id不存在");
            } else {
                if (!viewCarService.remove(id)) {
                    responseBody.put("success", false);
                    responseBody.put("message", "刪除失敗");
                } else {
                    responseBody.put("success", true);
                    responseBody.put("message", "刪除成功");
                }
            }
        }
        return responseBody.toString();
    }

}
