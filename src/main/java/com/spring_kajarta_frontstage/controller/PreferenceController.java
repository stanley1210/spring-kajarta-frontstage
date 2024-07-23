package com.spring_kajarta_frontstage.controller;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Carinfo;
import com.kajarta.demo.model.Customer;
import com.kajarta.demo.model.Preference;
import com.spring_kajarta_frontstage.service.CarInfoService;
import com.spring_kajarta_frontstage.service.CustomerService;
import com.spring_kajarta_frontstage.service.PreferenceService;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

@RestController
@CrossOrigin
@RequestMapping("/preference")
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CarInfoService carinfoService;

    @GetMapping("/find/{Id}") // 查單筆ID
    @ResponseBody
    public String findDataById(@PathVariable(name = "Id") Integer Id) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        Preference preference = preferenceService.findById(Id);
        Customer customer = customerService.findById(Id);
        Carinfo carInfo = carinfoService.findById(Id);
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
                    .put("torque", preference.getTorque())
                    .put("createTime", preference.getCreateTime())
                    .put("updateTime", preference.getUpdateTime())
                    .put("preferencesLists", preference.getPreferencesLists());

            array = array.put(item);
        }
        responseBody.put("list", array);
        return responseBody.toString();
    }

    @GetMapping("/list") // 查全部
    @ResponseBody
    public String showAll() {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        List<Preference> preferences = preferenceService.select();
        for (Preference preference : preferences) {
            Integer carinfoId = (preference.getCarinfo()) == null ? -1 : preference.getCarinfo().getId();
            JSONObject item = new JSONObject()
                    .put("id", preference.getId())
                    .put("selectName", preference.getSelectName())
                    .put("productionYear", preference.getProductionYear())
                    .put("price", preference.getPrice())
                    .put("milage", preference.getMilage())
                    .put("score", preference.getScore())
                    .put("customer_id", preference.getCustomer().getId())
                    .put("carinfo_id", carinfoId)
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
                    .put("torque", preference.getTorque())
                    .put("createTime", preference.getCreateTime())
                    .put("updateTime", preference.getUpdateTime())
                    .put("preferencesLists", preference.getPreferencesLists());
            array.put(item);
        }

        responseBody.put("list", array);
        return responseBody.toString();
    }

    @GetMapping("/search") // 模糊查詢全部
    @ResponseBody
    public String searchByKeyword(@RequestParam(name = "keyword") String keyword) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        List<Preference> preferences = preferenceService.fuzzySearch(keyword);

        for (Preference preference : preferences) {
            Integer carinfoId = (preference.getCarinfo()) == null ? -1 : preference.getCarinfo().getId();
            JSONObject item = new JSONObject()
                    .put("id", preference.getId())
                    .put("selectame", preference.getSelectName())
                    .put("productionYear", preference.getProductionYear())
                    .put("price", preference.getPrice())
                    .put("milage", preference.getMilage())
                    .put("score", preference.getScore())
                    .put("customer_id", preference.getCustomer().getId())
                    .put("carinfo_id", carinfoId)
                    .put("brand", preference.getBrand())
                    .put("suspension", preference.getSuspension())
                    .put("door", preference.getDoor())
                    .put("passenger", preference.getPassenger())
                    .put("rearWheel", preference.getRearWheel())
                    .put("gasoline", preference.getGasoline())
                    .put("transmission", preference.getTransmission())
                    .put("cc", preference.getCc())
                    .put("hp", preference.getHp())
                    .put("torque", preference.getTorque())
                    .put("createTime", preference.getCreateTime())
                    .put("updateTime", preference.getUpdateTime())
                    .put("preferencesLists", preference.getPreferencesLists());
            array.put(item);
        }
        responseBody.put("list", array);
        return responseBody.toString();
    }

    @GetMapping("/search/selectName") // 模糊查詢select_name欄位
    @ResponseBody
    public String searchBySelectName(@RequestParam(name = "keyword") String keyword) {
        JSONObject responseBody = new JSONObject();
        List<Preference> preferences = preferenceService.searchBySelectName(keyword);
        for (Preference preference : preferences) {// 判斷資料庫是否有資料
            String selectName = preference.getSelectName();
            if (selectName == "沒有這筆資料唷") {
                responseBody.put("message", selectName);
                return responseBody.toString();
            }
        }
        JSONArray array = new JSONArray();
        for (Preference preference : preferences) {
            Integer customerId = (preference.getCustomer()) == null ? -1 : preference.getCustomer().getId();
            Integer carinfoId = (preference.getCarinfo()) == null ? -1 : preference.getCarinfo().getId();
            JSONObject item = new JSONObject()
                    .put("id", preference.getId())
                    .put("selectame", preference.getSelectName())
                    .put("productionYear", preference.getProductionYear())
                    .put("price", preference.getPrice())
                    .put("milage", preference.getMilage())
                    .put("score", preference.getScore())
                    .put("customer_id", preference.getCustomer().getId())
                    .put("carinfo_id", carinfoId)
                    .put("brand", customerId)
                    .put("suspension", preference.getSuspension())
                    .put("door", preference.getDoor())
                    .put("passenger", preference.getPassenger())
                    .put("rearWheel", preference.getRearWheel())
                    .put("gasoline", preference.getGasoline())
                    .put("transmission", preference.getTransmission())
                    .put("cc", preference.getCc())
                    .put("hp", preference.getHp())
                    .put("torque", preference.getTorque())
                    .put("createTime", preference.getCreateTime())
                    .put("updateTime", preference.getUpdateTime())
                    .put("preferencesLists", preference.getPreferencesLists());
            array.put(item);
        }
        responseBody.put("list", array);
        return responseBody.toString();
    }

    @GetMapping("/search/productionYear") // 模糊查詢production_year欄位
    @ResponseBody
    public String searchByProductionYear(@RequestParam(name = "keyword") Integer keyword) {
        JSONObject responseBody = new JSONObject();
        List<Preference> preferences = preferenceService.searchByProductionYear(keyword);
        for (Preference preference : preferences) {// 判斷資料庫是否有資料
            Integer productionYear = preference.getProductionYear();
            if (productionYear == 0) {
                responseBody.put("沒有這筆資料唷", productionYear);
                return responseBody.toString();
            }
        }
        JSONArray array = new JSONArray();
        for (Preference preference : preferences) {
            Integer customerId = (preference.getCustomer()) == null ? -1 : preference.getCustomer().getId();
            Integer carinfoId = (preference.getCarinfo()) == null ? -1 : preference.getCarinfo().getId();
            JSONObject item = new JSONObject()
                    .put("id", preference.getId())
                    .put("selectame", preference.getSelectName())
                    .put("productionYear", preference.getProductionYear())
                    .put("price", preference.getPrice())
                    .put("milage", preference.getMilage())
                    .put("score", preference.getScore())
                    .put("customer_id", preference.getCustomer().getId())
                    .put("carinfo_id", carinfoId)
                    .put("brand", customerId)
                    .put("suspension", preference.getSuspension())
                    .put("door", preference.getDoor())
                    .put("passenger", preference.getPassenger())
                    .put("rearWheel", preference.getRearWheel())
                    .put("gasoline", preference.getGasoline())
                    .put("transmission", preference.getTransmission())
                    .put("cc", preference.getCc())
                    .put("hp", preference.getHp())
                    .put("torque", preference.getTorque())
                    .put("createTime", preference.getCreateTime())
                    .put("updateTime", preference.getUpdateTime())
                    .put("preferencesLists", preference.getPreferencesLists());
            array.put(item);
        }
        responseBody.put("list", array);
        return responseBody.toString();
    }

    @GetMapping("/search/price") // 模糊查詢price欄位
    @ResponseBody
    public String searchByPrice(@RequestParam(name = "keyword") BigDecimal keyword) {
        JSONObject responseBody = new JSONObject();
        List<Preference> preferences = preferenceService.searchByPrice(keyword);
        for (Preference preference : preferences) {// 判斷資料庫是否有資料
            BigDecimal price = preference.getPrice();
            if (price == BigDecimal.ZERO) {
                responseBody.put("沒有這筆資料唷", price);
                return responseBody.toString();
            }
        }
        JSONArray array = new JSONArray();
        for (Preference preference : preferences) {
            Integer customerId = (preference.getCustomer()) == null ? -1 : preference.getCustomer().getId();
            Integer carinfoId = (preference.getCarinfo()) == null ? -1 : preference.getCarinfo().getId();
            JSONObject item = new JSONObject()
                    .put("id", preference.getId())
                    .put("selectame", preference.getSelectName())
                    .put("productionYear", preference.getProductionYear())
                    .put("price", preference.getPrice())
                    .put("milage", preference.getMilage())
                    .put("score", preference.getScore())
                    .put("customer_id", preference.getCustomer().getId())
                    .put("carinfo_id", carinfoId)
                    .put("brand", customerId)
                    .put("suspension", preference.getSuspension())
                    .put("door", preference.getDoor())
                    .put("passenger", preference.getPassenger())
                    .put("rearWheel", preference.getRearWheel())
                    .put("gasoline", preference.getGasoline())
                    .put("transmission", preference.getTransmission())
                    .put("cc", preference.getCc())
                    .put("hp", preference.getHp())
                    .put("torque", preference.getTorque())
                    .put("createTime", preference.getCreateTime())
                    .put("updateTime", preference.getUpdateTime())
                    .put("preferencesLists", preference.getPreferencesLists());
            array.put(item);
        }
        responseBody.put("list", array);
        return responseBody.toString();
    }

    @GetMapping("/search/milage") // 模糊查詢milage欄位
    @ResponseBody
    public String searchByMilage(@RequestParam(name = "keyword") Integer keyword) {
        JSONObject responseBody = new JSONObject();
        List<Preference> preferences = preferenceService.searchByMilage(keyword);
        for (Preference preference : preferences) { // 判斷資料庫是否有資料
            Integer milage = preference.getMilage();
            if (milage == 0) {
                responseBody.put("沒有這筆資料唷", milage);
                return responseBody.toString();
            }
        }
        JSONArray array = new JSONArray();
        for (Preference preference : preferences) {
            Integer customerId = (preference.getCustomer()) == null ? -1 : preference.getCustomer().getId();
            Integer carinfoId = (preference.getCarinfo()) == null ? -1 : preference.getCarinfo().getId();
            JSONObject item = new JSONObject()
                    .put("id", preference.getId())
                    .put("selectame", preference.getSelectName())
                    .put("productionYear", preference.getProductionYear())
                    .put("price", preference.getPrice())
                    .put("milage", preference.getMilage())
                    .put("score", preference.getScore())
                    .put("customer_id", preference.getCustomer().getId())
                    .put("carinfo_id", carinfoId)
                    .put("brand", customerId)
                    .put("suspension", preference.getSuspension())
                    .put("door", preference.getDoor())
                    .put("passenger", preference.getPassenger())
                    .put("rearWheel", preference.getRearWheel())
                    .put("gasoline", preference.getGasoline())
                    .put("transmission", preference.getTransmission())
                    .put("cc", preference.getCc())
                    .put("hp", preference.getHp())
                    .put("torque", preference.getTorque())
                    .put("createTime", preference.getCreateTime())
                    .put("updateTime", preference.getUpdateTime())
                    .put("preferencesLists", preference.getPreferencesLists());
            array.put(item);
        }
        responseBody.put("list", array);
        return responseBody.toString();
    }

    @GetMapping("/search/score") // 模糊查詢score欄位
    @ResponseBody
    public String searchByScore(@RequestParam(name = "keyword") Integer keyword) {
        JSONObject responseBody = new JSONObject();
        List<Preference> preferences = preferenceService.searchByScore(keyword);
        for (Preference preference : preferences) { // 判斷資料庫是否有資料
            Integer score = preference.getScore();
            if (score == 0) {
                responseBody.put("沒有這筆資料唷", score);
                return responseBody.toString();
            }
        }
        JSONArray array = new JSONArray();
        for (Preference preference : preferences) {
            Integer customerId = (preference.getCustomer()) == null ? -1 : preference.getCustomer().getId();
            Integer carinfoId = (preference.getCarinfo()) == null ? -1 : preference.getCarinfo().getId();
            JSONObject item = new JSONObject()
                    .put("id", preference.getId())
                    .put("selectame", preference.getSelectName())
                    .put("productionYear", preference.getProductionYear())
                    .put("price", preference.getPrice())
                    .put("milage", preference.getMilage())
                    .put("score", preference.getScore())
                    .put("customer_id", preference.getCustomer().getId())
                    .put("carinfo_id", carinfoId)
                    .put("brand", customerId)
                    .put("suspension", preference.getSuspension())
                    .put("door", preference.getDoor())
                    .put("passenger", preference.getPassenger())
                    .put("rearWheel", preference.getRearWheel())
                    .put("gasoline", preference.getGasoline())
                    .put("transmission", preference.getTransmission())
                    .put("cc", preference.getCc())
                    .put("hp", preference.getHp())
                    .put("torque", preference.getTorque())
                    .put("createTime", preference.getCreateTime())
                    .put("updateTime", preference.getUpdateTime())
                    .put("preferencesLists", preference.getPreferencesLists());
            array.put(item);
        }
        responseBody.put("list", array);
        return responseBody.toString();
    }

    @GetMapping("/search/hp") // 模糊查詢hp欄位
    @ResponseBody
    public String searchByHp(@RequestParam(name = "keyword") Integer keyword) {
        JSONObject responseBody = new JSONObject();
        List<Preference> preferences = preferenceService.searchByHp(keyword);
        for (Preference preference : preferences) { // 判斷資料庫是否有資料
            Integer hp = preference.getHp();
            if (hp == 0) {
                responseBody.put("沒有這筆資料唷", hp);
                return responseBody.toString();
            }
        }
        JSONArray array = new JSONArray();
        for (Preference preference : preferences) {
            Integer customerId = (preference.getCustomer()) == null ? -1 : preference.getCustomer().getId();
            Integer carinfoId = (preference.getCarinfo()) == null ? -1 : preference.getCarinfo().getId();
            JSONObject item = new JSONObject()
                    .put("id", preference.getId())
                    .put("selectame", preference.getSelectName())
                    .put("productionYear", preference.getProductionYear())
                    .put("price", preference.getPrice())
                    .put("milage", preference.getMilage())
                    .put("score", preference.getScore())
                    .put("customer_id", preference.getCustomer().getId())
                    .put("carinfo_id", carinfoId)
                    .put("brand", customerId)
                    .put("suspension", preference.getSuspension())
                    .put("door", preference.getDoor())
                    .put("passenger", preference.getPassenger())
                    .put("rearWheel", preference.getRearWheel())
                    .put("gasoline", preference.getGasoline())
                    .put("transmission", preference.getTransmission())
                    .put("cc", preference.getCc())
                    .put("hp", preference.getHp())
                    .put("torque", preference.getTorque())
                    .put("createTime", preference.getCreateTime())
                    .put("updateTime", preference.getUpdateTime())
                    .put("preferencesLists", preference.getPreferencesLists());
            array.put(item);
        }
        responseBody.put("list", array);
        return responseBody.toString();
    }

    @GetMapping("/search/torque") // 模糊查詢torque欄位
    @ResponseBody
    public String searchByTorque(@RequestParam(name = "keyword") String keyword) {
        JSONObject responseBody = new JSONObject();
        List<Preference> preferences = preferenceService.searchByTorque(keyword);
        for (Preference preference : preferences) { // 判斷資料庫是否有資料
            Double torque = preference.getTorque();
            if (torque == 0.0) {
                responseBody.put("沒有這筆資料唷", torque);
                return responseBody.toString();
            }
        }
        JSONArray array = new JSONArray();
        for (Preference preference : preferences) {
            Integer customerId = (preference.getCustomer()) == null ? -1 : preference.getCustomer().getId();
            Integer carinfoId = (preference.getCarinfo()) == null ? -1 : preference.getCarinfo().getId();
            JSONObject item = new JSONObject()
                    .put("id", preference.getId())
                    .put("selectame", preference.getSelectName())
                    .put("productionYear", preference.getProductionYear())
                    .put("price", preference.getPrice())
                    .put("milage", preference.getMilage())
                    .put("score", preference.getScore())
                    .put("customer_id", preference.getCustomer().getId())
                    .put("carinfo_id", carinfoId)
                    .put("brand", customerId)
                    .put("suspension", preference.getSuspension())
                    .put("door", preference.getDoor())
                    .put("passenger", preference.getPassenger())
                    .put("rearWheel", preference.getRearWheel())
                    .put("gasoline", preference.getGasoline())
                    .put("transmission", preference.getTransmission())
                    .put("cc", preference.getCc())
                    .put("hp", preference.getHp())
                    .put("torque", preference.getTorque())
                    .put("createTime", preference.getCreateTime())
                    .put("updateTime", preference.getUpdateTime())
                    .put("preferencesLists", preference.getPreferencesLists());
            array.put(item);
        }
        responseBody.put("list", array);
        return responseBody.toString();
    }

    @PostMapping("/create") // 新增
    public String create(@RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        Preference preference = preferenceService.create(body);
        if (preference == null) {
            responseBody.put("success", false);
            responseBody.put("message", "新增失敗");
        } else {
            responseBody.put("success", true);
            responseBody.put("message", "新增成功");
        }
        return responseBody.toString();
    }

    @PutMapping("/modify/{id}") // 修改
    public String modify(@RequestBody String body, @PathVariable(name = "id") Integer Id) {
        JSONObject responseBody = new JSONObject();
        if (!preferenceService.exists(Id)) {
            responseBody.put("success", false);
            responseBody.put("message", "ID不存在");
        } else {
            Preference preference = preferenceService.modify(body);
            if (preference == null) {
                responseBody.put("success", false);
                responseBody.put("message", "修改失敗");
            } else {
                responseBody.put("success", true);
                responseBody.put("message", "修改成功");
            }

        }

        return responseBody.toString();
    }

    @DeleteMapping("/delete/{id}") // 刪除
    public String remove(@PathVariable Integer id) {
        JSONObject responseBody = new JSONObject();

        if (!preferenceService.exists(id)) {
            responseBody.put("success", false);
            responseBody.put("message", "Id不存在");
        } else {
            if (!preferenceService.remove(id)) {
                responseBody.put("success", false);
                responseBody.put("message", "刪除失敗");
            } else {
                responseBody.put("success", true);
                responseBody.put("message", "刪除成功");
            }
        }

        return responseBody.toString();
    }

    // 多條件動態查詢
    @GetMapping("/searchMore")
    public String searchPreferences(
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) Integer productionYear,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) Integer milage,
            @RequestParam(required = false) Integer score,
            @RequestParam(required = false) Integer hp,
            @RequestParam(required = false) Double torque) {

        List<Car> carList = preferenceService.searchPreferencesCarJoinCarinfo(modelName, productionYear, price, milage,
                score,
                hp, torque);

        JSONObject responseBody = new JSONObject();
        JSONArray carArray = new JSONArray();
        for (Car car : carList) {
            Carinfo carInfoBean = carinfoService.findById(car.getCarinfo().getId());
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
                    .put("updateTime", updateTime);
            carArray.put(item);
        }

        responseBody.put("preferenceCarList", carArray);
        return responseBody.toString();
    }
}
