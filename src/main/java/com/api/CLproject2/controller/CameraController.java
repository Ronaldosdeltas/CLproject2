package com.api.CLproject2.controller;

import com.api.CLproject2.dto.CameraRequest;
import com.api.CLproject2.service.OnvifCameraService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/camera")
public class CameraController {

    private final OnvifCameraService CameraService;

    public CameraController(OnvifCameraService CameraService) {
        this.CameraService = CameraService;
    }

    @PostMapping("/connect")
    public String connectToCamera(@RequestBody CameraRequest request) {
        return CameraService.connectToCamera(request);

    }
    @PostMapping("/discover")
    public String discoverCamera(@RequestParam String ip) {
        return CameraService.discoverCamera(ip);
    }
    @PostMapping("/disconnect/{id}")
    public String disconnectCamera(@PathVariable String id) {
        return CameraService.disconnectCamera(id);
    }
    @PostMapping("ptz")
    public String ptz(@RequestParam String id, @RequestParam String action) {
        return CameraService.controlPTZ(id, action);
    }
    @GetMapping("/status/{id}")
    public String checkCameraStatus(@PathVariable String id) {
        return CameraService.isCameraConnected(id) ? "Câmera connectada" : "Câmera desconnectada";
    }
}

