package com.api.CLproject2.controller;

import com.api.CLproject2.service.OnvifCameraService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/camera")
public class CameraController {

    private final OnvifCameraService CameraService;

    public CameraController(OnvifCameraService CameraService) {
        this.CameraService = CameraService;
    }

    @GetMapping("/discover")
    public String discoverCamera() {
        CameraService.discoverCamera();
        return "Câmera ONVIF encontrada";
    }

    @PostMapping("/connect")
    public String connectToCamera(String ip, String user, String password) {
        CameraService.connectToCamera(ip, user, password);
        return "Conectado à câmera: ";
    }
}

