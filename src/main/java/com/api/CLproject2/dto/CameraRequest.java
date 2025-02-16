package com.api.CLproject2.dto;

public record CameraRequest(
        String id,
        String ip,
        String user,
        String password
) {
}
