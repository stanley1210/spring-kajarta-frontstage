package com.spring_kajarta_frontstage.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kajarta.demo.enums.BranchEnum;
import com.kajarta.demo.model.Brand;
import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Carinfo;
import com.kajarta.demo.model.Displacement;
import com.kajarta.demo.model.Door;
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.model.Gasoline;
import com.kajarta.demo.model.Negotiable;
import com.kajarta.demo.model.Passenger;
import com.kajarta.demo.model.Preference;
import com.kajarta.demo.model.Rearwheel;
import com.kajarta.demo.model.Suspension;
import com.kajarta.demo.model.Transmission;
import com.spring_kajarta_frontstage.service.BrandService;
import com.spring_kajarta_frontstage.service.CarInfoService;
import com.spring_kajarta_frontstage.service.CarService;
import com.spring_kajarta_frontstage.service.DisplacementService;
import com.spring_kajarta_frontstage.service.DoorService;
import com.spring_kajarta_frontstage.service.EmployeeService;
import com.spring_kajarta_frontstage.service.GasolineService;
import com.spring_kajarta_frontstage.service.NegotiableService;
import com.spring_kajarta_frontstage.service.PassengerService;
import com.spring_kajarta_frontstage.service.RearWheelService;
import com.spring_kajarta_frontstage.service.SuspensionService;
import com.spring_kajarta_frontstage.service.TransmissionService;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

@RestController
@RequestMapping("/car")
@CrossOrigin
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    private CarInfoService carInfoService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private NegotiableService negotiableService;

    @Autowired
    private SuspensionService suspensionService;

    @Autowired
    private DoorService doorService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private RearWheelService rearWheelService;

    @Autowired
    private GasolineService gasolineService;

    @Autowired
    private TransmissionService transmissionService;

    @Autowired
    private DisplacementService displacementService;

    @Autowired
    private EmployeeService employeeService;

    // 查全部
    @GetMapping("/findAll")
    public String findAll(@RequestParam Integer pageNumber,
            @RequestParam String sortOrder,
            @RequestParam Integer max) {
        Page<Car> carPage = carService.findByPage(pageNumber, sortOrder, max);
        List<Car> Cars = carPage.getContent();
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        for (Car car : Cars) {
            String createTime = DatetimeConverter.toString(car.getCreateTime(), "yyyy-MM-dd");
            String updateTime = DatetimeConverter.toString(car.getUpdateTime(), "yyyy-MM-dd");
            Brand brandEnum = brandService.findById(car.getCarinfo().getBrand());
            Employee employee = employeeService.findById(car.getEmployee().getId());
            Integer stateCode = car.getState();
            String state = "無狀態";
            if (stateCode == 1) {
                state = "草稿";
            } else if (stateCode == 2) {
                state = "上架";
            } else if (stateCode == 3) {
                state = "下架";
            } else if (stateCode == 4) {
                state = "暫時下架";
            }

            JSONObject item = new JSONObject()
                    .put("id", car.getId())
                    .put("productionYear", car.getProductionYear())
                    .put("milage", car.getMilage())
                    .put("customerId", car.getCustomer().getId())
                    .put("employeeId", car.getEmployee().getId())
                    .put("employeeName", employee.getName())
                    .put("negotiable", car.getNegotiable())
                    .put("conditionScore", car.getConditionScore())
                    .put("branch", car.getBranch())
                    .put("state", car.getState())
                    .put("stateName", state)
                    .put("price", car.getPrice())
                    .put("launchDate", car.getLaunchDate())
                    .put("carinfoId", car.getCarinfo().getId())
                    .put("carinfoModelName", car.getCarinfo().getModelName())
                    .put("cainfoBrand", brandEnum.getBrand())
                    .put("color", car.getColor())
                    .put("remark", car.getRemark())
                    .put("createTime", createTime)
                    .put("updateTime", updateTime);
            array.put(item);
        }
        responseBody.put("list", array);
        responseBody.put("totalPages", carPage.getTotalPages());
        responseBody.put("totalElements", carPage.getTotalElements());
        return responseBody.toString();
    }

    // CustomerId查詢單筆
    @GetMapping("/findCustomerId/{Id}")
    @ResponseBody
    public String findDataByCustomerId(@PathVariable(name = "Id") Integer Id) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        if (Id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "ID不得為空");
        } else {
            List<Car> carList = carService.findByCustomerId(Id);
            for (Car car : carList) {
                Carinfo carInfoBean = carInfoService.findById(car.getCarinfo().getId());
                Brand brandEnum = brandService.findById(carInfoBean.getBrand());
                Negotiable negotiableEnum = negotiableService.findById(car.getNegotiable());
                Suspension suspensionEnum = suspensionService.findById(carInfoBean.getSuspension());
                Door doorEnum = doorService.findById(carInfoBean.getDoor());
                Passenger passengerEnum = passengerService.findById(carInfoBean.getPassenger());
                Rearwheel rearwheelEnum = rearWheelService.findById(carInfoBean.getRearwheel());
                Gasoline gasolineEnum = gasolineService.findById(carInfoBean.getGasoline());
                Transmission transmissionEnum = transmissionService.findById(carInfoBean.getTransmission());
                Displacement displacementEnum = displacementService.findById(carInfoBean.getCc());
                BranchEnum branch = BranchEnum.getByCode(car.getBranch());

                Car carModel = car;
                JSONObject carJson = new JSONObject()
                        .put("id", carModel.getId())
                        .put("productionYear", carModel.getProductionYear())
                        .put("milage", carModel.getMilage())
                        .put("customerId", carModel.getCustomer().getId())
                        .put("employeeId", carModel.getEmployee().getId())
                        .put("negotiable", negotiableEnum.getPercent())
                        .put("conditionScore", carModel.getConditionScore())
                        .put("branch", branch.getBranchName())
                        .put("state", carModel.getState())
                        .put("price", carModel.getPrice())
                        .put("launchDate", carModel.getLaunchDate())
                        .put("color", carModel.getColor())
                        .put("remark", carModel.getRemark())
                        // CarInfo的值
                        .put("carinfoId", carModel.getCarinfo().getId())
                        .put("carinfoBrand", brandEnum.getBrand())
                        .put("carinfoModelName", carInfoBean.getModelName())
                        .put("carinfoSuspension", suspensionEnum.getType())
                        .put("carinfoDoor", doorEnum.getCardoor())
                        .put("carinfoPassenger", passengerEnum.getSeat())
                        .put("carinfoRearWheel", rearwheelEnum.getWheel())
                        .put("carinfoGasoline", gasolineEnum.getGaso())
                        .put("carinfoTransmission", transmissionEnum.getTrans())
                        .put("carinfoCc", displacementEnum.getCc())
                        .put("carinfoHp", carInfoBean.getHp())
                        .put("carinfoTorque", carInfoBean.getTorque())
                        .put("carinfoCreateTime", carInfoBean.getCreateTime())
                        .put("carinfoUpdateTime", carInfoBean.getUpdateTime());
                array = array.put(carJson);
            }
            responseBody.put("list", array);
        }
        return responseBody.toString();
    }

    // 查詢單筆
    @GetMapping("/find/{Id}")
    @ResponseBody
    public String findDataById(@PathVariable(name = "Id") Integer Id) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        if (Id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "ID不得為空");
        } else {
            Car carBean = carService.findById(Id);
            Carinfo carInfoBean = carInfoService.findById(carBean.getCarinfo().getId());
            Brand brandEnum = brandService.findById(carInfoBean.getBrand());
            Negotiable negotiableEnum = negotiableService.findById(carBean.getNegotiable());
            Suspension suspensionEnum = suspensionService.findById(carInfoBean.getSuspension());
            Door doorEnum = doorService.findById(carInfoBean.getDoor());
            Passenger passengerEnum = passengerService.findById(carInfoBean.getPassenger());
            Rearwheel rearwheelEnum = rearWheelService.findById(carInfoBean.getRearwheel());
            Gasoline gasolineEnum = gasolineService.findById(carInfoBean.getGasoline());
            Transmission transmissionEnum = transmissionService.findById(carInfoBean.getTransmission());
            Displacement displacementEnum = displacementService.findById(carInfoBean.getCc());
            BranchEnum branch = BranchEnum.getByCode(carBean.getBranch());
            Integer stateCode = carBean.getState();
            Integer remarkCode = carBean.getRemark();
            String state = "無狀態";
            if (stateCode == 1) {
                state = "草稿";
            } else if (stateCode == 2) {
                state = "上架";
            } else if (stateCode == 3) {
                state = "下架";
            } else if (stateCode == 4) {
                state = "暫時下架";
            }
            String remark;
            if (remarkCode == 0) {
                remark = "無";
            } else {
                remark = "有";
            }

            if (carBean != null) {
                Car carModel = carBean;
                String compareUrl = "kajarta/car/compare";
                JSONObject carJson = new JSONObject()
                        .put("id", carModel.getId())
                        .put("productionYear", carModel.getProductionYear())
                        .put("milage", carModel.getMilage())
                        .put("customerId", carModel.getCustomer().getId())
                        .put("employeeId", carModel.getEmployee().getId())
                        .put("employeeName", carModel.getEmployee().getName())
                        .put("negotiable", negotiableEnum.getPercent())
                        .put("conditionScore", carModel.getConditionScore())
                        .put("branch", branch.getBranchName())
                        .put("state", carModel.getState())
                        .put("stateName", state)
                        .put("price", carModel.getPrice())
                        .put("launchDate", carModel.getLaunchDate())
                        .put("color", carModel.getColor())
                        .put("remark", carModel.getRemark())
                        .put("remarkName", remark)
                        .put("compare", compareUrl)
                        // CarInfo的值
                        .put("carinfoId", carModel.getCarinfo().getId())
                        .put("carinfoBrand", brandEnum.getBrand())
                        .put("carinfoModelName", carInfoBean.getModelName())
                        .put("carinfoSuspension", suspensionEnum.getType())
                        .put("carinfoDoor", doorEnum.getCardoor())
                        .put("carinfoPassenger", passengerEnum.getSeat())
                        .put("carinfoRearWheel", rearwheelEnum.getWheel())
                        .put("carinfoGasoline", gasolineEnum.getGaso())
                        .put("carinfoTransmission", transmissionEnum.getTrans())
                        .put("carinfoCc", displacementEnum.getCc())
                        .put("carinfoHp", carInfoBean.getHp())
                        .put("carinfoTorque", carInfoBean.getTorque())
                        .put("carinfoCreateTime", carInfoBean.getCreateTime())
                        .put("carinfoUpdateTime", carInfoBean.getUpdateTime());

                array = array.put(carJson);
                responseBody.put("list", array);
                return responseBody.toString();
            }
        }
        return responseBody.toString();
    }

    // ------------------------------------------------------------------------
    // 查詢兩筆(比較) 未完成
    @GetMapping("/compare")
    @ResponseBody
    public String compare(@PathVariable(name = "Id") Integer Id) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        if (Id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "ID不得為空");
        } else {
            Car carOptional = carService.findById(Id);
            if (carOptional != null) {
                Car carModel = carOptional;
                JSONObject item = new JSONObject()
                        .put("id", carModel.getId())
                        .put("productionYear", carModel.getProductionYear())
                        .put("milage", carModel.getMilage())
                        .put("customerId", carModel.getCustomer().getId())
                        .put("employeeId", carModel.getEmployee().getId())
                        .put("negotiable", carModel.getNegotiable())
                        .put("conditionScore", carModel.getConditionScore())
                        .put("branch", carModel.getBranch())
                        .put("state", carModel.getState())
                        .put("price", carModel.getPrice())
                        .put("launchDate", carModel.getLaunchDate())
                        .put("carinfoId", carModel.getCarinfo().getId())
                        .put("color", carModel.getColor())
                        .put("remark", carModel.getRemark());
                array = array.put(item);
                responseBody.put("list", array);
                return responseBody.toString();
            } else {
                responseBody.put("success", false);
                responseBody.put("message", "ID不存在");
            }
        }

        if (Id == null) {
            responseBody.put("success", "選擇你要比較的車輛");
        } else {
            Car carOptional = carService.findById(Id);
            if (carOptional != null) {
                Car carModel = carOptional;
                JSONObject item = new JSONObject()
                        .put("id", carModel.getId())
                        .put("productionYear", carModel.getProductionYear())
                        .put("milage", carModel.getMilage())
                        .put("customerId", carModel.getCustomer().getId())
                        .put("employeeId", carModel.getEmployee().getId())
                        .put("negotiable", carModel.getNegotiable())
                        .put("conditionScore", carModel.getConditionScore())
                        .put("branch", carModel.getBranch())
                        .put("state", carModel.getState())
                        .put("price", carModel.getPrice())
                        .put("launchDate", carModel.getLaunchDate())
                        .put("carinfoId", carModel.getCarinfo().getId())
                        .put("color", carModel.getColor())
                        .put("remark", carModel.getRemark());
                array = array.put(item);
                responseBody.put("list", array);
                return responseBody.toString();
            } else {
                responseBody.put("success", false);
                responseBody.put("message", "ID不存在");
            }
        }
        return responseBody.toString();
    }
    // ------------------------------------------------------------------------

    // 新增單筆
    @PostMapping("/create")
    public String jsonCreate(@RequestBody String body) {
        JSONObject reponseBody = new JSONObject();
        Car car = carService.create(body);
        if (car == null) {
            reponseBody.put("success", false);
            reponseBody.put("message", "新增失敗");
        } else {
            reponseBody.put("success", true);
            reponseBody.put("message", "新增成功");
        }
        return reponseBody.toString();
    }

    // 刪除
    @DeleteMapping("/delete/{id}")
    public String remove(@PathVariable Integer id) {
        JSONObject responseBody = new JSONObject();
        if (!carService.exists(id)) {
            responseBody.put("success", false);
            responseBody.put("message", "Id不存在");
        } else {
            if (!carService.remove(id)) {
                responseBody.put("success", false);
                responseBody.put("message", "刪除失敗");
            } else {
                responseBody.put("success", true);
                responseBody.put("message", "刪除成功");
            }
        }
        return responseBody.toString();
    }

    // 修改
    @PutMapping("/modify/{id}")
    public String modify(@RequestBody String body, @PathVariable(name = "id") Integer Id) {
        JSONObject responseBody = new JSONObject();
        if (!carService.exists(Id)) {
            responseBody.put("success", false);
            responseBody.put("message", "ID不存在");
        } else {
            Car car = carService.modify(body);
            if (car == null) {
                responseBody.put("success", false);
                responseBody.put("message", "修改失敗");
            } else {
                responseBody.put("success", true);
                responseBody.put("message", "修改成功");
            }
        }
        return responseBody.toString();
    }

}
