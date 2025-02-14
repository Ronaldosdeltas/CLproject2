package com.api.CLproject2.repository;

import de.onvif.soap.OnvifDevice;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CameraConnectionManager {

    private final Map<String, OnvifDevice> cameras = new ConcurrentHashMap<>();

    public void addCamera(String ip, OnvifDevice device) {
        cameras.put(ip, device);
    }
    public OnvifDevice getCamera(String ip) {
        return cameras.get(ip);
    }
    public void removeCamera(String ip) {
        cameras.remove(ip);
    }
    public boolean containsCamera(String ip) {
        return cameras.containsKey(ip);
    }
}
