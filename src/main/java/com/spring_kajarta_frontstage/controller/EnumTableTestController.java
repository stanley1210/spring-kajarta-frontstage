package com.spring_kajarta_frontstage.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kajarta.demo.model.Displacement;
import com.kajarta.demo.model.Door;
import com.kajarta.demo.model.Gasoline;
import com.kajarta.demo.model.Negotiable;
import com.kajarta.demo.model.Passenger;
import com.kajarta.demo.model.Rearwheel;
import com.kajarta.demo.model.Suspension;
import com.kajarta.demo.model.Transmission;
import com.spring_kajarta_frontstage.service.BrandService;
import com.spring_kajarta_frontstage.service.DisplacementService;
import com.spring_kajarta_frontstage.service.DoorService;
import com.spring_kajarta_frontstage.service.GasolineService;
import com.spring_kajarta_frontstage.service.NegotiableService;
import com.spring_kajarta_frontstage.service.PassengerService;
import com.spring_kajarta_frontstage.service.RearWheelService;
import com.spring_kajarta_frontstage.service.SuspensionService;
import com.spring_kajarta_frontstage.service.TransmissionService;

@RestController
@RequestMapping("/enum")
@CrossOrigin
public class EnumTableTestController {

    @Autowired
    private NegotiableService negotiableService;

    @Autowired
    private TransmissionService transmissionService;

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
    private DisplacementService displacementService;

    @GetMapping("/negotiable/{id}")
    public String negotiable(@PathVariable(name = "id") Integer Id) {
        Negotiable item = negotiableService.findById(Id);
        return new JSONObject()
                .put("list", item.getPercent()).toString();
    }

    @GetMapping("/transmission/{id}")
    public String transmission(@PathVariable(name = "id") Integer Id) {
        Transmission item = transmissionService.findById(Id);
        return new JSONObject()
                .put("list", item.getTrans().trim()).toString();
    }

    @GetMapping("/suspension/{id}")
    public String suspension(@PathVariable(name = "id") Integer Id) {
        Suspension item = suspensionService.findById(Id);
        return new JSONObject()
                .put("list", item.getType()).toString();
    }

    @GetMapping("/door/{id}")
    public String door(@PathVariable(name = "id") Integer Id) {
        Door item = doorService.findById(Id);
        return new JSONObject()
                .put("list", item.getCardoor()).toString();
    }

    @GetMapping("/passenger/{id}")
    public String passenger(@PathVariable(name = "id") Integer Id) {
        Passenger item = passengerService.findById(Id);
        return new JSONObject()
                .put("list", item.getSeat()).toString();
    }

    @GetMapping("/rearwheel/{id}")
    public String rearWheel(@PathVariable(name = "id") Integer Id) {
        Rearwheel item = rearWheelService.findById(Id);
        return new JSONObject()
                .put("list", item.getWheel()).toString();
    }

    @GetMapping("/gasoline/{id}")
    public String gasoline(@PathVariable(name = "id") Integer Id) {
        Gasoline item = gasolineService.findById(Id);
        return new JSONObject()
                .put("list", item.getGaso()).toString();
    }

    @GetMapping("/displacement/{id}")
    public String displacement(@PathVariable(name = "id") Integer Id) {
        Displacement item = displacementService.findById(Id);
        return new JSONObject()
                .put("list", item.getCc()).toString();
    }
}
