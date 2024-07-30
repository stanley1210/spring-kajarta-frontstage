package com.spring_kajarta_frontstage.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.CustomerRecord;
import com.kajarta.demo.vo.CustomerRecordVO;
import com.spring_kajarta_frontstage.repository.CustomerRecordRepository;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

@Service
public class CustomerRecordService {

    @Autowired
    private CustomerRecordRepository customerRecordRepo;

    // 查單筆
    public CustomerRecord findById(Integer id) {
        if (id != null) {
            Optional<CustomerRecord> optional = customerRecordRepo.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }

    // 新增
    public CustomerRecord create(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer customerId = obj.isNull("customerId") ? null : obj.getInt("customerId");
            Date customerUpdateTime = obj.isNull("customerUpdateTime") ? new Date()
                    : DatetimeConverter.parse(obj.getString("customerUpdateTime"), "yyyy-MM-dd  HH:mm:ss");

            CustomerRecord insert = new CustomerRecord();
            insert.setCustomerId(customerId);
            insert.setCustomerIP("");
            insert.setCustomerUpdateTime(customerUpdateTime);

            // 記數
            insert.setCount(0);

            // 品牌
            insert.setBrandId01(0);
            insert.setBrandId02(0);
            insert.setBrandId03(0);
            insert.setBrandId04(0);
            insert.setBrandId05(0);
            insert.setBrandId06(0);
            insert.setBrandId07(0);
            insert.setBrandId08(0);
            insert.setBrandId09(0);

            // 排氣量
            insert.setDisplacementId01(0);
            insert.setDisplacementId02(0);
            insert.setDisplacementId03(0);
            insert.setDisplacementId04(0);
            insert.setDisplacementId05(0);
            insert.setDisplacementId06(0);
            insert.setDisplacementId07(0);

            // 門數
            insert.setDoorId01(0);
            insert.setDoorId02(0);
            insert.setDoorId03(0);
            insert.setDoorId04(0);
            insert.setDoorId05(0);

            // 乘客數
            insert.setPassengerId01(0);
            insert.setPassengerId02(0);
            insert.setPassengerId03(0);
            insert.setPassengerId04(0);

            // 燃料
            insert.setGasolineId01(0);
            insert.setGasolineId02(0);
            insert.setGasolineId03(0);
            insert.setGasolineId04(0);

            // 驅動
            insert.setRearWheelId01(0);
            insert.setRearWheelId02(0);
            insert.setRearWheelId03(0);

            // 車型
            insert.setSuspensionId01(0);
            insert.setSuspensionId02(0);
            insert.setSuspensionId03(0);
            insert.setSuspensionId04(0);
            insert.setSuspensionId05(0);
            insert.setSuspensionId06(0);

            // 打檔
            insert.setTransmissionId01(0);
            insert.setTransmissionId02(0);
            insert.setTransmissionId03(0);
            insert.setTransmissionId04(0);

            insert.setProductionYearAVG(new BigDecimal(0));// (出廠年份)
            insert.setProductionYearCount(0);

            insert.setMilageAVG(new BigDecimal(0));// (里程)
            insert.setMilageCount(0);

            insert.setScoreAVG(new BigDecimal(0));// (車評分)
            insert.setScoreCount(0);

            insert.setHpAVG(new BigDecimal(0));// (馬力)
            insert.setHpCount(0);

            insert.setTorqueAVG(new BigDecimal(0));// (扭力)
            insert.setTorqueCount(0);

            return customerRecordRepo.save(insert);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    // 修改
    public CustomerRecord modify(Integer id, BigDecimal productionYear, BigDecimal price, BigDecimal milage,
            BigDecimal score, BigDecimal hp, BigDecimal torque, Integer brandId, Integer suspensionId, Integer doorId,
            Integer passengerId, Integer rearWheelId, Integer gasolineId, Integer transmissionId,
            Integer displacementId) {
        try {

            Integer customerId = id;

            Optional<CustomerRecord> optional = customerRecordRepo.findById(customerId);
            if (optional.isPresent()) {
                CustomerRecord update = optional.get();
                if (brandId != null) {
                    switch (brandId) {
                        case 1:
                            update.setBrandId01(update.getBrandId01() + 1);
                            break;
                        case 2:
                            update.setBrandId02(update.getBrandId02() + 1);
                            break;
                        case 3:
                            update.setBrandId03(update.getBrandId03() + 1);
                            break;
                        case 4:
                            update.setBrandId04(update.getBrandId04() + 1);
                            break;
                        case 5:
                            update.setBrandId05(update.getBrandId05() + 1);
                            break;
                        case 6:
                            update.setBrandId06(update.getBrandId06() + 1);
                            break;
                        case 7:
                            update.setBrandId07(update.getBrandId07() + 1);
                            break;
                        case 8:
                            update.setBrandId08(update.getBrandId08() + 1);
                            break;
                        case 9:
                            update.setBrandId09(update.getBrandId09() + 1);
                            break;
                        default:
                            break;
                    }
                }

                if (displacementId != null) {
                    switch (displacementId) {
                        case 1:
                            update.setDisplacementId01(update.getDisplacementId01() + 1);
                            break;
                        case 2:
                            update.setDisplacementId02(update.getDisplacementId02() + 1);
                            break;
                        case 3:
                            update.setDisplacementId03(update.getDisplacementId03() + 1);
                            break;
                        case 4:
                            update.setDisplacementId04(update.getDisplacementId04() + 1);
                            break;
                        case 5:
                            update.setDisplacementId05(update.getDisplacementId05() + 1);
                            break;
                        case 6:
                            update.setDisplacementId06(update.getDisplacementId06() + 1);
                            break;
                        case 7:
                            update.setDisplacementId07(update.getDisplacementId07() + 1);
                            break;
                        default:
                            break;
                    }
                }

                if (doorId != null) {
                    switch (doorId) {
                        case 1:
                            update.setDoorId01(update.getDoorId01() + 1);
                            break;
                        case 2:
                            update.setDoorId02(update.getDoorId02() + 1);
                            break;
                        case 3:
                            update.setDoorId03(update.getDoorId03() + 1);
                            break;
                        case 4:
                            update.setDoorId04(update.getDoorId04() + 1);
                            break;
                        case 5:
                            update.setDoorId05(update.getDoorId05() + 1);
                            break;
                        default:
                            break;
                    }
                }

                if (passengerId != null) {
                    switch (passengerId) {
                        case 1:
                            update.setPassengerId01(update.getPassengerId01() + 1);
                            break;
                        case 2:
                            update.setPassengerId02(update.getPassengerId02() + 1);
                            break;
                        case 3:
                            update.setPassengerId03(update.getPassengerId03() + 1);
                            break;
                        case 4:
                            update.setPassengerId04(update.getPassengerId04() + 1);
                            break;
                        default:
                            break;
                    }
                }

                if (gasolineId != null) {
                    switch (gasolineId) {
                        case 1:
                            update.setGasolineId01(update.getGasolineId01() + 1);
                            break;
                        case 2:
                            update.setGasolineId02(update.getGasolineId02() + 1);
                            break;
                        case 3:
                            update.setGasolineId03(update.getGasolineId03() + 1);
                            break;
                        case 4:
                            update.setGasolineId04(update.getGasolineId04() + 1);
                            break;
                        default:
                            break;
                    }
                }

                switch (rearWheelId) {
                    case 1:
                        update.setRearWheelId01(update.getRearWheelId01() + 1);
                        break;
                    case 2:
                        update.setRearWheelId02(update.getRearWheelId02() + 1);
                        break;
                    case 3:
                        update.setRearWheelId03(update.getRearWheelId03() + 1);
                        break;
                    default:
                        break;
                }

                if (suspensionId != null) {
                    switch (suspensionId) {
                        case 1:
                            update.setSuspensionId01(update.getSuspensionId01() + 1);
                            break;
                        case 2:
                            update.setSuspensionId02(update.getSuspensionId02() + 1);
                            break;
                        case 3:
                            update.setSuspensionId03(update.getSuspensionId03() + 1);
                            break;
                        case 4:
                            update.setSuspensionId04(update.getSuspensionId04() + 1);
                            break;
                        case 5:
                            update.setSuspensionId05(update.getSuspensionId05() + 1);
                            break;
                        case 6:
                            update.setSuspensionId06(update.getSuspensionId06() + 1);
                            break;
                        default:
                            break;
                    }
                }

                if (transmissionId != null) {
                    switch (transmissionId) {
                        case 1:
                            update.setTransmissionId01(update.getTransmissionId01() + 1);
                            break;
                        case 2:
                            update.setTransmissionId02(update.getTransmissionId02() + 1);
                            break;
                        case 3:
                            update.setTransmissionId03(update.getTransmissionId03() + 1);
                            break;
                        case 4:
                            update.setTransmissionId04(update.getTransmissionId04() + 1);
                            break;
                        default:
                            break;
                    }
                }

                if (productionYear != null) {
                    BigDecimal productionYearCountBD = BigDecimal.valueOf(update.getProductionYearCount());
                    BigDecimal productionYearCountBDNew = BigDecimal.valueOf(update.getProductionYearCount() + 1);
                    update.setProductionYearAVG(
                            productionYearCountBD.multiply(update.getProductionYearAVG()).add(productionYear)
                                    .divide(productionYearCountBDNew, 2, RoundingMode.HALF_UP));
                    update.setProductionYearCount(update.getProductionYearCount() + 1);

                }
                if (milage != null) {
                    BigDecimal milageCountBD = BigDecimal.valueOf(update.getMilageCount());
                    BigDecimal milageCountBDNew = BigDecimal.valueOf(update.getMilageCount() + 1);
                    update.setMilageAVG(
                            milageCountBD.multiply(update.getMilageAVG()).add(milage).divide(milageCountBDNew, 2,
                                    RoundingMode.HALF_UP));
                    update.setMilageCount(update.getMilageCount() + 1);
                }
                if (score != null) {
                    BigDecimal scoreCountBD = BigDecimal.valueOf(update.getScoreCount());
                    BigDecimal scoreCountBDNew = BigDecimal.valueOf(update.getScoreCount() + 1);
                    update.setScoreAVG(
                            scoreCountBD.multiply(update.getScoreAVG()).add(score).divide(scoreCountBDNew, 2,
                                    RoundingMode.HALF_UP));
                    update.setScoreCount(update.getScoreCount() + 1);
                }
                if (hp != null) {
                    BigDecimal hpCountBD = BigDecimal.valueOf(update.getHpCount());
                    BigDecimal hpCountBDNew = BigDecimal.valueOf(update.getHpCount() + 1);
                    update.setHpAVG(
                            hpCountBD.multiply(update.getHpAVG()).add(hp).divide(hpCountBDNew, 2,
                                    RoundingMode.HALF_UP));
                    update.setHpCount(update.getHpCount() + 1);
                }
                if (torque != null) {
                    BigDecimal torqueCountBD = BigDecimal.valueOf(update.getTorqueCount());
                    BigDecimal torqueCountBDNew = BigDecimal.valueOf(update.getTorqueCount() + 1);
                    update.setTorqueAVG(
                            torqueCountBD.multiply(update.getTorqueAVG()).add(torque).divide(torqueCountBDNew, 2,
                                    RoundingMode.HALF_UP));
                    update.setTorqueCount(update.getTorqueCount() + 1);
                }

                update.setCount(update.getCount() + 1);

                return customerRecordRepo.save(update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Bean 轉 VO
    public CustomerRecordVO vOChange(CustomerRecord customerRecord) {
        CustomerRecordVO customerRecordVO = new CustomerRecordVO();

        BeanUtils.copyProperties(customerRecord, customerRecordVO);

        // 這邊要判斷 (Integer編號寫在VO) no01Care / no02Care / no03Care
        Map<String, Integer> map = new HashMap<>();
        map.put("01", customerRecord.getBrandId01());// 品牌
        map.put("02", customerRecord.getBrandId02());
        map.put("03", customerRecord.getBrandId03());
        map.put("04", customerRecord.getBrandId04());
        map.put("05", customerRecord.getBrandId05());
        map.put("06", customerRecord.getBrandId06());
        map.put("07", customerRecord.getBrandId07());
        map.put("08", customerRecord.getBrandId08());
        map.put("09", customerRecord.getBrandId09());

        map.put("11", customerRecord.getDisplacementId01());// 排氣量
        map.put("12", customerRecord.getDisplacementId02());
        map.put("13", customerRecord.getDisplacementId03());
        map.put("14", customerRecord.getDisplacementId04());
        map.put("15", customerRecord.getDisplacementId05());
        map.put("16", customerRecord.getDisplacementId06());
        map.put("17", customerRecord.getDisplacementId07());

        map.put("21", customerRecord.getDoorId01());// 門數
        map.put("22", customerRecord.getDoorId02());
        map.put("23", customerRecord.getDoorId03());
        map.put("24", customerRecord.getDoorId04());
        map.put("25", customerRecord.getDoorId05());

        map.put("31", customerRecord.getPassengerId01());// 乘客數
        map.put("32", customerRecord.getPassengerId02());
        map.put("33", customerRecord.getPassengerId03());
        map.put("34", customerRecord.getPassengerId04());

        map.put("41", customerRecord.getGasolineId01());// 燃料
        map.put("42", customerRecord.getGasolineId02());
        map.put("43", customerRecord.getGasolineId03());
        map.put("44", customerRecord.getGasolineId04());

        map.put("51", customerRecord.getRearWheelId01());// 驅動
        map.put("52", customerRecord.getRearWheelId02());
        map.put("53", customerRecord.getRearWheelId03());

        map.put("61", customerRecord.getSuspensionId01());// 車型
        map.put("62", customerRecord.getSuspensionId02());
        map.put("63", customerRecord.getSuspensionId03());
        map.put("64", customerRecord.getSuspensionId04());
        map.put("65", customerRecord.getSuspensionId05());
        map.put("66", customerRecord.getSuspensionId06());

        map.put("71", customerRecord.getTransmissionId01());// 打檔
        map.put("72", customerRecord.getTransmissionId02());
        map.put("73", customerRecord.getTransmissionId03());
        map.put("74", customerRecord.getTransmissionId04());

        int k = 3; // 要取前 K 高的 key
        List<String> topKKeys = getTopKKeys(map, k);
        System.out.println(topKKeys);

        customerRecordVO.setNo01Care(Integer.valueOf(topKKeys.get(0)));
        customerRecordVO.setNo02Care(Integer.valueOf(topKKeys.get(1)));
        customerRecordVO.setNo03Care(Integer.valueOf(topKKeys.get(2)));

        return customerRecordVO;
    }

    // 取前3高的方法
    public static List<String> getTopKKeys(Map<String, Integer> map, int k) {
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());

        pq.addAll(map.entrySet());

        List<String> result = new ArrayList<>();
        for (int i = 0; i < k && !pq.isEmpty(); i++) {
            result.add(pq.poll().getKey());
        }

        return result;
    }
}
