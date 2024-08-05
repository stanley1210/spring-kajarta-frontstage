package com.spring_kajarta_frontstage.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.spring_kajarta_frontstage.service.CarService;
import com.spring_kajarta_frontstage.service.EmployeeService;
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

    // KPI運算
    @PutMapping("/KPI")
    public String updateKPI() {
        List<EmployeeVO> employeeVOsList = employeeService.findAll();
        Integer employeeId;
        Integer employeeType;
        Integer carId;
        Integer salesScore;
        Integer totalScore = 0;
        BigDecimal salesAvgScore;

        for (EmployeeVO employeeVO : employeeVOsList) {
            employeeId = employeeVO.getId();
            employeeType = employeeVO.getAccountType();
            if (employeeType == 3) {
                List<Car> carList = carService.findCarByEmployeeId(employeeId);
                Integer salesCount = 0;
                for (Car car : carList) {
                    carId = car.getId();
                    // System.out.println("銷售員" + employeeId + "負責的car" + carId);
                    List<ViewCar> viewCarList = viewCarService.findSalesScoreByCarId(carId);
                    for (ViewCar viewCar : viewCarList) {
                        salesScore = viewCar == null ? 0 : viewCar.getSalesScore();
                        totalScore = totalScore + salesScore;
                        salesCount++;
                        // System.out.println("銷售員" + employeeId + "負責的car:" + carId + " 並獲得:" +
                        // salesScore);
                    }
                }
                // System.out.println("銷售員" + employeeId + " 獲得總分:" + totalScore + " 共賣出:" +
                // salesCount);
                BigDecimal totalScoreBD = new BigDecimal(totalScore).setScale(1, RoundingMode.HALF_UP);
                BigDecimal salesCountBD = new BigDecimal(salesCount).setScale(1, RoundingMode.HALF_UP);
                if (salesCount != 0) {
                    salesAvgScore = totalScoreBD.divide(salesCountBD, 1, RoundingMode.HALF_UP);
                } else {
                    String x = "這廢物一台都沒賣出去";
                    salesAvgScore = BigDecimal.ZERO;
                    System.out.println(
                            "銷售員" + employeeId + " 獲得總分:" + totalScore + " 共賣出:" + salesCount + x);
                }
                System.out.println(
                        "銷售員" + employeeId + " 獲得總分:" + totalScore + " 共賣出:" + salesCount + "平均分數為:" + salesAvgScore);
                totalScore = 0;
                salesCount = 0;
            }
        }
        return null;
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
