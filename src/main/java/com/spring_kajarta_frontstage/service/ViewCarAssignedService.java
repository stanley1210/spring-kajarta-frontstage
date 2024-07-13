package com.spring_kajarta_frontstage.service;

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

import com.kajarta.demo.model.Agenda;
import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Carinfo;
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.model.ViewCar;
import com.kajarta.demo.model.ViewCarAssigned;
import com.kajarta.demo.vo.ViewCarAssignedVO;
import com.spring_kajarta_frontstage.repository.ViewCarAssignedRepository;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

@Service
public class ViewCarAssignedService {

    @Autowired
    private ViewCarAssignedRepository viewCarAssignedRepo;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ViewCarService viewCarService;

    @Autowired
    private CarService carService;

    @Autowired
    private CarInfoService carInfoService;

    // 新增
    public ViewCarAssigned create(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer teamLeaderId = obj.isNull("teamLeaderId") ? null : obj.getInt("teamLeaderId");
            Integer employeeId = obj.isNull("employeeId") ? null : obj.getInt("employeeId");
            Integer viewCarId = obj.isNull("viewCarId") ? null : obj.getInt("viewCarId");
            Integer assignedStatus = obj.isNull("assignedStatus") ? null : obj.getInt("assignedStatus");

            ViewCarAssigned insert = new ViewCarAssigned();
            insert.setTeamLeaderId(teamLeaderId);
            insert.setEmployee(employeeService.findById(employeeId));
            insert.setViewCar(viewCarService.findById(viewCarId));
            insert.setAssignedStatus(assignedStatus);

            return viewCarAssignedRepo.save(insert);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 修改
    public ViewCarAssigned modify(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Integer teamLeaderId = obj.isNull("teamLeaderId") ? null : obj.getInt("teamLeaderId");
            Integer employeeId = obj.isNull("employeeId") ? null : obj.getInt("employeeId");
            Integer viewCarId = obj.isNull("viewCarId") ? null : obj.getInt("viewCarId");
            Integer assignedStatus = obj.isNull("assignedStatus") ? null : obj.getInt("assignedStatus");

            Optional<ViewCarAssigned> optional = viewCarAssignedRepo.findById(id);
            if (optional.isPresent()) {
                ViewCarAssigned update = optional.get();
                update.setId(id);
                update.setTeamLeaderId(teamLeaderId);
                update.setEmployee(employeeService.findById(employeeId));
                update.setViewCar(viewCarService.findById(viewCarId));
                update.setAssignedStatus(assignedStatus);

                return viewCarAssignedRepo.save(update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 查全部
    public List<ViewCarAssigned> select(ViewCarAssigned viewCarAssignedbean) {
        List<ViewCarAssigned> result = null;
        if (viewCarAssignedbean != null && viewCarAssignedbean.getId() != null) {
            Optional<ViewCarAssigned> optional = viewCarAssignedRepo.findById(viewCarAssignedbean.getId());
            if (optional.isPresent()) {
                result = new ArrayList<>();
                result.add(optional.get());
            }
        } else {
            result = viewCarAssignedRepo.findAll();
        }
        return result;
    }

    // 查詢一筆
    public ViewCarAssigned findById(Integer id) {
        if (id != null) {
            Optional<ViewCarAssigned> optional = viewCarAssignedRepo.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }

    // 判斷id是否存在
    public boolean exists(Integer id) {
        if (id != null) {
            return viewCarAssignedRepo.existsById(id);
        }
        return false;
    }

    // 查詢多筆
    public Page<ViewCarAssigned> findByHQL(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Date viewCarDateStr = obj.isNull("viewCarDateStr") ? null
                    : DatetimeConverter.parse(obj.getString("viewCarDateStr"), "yyyy-MM-dd");
            Date viewCarDateEnd = obj.isNull("viewCarDateEnd") ? null
                    : DatetimeConverter.parse(obj.getString("viewCarDateEnd"), "yyyy-MM-dd");
            Employee employee = obj.isNull("employeeId") ? null : employeeService.findById(obj.getInt("employeeId"));
            Integer teamLeaderId = obj.isNull("teamLeaderId") ? null : obj.getInt("teamLeaderId");
            ViewCar viewCar = obj.isNull("viewCarId") ? null : viewCarService.findById(obj.getInt("viewCarId"));
            Car car = obj.isNull("carId") ? null : carService.findById(obj.getInt("carId"));
            Carinfo carinfo = obj.isNull("carinfoId") ? null : null; // carInfoService.findById(obj.getInt("carinfoId"))
            Integer model = obj.isNull("modelid") ? null : obj.getInt("modelid");
            Integer assignedStatus = obj.isNull("assignedStatus") ? null : obj.getInt("assignedStatus");

            Integer isPage = obj.isNull("isPage") ? 0 : obj.getInt("isPage");
            Integer max = obj.isNull("max") ? 4 : obj.getInt("max");
            boolean dir = obj.isNull("dir") ? true : obj.getBoolean("dir");
            String order = obj.isNull("order") ? "id" : obj.getString("order");
            Sort sort = dir ? Sort.by(Sort.Direction.ASC, order) : Sort.by(Sort.Direction.DESC, order);

            Pageable pgb = PageRequest.of(isPage.intValue(), max.intValue(), sort);
            Page<ViewCarAssigned> page = viewCarAssignedRepo.findByHQL(id, viewCarDateStr, viewCarDateEnd, employee,
                    teamLeaderId, viewCar, car, carinfo, model, assignedStatus, pgb);

            return page;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Bean 轉 VO
    public ViewCarAssignedVO vOChange(ViewCarAssigned viewCarAssigned) {
        ViewCarAssignedVO viewCarAssignedVO = new ViewCarAssignedVO();

        BeanUtils.copyProperties(viewCarAssigned, viewCarAssignedVO);

        switch (viewCarAssigned.getAssignedStatus()) {
            case 0:
                viewCarAssignedVO.setAssignedStatusName("未指派");
                break;
            case 1:
                viewCarAssignedVO.setAssignedStatusName("已指派");
                break;
            case 2:
                viewCarAssignedVO.setAssignedStatusName("註銷");
                break;

            default:
                viewCarAssignedVO.setAssignedStatusName(
                        "簽核狀態錯誤 viewCarAssigned = " + viewCarAssigned.getAssignedStatus().toString());
        }

        viewCarAssignedVO.setViewCarId(viewCarAssigned.getViewCar().getId());
        viewCarAssignedVO.setTeamLeaderName(employeeService.findById(viewCarAssigned.getTeamLeaderId()).getName());
        viewCarAssignedVO.setEmployeeName(viewCarAssigned.getEmployee().getName());

        return viewCarAssignedVO;
    }
}
