package com.api.CLproject2.service;

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

    public String connectToCamera(String id, String ip, String user, String password){

        try{
            OnvifDevice device = new OnvifDevice(ip, user, password);
            connectionManager.addCamera(id, device);
            return "Conectado à câmera: " + id;
        } catch (ConnectException e) {
            return "Falha ao conectar à câmera: " + e.getMessage();

        }catch (Exception e) {
            return "Falha ao conectar à câmera: " + e;
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
            return "Câmera não encontrada";
        }
        try {
            PtzDevices ptz = device.getPtz();
            if (ptz == null) {
                return "Câmera não suporta PTZ";
            }
            switch (action.toLowerCase()) {
                case "up":
                    ptz.continuousMove(0, 1, 0);
                    break;
                case "down":
                    device.ptzMoveDown();
                    break;
                case "left":
                    device.ptzMoveLeft();
                    break;
                case "right":
                    device.ptzMoveRight();
                    break;
                case "zoomIn":
                    device.ptzZoomIn();
                    break;
                case "zoomOut":
                    device.ptzZoomOut();
                    break;
                default:
                    return "Ação inválida";
            }
            return "Ação realizada com sucesso";
        } catch (Exception e) {
            return "Falha ao realizar ação: " + e.getMessage();
        }
    }
}

