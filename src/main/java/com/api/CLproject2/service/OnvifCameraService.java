package com.api.CLproject2.service;

import de.onvif.soap.OnvifDevice;
import org.me.javawsdiscovery.DeviceDiscovery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



import java.net.ConnectException;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class OnvifCameraService {
    private OnvifDevice onvifDevice;

    public void connectToCamera(String ip, String user, String password) {
        try {
            onvifDevice = new OnvifDevice(ip, user, password);
            log.info("Connected to camera: " + ip);

        } catch (ConnectException e) {
            log.error("Error connecting to camera: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado: " + e);
        }
    }
    public void discoverCamera(){
        try{
            Collection<String> cameras = DeviceDiscovery.discoverWsDevices();
            if(!cameras.isEmpty()){
                log.info("Câmeras ONVIF encontradas: ");
                cameras.forEach( log::info);
            }else {
                log.warn("Nenhuma câmera ONVIF encontrada");

            }
        }catch (Exception e){
            log.error("Erro ao descobrir câmeras ONVIF: ", e);
        }
    }
}
