package com.api.CLproject2.controller;

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
    public String connectToCamera( @RequestParam String id, @RequestParam String ip,
                                   @RequestParam String user, @RequestParam String password) {
        return CameraService.connectToCamera(id, ip, user, password);

    }
    @PostMapping("/discover")
    public String discoverCamera(@RequestParam String ip) {
        return CameraService.discoverCamera(ip);
    }
    @PostMapping("/disconnect/{id}")
    public String disconnectCamera(@PathVariable String id) {
        return CameraService.disconnectCamera(id);
    }
    @GetMapping("/status/{id}")
    public String checkCameraStatus(@PathVariable String id) {
        return CameraService.isCameraConnected(id) ? "Câmera connectada" : "Câmera desconnectada";
    }
}

