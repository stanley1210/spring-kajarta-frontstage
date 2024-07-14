package com.spring_kajarta_frontstage.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.CarAdjust;
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.vo.CarAdjustVO;
import com.spring_kajarta_frontstage.repository.CarAdjustRepository;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

@Service
public class CarAdjustService {

    @Autowired
    private CarAdjustRepository carAdjustRepo;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CarService carService;

    // 新增
    public CarAdjust create(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer teamLeaderId = obj.isNull("teamLeaderId") ? null : obj.getInt("teamLeaderId");
            Integer employeeId = obj.isNull("employeeId") ? null : obj.getInt("employeeId");
            Integer carId = obj.isNull("carId") ? null : obj.getInt("carId");
            Integer approvalStatus = obj.isNull("approvalStatus") ? null : obj.getInt("approvalStatus");
            Integer approvalType = obj.isNull("approvalType") ? null : obj.getInt("approvalType");
            BigDecimal floatingAmount = obj.isNull("floatingAmount") ? null : obj.getBigDecimal("floatingAmount");

            CarAdjust insert = new CarAdjust();
            insert.setTeamLeaderId(teamLeaderId);
            insert.setEmployee(employeeService.findById(employeeId));
            insert.setCar(carService.findById(carId));
            insert.setApprovalStatus(approvalStatus);
            insert.setApprovalType(approvalType);
            insert.setFloatingAmount(floatingAmount);

            return carAdjustRepo.save(insert);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 修改
    public CarAdjust modify(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Integer teamLeaderId = obj.isNull("teamLeaderId") ? null : obj.getInt("teamLeaderId");
            Integer employeeId = obj.isNull("employeeId") ? null : obj.getInt("employeeId");
            Integer carId = obj.isNull("carId") ? null : obj.getInt("carId");
            Integer approvalStatus = obj.isNull("approvalStatus") ? null : obj.getInt("approvalStatus");
            Integer approvalType = obj.isNull("approvalType") ? null : obj.getInt("approvalType");
            BigDecimal floatingAmount = obj.isNull("floatingAmount") ? null : obj.getBigDecimal("floatingAmount");

            Optional<CarAdjust> optional = carAdjustRepo.findById(id);
            if (optional.isPresent()) {
                CarAdjust update = optional.get();
                update.setId(id);
                update.setTeamLeaderId(teamLeaderId);
                update.setEmployee(employeeService.findById(employeeId));
                update.setCar(carService.findById(carId));
                update.setApprovalStatus(approvalStatus);
                update.setApprovalType(approvalType);
                update.setFloatingAmount(floatingAmount);

                return carAdjustRepo.save(update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 查全部
    public List<CarAdjust> select(CarAdjust carAdjustbean) {
        List<CarAdjust> result = null;
        if (carAdjustbean != null && carAdjustbean.getId() != null) {
            Optional<CarAdjust> optional = carAdjustRepo.findById(carAdjustbean.getId());
            if (optional.isPresent()) {
                result = new ArrayList<>();
                result.add(optional.get());
            }
        } else {
            result = carAdjustRepo.findAll();
        }
        return result;
    }

    // 查詢一筆
    public CarAdjust findById(Integer id) {
        if (id != null) {
            Optional<CarAdjust> optional = carAdjustRepo.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }

    // 判斷id是否存在
    public boolean exists(Integer id) {
        if (id != null) {
            return carAdjustRepo.existsById(id);
        }
        return false;
    }

    // 查詢多筆
    public Page<CarAdjust> findByHQL(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Integer teamLeaderId = obj.isNull("teamLeaderId") ? null : obj.getInt("teamLeaderId");
            Employee employee = obj.isNull("employeeId") ? null : employeeService.findById(obj.getInt("employeeId"));
            Car car = obj.isNull("carId") ? null : carService.findById(obj.getInt("carId"));
            Integer approvalStatus = obj.isNull("approvalStatus") ? null : obj.getInt("approvalStatus");
            Integer approvalType = obj.isNull("approvalType") ? null : obj.getInt("approvalType");
            BigDecimal floatingAmountMax = obj.isNull("floatingAmountMax") ? null
                    : obj.getBigDecimal("floatingAmountMax");
            BigDecimal floatingAmountMin = obj.isNull("floatingAmountMin") ? null
                    : obj.getBigDecimal("floatingAmountMin");
            Date createTime = obj.isNull("createTime") ? null
                    : DatetimeConverter.parse(obj.getString("createTime"), "yyyy-MM-dd");
            Date updateTime = obj.isNull("updateTime") ? null
                    : DatetimeConverter.parse(obj.getString("updateTime"), "yyyy-MM-dd");

            Integer isPage = obj.isNull("isPage") ? 0 : obj.getInt("isPage");
            Integer max = obj.isNull("max") ? 4 : obj.getInt("max");
            boolean dir = obj.isNull("dir") ? true : obj.getBoolean("dir");
            String order = obj.isNull("order") ? "id" : obj.getString("order");
            Sort sort = dir ? Sort.by(Sort.Direction.ASC, order) : Sort.by(Sort.Direction.DESC, order);

            Pageable pgb = PageRequest.of(isPage.intValue(), max.intValue(), sort);
            Page<CarAdjust> page = carAdjustRepo.findByHQL(id, teamLeaderId, employee, car, approvalStatus,
                    approvalType, floatingAmountMax, floatingAmountMin, createTime, updateTime, pgb);

            return page;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Bean 轉 VO
    public CarAdjustVO vOChange(CarAdjust carAdjust) {
        CarAdjustVO carAdjustVO = new CarAdjustVO();

        BeanUtils.copyProperties(carAdjust, carAdjustVO);
        // System.out.println(carAdjust.getTeamLeaderId());
        // 主管 teamleader
        Employee teamleader = employeeService.findById(carAdjust.getTeamLeaderId());

        // 簽核狀態 ApprovalStatus
        switch (carAdjust.getApprovalStatus()) {
            case 0:
                carAdjustVO.setApprovalStatusName("待簽");
                break;
            case 1:
                carAdjustVO.setApprovalStatusName("已簽");
                break;
            case 2:
                carAdjustVO.setApprovalStatusName("拒絕");
                break;

            default:
                carAdjustVO.setApprovalStatusName(
                        "簽核狀態錯誤 ApprovalStatus = " + carAdjust.getApprovalStatus().toString());
        }
        // 簽核種類 ApprovalType
        switch (carAdjust.getApprovalType()) {
            case 1:
                carAdjustVO.setApprovalTypeName("降價");
                break;
            case 2:
                carAdjustVO.setApprovalTypeName("漲價");
                break;
            case 3:
                carAdjustVO.setApprovalTypeName("下架");
                break;

            default:
                carAdjustVO.setApprovalTypeName("簽核種類錯誤 ApprovalType = " + carAdjust.getApprovalType().toString());
        }
        carAdjustVO.setTeamLeaderName(teamleader.getName());
        carAdjustVO.setEmployeeName(carAdjust.getEmployee().getName());
        carAdjustVO.setCarId(carAdjust.getCar().getId());

        return carAdjustVO;
    }
}
