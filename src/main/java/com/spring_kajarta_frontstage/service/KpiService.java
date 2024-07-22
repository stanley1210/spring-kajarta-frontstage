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
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.model.Kpi;
import com.kajarta.demo.vo.KpiVO;
import com.spring_kajarta_frontstage.repository.KpiRepository;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

@Service
public class KpiService {
    @Autowired
    private KpiRepository kpiRepo;

    @Autowired
    private EmployeeService employeeService;

    // 新增
    public Kpi create(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            String seasonStrDay = obj.isNull("seasonStrDay") ? null : obj.getString("seasonStrDay");
            Integer employeeId = obj.isNull("employeeId") ? null : obj.getInt("employeeId");

            Kpi insert = new Kpi();
            insert.setSeasonStrDay(DatetimeConverter.parse(seasonStrDay, "yyyy-MM-dd"));
            insert.setEmployee(employeeService.findById(employeeId));
            insert.setSalesScore(0);
            insert.setTotalScore(BigDecimal.valueOf(0));
            insert.setTeamLeaderRating(0);

            return kpiRepo.save(insert);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 修改
    public Kpi modify(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Integer teamLeaderRating = obj.isNull("teamLeaderRating") ? null : obj.getInt("teamLeaderRating");
            Integer salesScore = obj.isNull("salesScore") ? null : obj.getInt("salesScore");
            BigDecimal totalScore = obj.isNull("totalScore") ? null : obj.getBigDecimal("totalScore");
            Integer employeeId = obj.isNull("employeeId") ? null : obj.getInt("employeeId");

            Optional<Kpi> optional = kpiRepo.findById(id);
            if (optional.isPresent()) {
                Kpi update = optional.get();
                update.setId(id);
                update.setSeasonStrDay(kpiRepo.findById(id).get().getSeasonStrDay());
                update.setTeamLeaderRating(teamLeaderRating);
                update.setSalesScore(salesScore);
                update.setTotalScore(totalScore);
                update.setEmployee(employeeService.findById(employeeId));

                return kpiRepo.save(update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 查全部
    public List<Kpi> select(Kpi kpibean) {
        List<Kpi> result = null;
        if (kpibean != null && kpibean.getId() != null) {
            Optional<Kpi> optional = kpiRepo.findById(kpibean.getId());
            if (optional.isPresent()) {
                result = new ArrayList<>();
                result.add(optional.get());
            }
        } else {
            result = kpiRepo.findAll();
        }
        return result;
    }

    // 查詢一筆
    public Kpi findById(Integer id) {
        if (id != null) {
            Optional<Kpi> optional = kpiRepo.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }

    // 判斷id是否存在
    public boolean exists(Integer id) {
        if (id != null) {
            return kpiRepo.existsById(id);
        }
        return false;
    }

    // 查詢多筆
    public Page<Kpi> findByHQL(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Date selectStrDay = obj.isNull("selectStrDay") ? null
                    : DatetimeConverter.parse(obj.getString("selectStrDay"), "yyyy-MM-dd");
            Date selectEndDay = obj.isNull("selectEndDay") ? null
                    : DatetimeConverter.parse(obj.getString("selectEndDay"), "yyyy-MM-dd");
            Employee employee = obj.isNull("employeeId") ? null : employeeService.findById(obj.getInt("employeeId"));
            Employee teamLeader = obj.isNull("teamLeaderId") ? null
                    : employeeService.findById(obj.getInt("teamLeaderId"));
            Integer teamLeaderRatingMax = obj.isNull("teamLeaderRatingMax") ? null : obj.getInt("teamLeaderRatingMax");
            Integer teamLeaderRatingMin = obj.isNull("teamLeaderRatingMin") ? null : obj.getInt("teamLeaderRatingMin");
            Integer salesScoreMax = obj.isNull("salesScoreMax") ? null : obj.getInt("salesScoreMax");
            Integer salesScoreMin = obj.isNull("salesScoreMin") ? null : obj.getInt("salesScoreMin");
            BigDecimal totalScoreMax = obj.isNull("salesScoreMin") ? null : obj.getBigDecimal("totalScoreMax");
            BigDecimal totalScoreMin = obj.isNull("totalScoreMin") ? null : obj.getBigDecimal("totalScoreMin");

            Integer isPage = obj.isNull("isPage") ? 0 : obj.getInt("isPage");
            Integer max = obj.isNull("max") ? 4 : obj.getInt("max");
            boolean dir = obj.isNull("dir") ? true : obj.getBoolean("dir");
            String order = obj.isNull("order") ? "id" : obj.getString("order");
            Sort sort = dir ? Sort.by(Sort.Direction.ASC, order) : Sort.by(Sort.Direction.DESC, order);

            Pageable pgb = PageRequest.of(isPage.intValue(), max.intValue(), sort);
            Page<Kpi> page = kpiRepo.findByHQL(id, selectStrDay, selectEndDay, employee, teamLeader,
                    teamLeaderRatingMax, teamLeaderRatingMin, salesScoreMax, salesScoreMin, totalScoreMax,
                    totalScoreMin, pgb);

            return page;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Bean 轉 VO
    public KpiVO vOChange(Kpi kpi) {
        KpiVO kpiVO = new KpiVO();

        BeanUtils.copyProperties(kpi, kpiVO);

        // 主管 teamleader
        if (kpi.getEmployee().getTeamLeader() != null) {
            Employee teamleader = kpi.getEmployee().getTeamLeader();
            kpiVO.setTeamLeaderName(teamleader.getName());
        } else {
            kpiVO.setTeamLeaderName("此員工無主管");
        }
        kpiVO.setEmployeeName(kpi.getEmployee().getName());
        kpiVO.setSeasonStrDayString(DatetimeConverter.toString(kpi.getSeasonStrDay(), "yyyy-MM-dd"));
        kpiVO.setCreateTimeString(DatetimeConverter.toString(kpi.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        kpiVO.setUpdateTimeString(DatetimeConverter.toString(kpi.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));

        return kpiVO;
    }
}
