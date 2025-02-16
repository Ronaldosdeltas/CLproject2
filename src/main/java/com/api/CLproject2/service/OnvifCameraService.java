package com.api.CLproject2.service;

import com.api.CLproject2.dto.CameraRequest;
import com.api.CLproject2.repository.CameraConnectionManager;
import de.onvif.soap.OnvifDevice;
import de.onvif.soap.devices.PtzDevices;
import org.springframework.stereotype.Service;

import java.net.ConnectException;

@Service
public class OnvifCameraService {

    private final CameraConnectionManager connectionManager;

    public OnvifCameraService (CameraConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
        public String connectToCamera(CameraRequest request) {
         String id = request.id();
         String ip = request.ip();
         String user = request.user();
         String password = request.password();

            if (connectToCamera(id, ip,user, password)) {
                return "Camera connected successfully";
            } else {
                return "Failed to connect to camera";
            }
        }

    public boolean connectToCamera(String id, String ip, String user, String password){

        try{
            System.out.println("Attempting to connect to camera with IP: " + ip);

            OnvifDevice device = new OnvifDevice(ip, user, password);
            connectionManager.addCamera(id, device);
            System.out.println("Camera connected successfully with IP: " + ip);

            return true;
        } catch (ConnectException e) {
            System.out.println("Failed to connect to camera " + e.getMessage());
            return false;
        }catch (Exception e) {
            System.out.println("Failed to connect to camera" + e);
            return false;
        }
    }
    public String disconnectCamera(String id) {
        if (connectionManager.containsCamera(id)) {
            connectionManager.removeCamera(id);
            return "Câmera desconectada";
        }
        return "Câmera não encontrada";
    }
    public String discoverCamera(String ip){
        System.out.println("Descobrindo câmera em: " + ip);
        return "Câmera descoberta com sucesso";
    }
    public boolean isCameraConnected(String id) {
        return connectionManager.containsCamera(id);
    }
    public String controlPTZ(String id, String action) {
        OnvifDevice device = connectionManager.getCamera(id);
        if (device == null) {
            return "Camera not found";
        }
        try {
            PtzDevices ptz = device.getPtz();
            if (ptz == null) {
                return "Camera doesn't support PTZ";
            }

            switch (action.toLowerCase()) {
                case "up":
                    ptz.continuousMove("profileToken", 0f, 1f, 0f);
                    break;
                case "down":
                    ptz.continuousMove("profileToken", 0f, -1f, 0f);
                    break;
                case "left":
                    ptz.continuousMove("profileToken", -1f, 0f, 0f);
                    break;
                case "right":
                    ptz.continuousMove("profileToken", 1f, 0f, 0f);
                    break;
                case "zoomin":
                    ptz.continuousMove("profileToken", 0f, 0f, 1f);
                    break;
                case "zoomout":
                    ptz.continuousMove("profileToken", 0f, 0f, -1f);

                    break;
                default:
                    return "Action invalid";
            }
            return "Action performed successfully";
        } catch (Exception e) {
            return "Failed to perform action: " + e.getMessage();
        }
    }
}

