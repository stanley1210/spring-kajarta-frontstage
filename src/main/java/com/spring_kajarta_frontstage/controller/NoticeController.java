package com.spring_kajarta_frontstage.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.kajarta.demo.model.Brand;
import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Carinfo;
import com.kajarta.demo.model.Customer;
import com.kajarta.demo.model.Displacement;
import com.kajarta.demo.model.Door;
import com.kajarta.demo.model.Gasoline;
import com.kajarta.demo.model.Notice;
import com.kajarta.demo.model.Passenger;
import com.kajarta.demo.model.Preference;
import com.kajarta.demo.model.Rearwheel;
import com.kajarta.demo.model.Suspension;
import com.kajarta.demo.model.Transmission;
import com.spring_kajarta_frontstage.service.BrandService;
import com.spring_kajarta_frontstage.service.CarInfoService;
import com.spring_kajarta_frontstage.service.CarService;
import com.spring_kajarta_frontstage.service.CustomerService;
import com.spring_kajarta_frontstage.service.DisplacementService;
import com.spring_kajarta_frontstage.service.DoorService;
import com.spring_kajarta_frontstage.service.GasolineService;
import com.spring_kajarta_frontstage.service.NoticeService;
import com.spring_kajarta_frontstage.service.PassengerService;
import com.spring_kajarta_frontstage.service.PreferenceService;
import com.spring_kajarta_frontstage.service.RearWheelService;
import com.spring_kajarta_frontstage.service.SuspensionService;
import com.spring_kajarta_frontstage.service.TransmissionService;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

@RestController
@RequestMapping("/front/notice")
@CrossOrigin
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private CarService carService;
    @Autowired
    private CarInfoService carInfoService;
    @Autowired
    private PreferenceService preferenceService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private BrandService brandService;
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


    // 計算數量
    @GetMapping("/count")
    public long count() {
        return noticeService.count();
    }

    // 新增
    @PostMapping("/create")
    public String create(@RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        JSONObject obj = new JSONObject(body);
        Notice card = noticeService.create(body);
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
            if (!noticeService.exists(id)) {
                responseBody.put("success", false);
                responseBody.put("message", "Id不存在");
            } else {
                Notice product = noticeService.modify(body);
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

        Notice notice = noticeService.findById(id);
        if (notice != null) {
            String createTime = DatetimeConverter.toString(notice.getCreateTime(), "yyyy-MM-dd");
            String updateTime = DatetimeConverter.toString(notice.getUpdateTime(), "yyyy-MM-dd");
            Integer carId = (notice.getCar() != null) ? notice.getCar().getId() : null;
            Integer viewCarId = (notice.getViewCar() != null) ? notice.getViewCar().getId() : null;
            Integer viewCarAssignedId = (notice.getViewCarAssigned() != null) ? notice.getViewCarAssigned().getId()
                    : null;
            Integer preferenceId = (notice.getPreference() != null) ? notice.getPreference().getId() : null;
            JSONObject item = new JSONObject()
                    .put("id", notice.getId())
                    .put("category", notice.getCategory())
                    .put("createTime", createTime)
                    .put("updateTime", updateTime)
                    .put("viewableNotification", notice.getViewableNotification())
                    .put("readStatus", notice.getReadStatus())
                    .put("receiver", notice.getReceiver())
                    .put("accountType", notice.getAccountType())
                    .put("viewCar", viewCarId)
                    .put("viewCarAssigned", viewCarAssignedId)
                    .put("preference", preferenceId)
                    .put("car", carId);
            array = array.put(item);
        }
        responseBody.put("list", array);
        return responseBody.toString();
    }

    // 查全
    @GetMapping("/selectAll")
    public String findAll() {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        List<Notice> notices = noticeService.findAll();
        for (Notice notice : notices) {
            String createTime = DatetimeConverter.toString(notice.getCreateTime(), "yyyy-MM-dd");
            String updateTime = DatetimeConverter.toString(notice.getUpdateTime(), "yyyy-MM-dd");
            Integer carId = (notice.getCar() != null) ? notice.getCar().getId() : null;
            Integer viewCarId = (notice.getViewCar() != null) ? notice.getViewCar().getId() : null;
            Integer viewCarAssignedId = (notice.getViewCarAssigned() != null) ? notice.getViewCarAssigned().getId()
                    : null;
            Integer preferenceId = (notice.getPreference() != null) ? notice.getPreference().getId() : null;
            JSONObject item = new JSONObject()
                    .put("id", notice.getId())
                    .put("category", notice.getCategory())
                    .put("createTime", createTime)
                    .put("updateTime", updateTime)
                    .put("viewableNotification", notice.getViewableNotification())
                    .put("readStatus", notice.getReadStatus())
                    .put("receiver", notice.getReceiver())
                    .put("accountType", notice.getAccountType())
                    .put("viewCar", viewCarId)
                    .put("viewCarAssigned", viewCarAssignedId)
                    .put("preference", preferenceId)
                    .put("car", carId);
            array.put(item);
        }
        long count = noticeService.count();
        responseBody.put("count", count);
        responseBody.put("list", array);
        return responseBody.toString();
    }

    // 多條件查詢
    // 刪除
    @DeleteMapping("/delete/{id}")
    public String remove(@PathVariable Integer id) {
        JSONObject responseBody = new JSONObject();
        if (id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "Id是必要欄位");
        } else {
            if (!noticeService.exists(id)) {
                responseBody.put("success", false);
                responseBody.put("message", "Id不存在");
            } else {
                if (!noticeService.remove(id)) {
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





    @GetMapping("/new-cars")
    public String findNewCars(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since) {
        List<Car> newCars = carService.findCarsAddedAfter(since);
        JSONArray array = new JSONArray();
        for (Car car : newCars) {
            String createTime = DatetimeConverter.toString(car.getCreateTime(), "yyyy-MM-dd'T'HH:mm:ss");
            String updateTime = DatetimeConverter.toString(car.getUpdateTime(), "yyyy-MM-dd'T'HH:mm:ss");
            JSONObject item = new JSONObject()
                    .put("id", car.getId())
                    .put("productionYear", car.getProductionYear())
                    .put("milage", car.getMilage())
                    .put("customerId", car.getCustomer().getId())
                    .put("employeeId", car.getEmployee().getId())
                    .put("negotiable", car.getNegotiable())
                    .put("conditionScore", car.getConditionScore())
                    .put("branch", car.getBranch())
                    .put("state", car.getState())
                    .put("price", car.getPrice())
                    .put("launchDate", car.getLaunchDate())
                    .put("carinfoId", car.getCarinfo().getId())
                    .put("color", car.getColor())
                    .put("remark", car.getRemark())
                    .put("createTime", createTime)
                    .put("updateTime", updateTime);
            array.put(item);
        }
        return array.toString();
    }



    @GetMapping("/findByCustomerId/{Id}") // CustomerId查詢會員搜尋條件列表並模糊搜尋二手車
    @ResponseBody
    public String findDataByCustomerId(@PathVariable(name = "Id") Integer Id) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        
        // 查找用戶喜好清單
        List<Preference> preferenceList = preferenceService.findByCustomerId(Id);
        Customer customer = customerService.findById(Id);
        List<Car> carList = carService.findByCustomerId(Id);
        Carinfo carInfo = new Carinfo();
        for (Car car : carList) {
            carInfo = carInfoService.findById(car.getCarinfo().getId());
        }
    
        // 用來儲存模糊搜尋結果
        JSONArray carArray = new JSONArray();
    
        for (Preference preference : preferenceList) {
            if (preference != null) {
                JSONObject item = new JSONObject()
                        .put("id", preference.getId())
                        .put("selectName", preference.getSelectName())
                        .put("productionYear", preference.getProductionYear())
                        .put("price", preference.getPrice())
                        .put("milage", preference.getMilage())
                        .put("score", preference.getScore())
                        .put("customer_id", customer.getId())
                        .put("carinfo_id", carInfo.getId())
                        .put("gasoline", preference.getGasoline())
                        .put("brand", preference.getBrand())
                        .put("suspension", preference.getSuspension())
                        .put("door", preference.getDoor())
                        .put("passenger", preference.getPassenger())
                        .put("rearWheel", preference.getRearWheel())
                        .put("gasoline", preference.getGasoline())
                        .put("transmission", preference.getTransmission())
                        .put("cc", preference.getCc())
                        .put("hp", preference.getHp())
                        .put("torque", preference.getTorque() != null ? preference.getTorque().toString() : null) // 確保在null的情況下傳遞null
                        .put("createTime", preference.getCreateTime())
                        .put("updateTime", preference.getUpdateTime())
                        .put("preferencesLists", preference.getPreferencesLists());
                array.put(item);
    
                // 使用喜好清單中的條件來模糊搜尋二手車
                JSONArray searchResult = searchPreferences(
                    carInfo.getId().toString(), 
                    carInfo.getModelName(), 
                    preference.getProductionYear(), 
                    preference.getPrice(), 
                    preference.getMilage(), 
                    preference.getScore(), 
                    preference.getHp(), 
                    preference.getTorque() != null ? preference.getTorque().toString() : null, // 確保在null的情況下傳遞null
                    carInfo.getBrand(), 
                    carInfo.getSuspension(), 
                    carInfo.getDoor(), 
                    carInfo.getPassenger(), 
                    carInfo.getRearwheel(), 
                    carInfo.getGasoline(), 
                    carInfo.getTransmission(), 
                    carInfo.getCc()
                );
    
                for (int i = 0; i < searchResult.length(); i++) {
                    carArray.put(searchResult.getJSONObject(i));
                }
            }
        }
    
        responseBody.put("preferenceCarList", carArray);
        return responseBody.toString();
    }
    
    // 多條件動態查詢
    public JSONArray searchPreferences(
            String carinfoId,
            String modelName,
            Integer productionYear,
            BigDecimal price,
            Integer milage,
            Integer score,
            Integer hp,
            String torque,
            Integer brand,
            Integer suspension,
            Integer door,
            Integer passenger,
            Integer rearwheel,
            Integer gasoline,
            Integer transmission,
            Integer cc) {
    
        List<Car> carList = preferenceService.searchPreferencesCarJoinCarinfo(carinfoId, modelName, productionYear,
                price, milage,
                score,
                hp, torque, brand, suspension, door, passenger, rearwheel, gasoline, transmission, cc);
    
        JSONArray carArray = new JSONArray();
    
        for (Car car : carList) {
            Carinfo carInfoBean = carInfoService.findById(car.getCarinfo().getId());
            Brand brandEnum = brandService.findById(carInfoBean.getBrand());
            Suspension suspensionEnum = suspensionService.findById(carInfoBean.getSuspension());
            Door doorEnum = doorService.findById(carInfoBean.getDoor());
            Passenger passengerEnum = passengerService.findById(carInfoBean.getPassenger());
            Rearwheel rearwheelEnum = rearWheelService.findById(carInfoBean.getRearwheel());
            Gasoline gasolineEnum = gasolineService.findById(carInfoBean.getGasoline());
            Transmission transmissionEnum = transmissionService.findById(carInfoBean.getTransmission());
            Displacement displacementEnum = displacementService.findById(carInfoBean.getCc());
            String createTime = DatetimeConverter.toString(car.getCreateTime(), "yyyy-MM-dd");
            String updateTime = DatetimeConverter.toString(car.getUpdateTime(), "yyyy-MM-dd");
    
            JSONObject item = new JSONObject()
                    .put("id", car.getId())
                    .put("productionYear", car.getProductionYear())
                    .put("milage", car.getMilage())
                    .put("customerId", car.getCustomer().getId())
                    .put("employeeId", car.getEmployee().getId())
                    .put("negotiable", car.getNegotiable())
                    .put("conditionScore", car.getConditionScore())
                    .put("branch", car.getBranch())
                    .put("state", car.getState())
                    .put("price", car.getPrice())
                    .put("launchDate", car.getLaunchDate())
                    .put("modelName", carInfoBean.getModelName())
                    .put("color", car.getColor())
                    .put("remark", car.getRemark())
                    .put("createTime", createTime)
                    .put("updateTime", updateTime)
                    // CarInfo的值
                    .put("carinfoId", carInfoBean.getId())
                    .put("brand", brandEnum.getBrand())
                    .put("modelName", carInfoBean.getModelName())
                    .put("suspension", suspensionEnum.getType())
                    .put("door", doorEnum.getCardoor())
                    .put("passenger", passengerEnum.getSeat())
                    .put("rearWheel", rearwheelEnum.getWheel())
                    .put("gasoline", gasolineEnum.getGaso())
                    .put("transmission", transmissionEnum.getTrans())
                    .put("cc", displacementEnum.getCc())
                    .put("hp", carInfoBean.getHp())
                    .put("torque", carInfoBean.getTorque() != null ? carInfoBean.getTorque().toString() : null);
            carArray.put(item);
        }
    
        return carArray;
    }
    


}
