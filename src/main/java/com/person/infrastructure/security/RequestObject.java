package com.person.infrastructure.security;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestObject {
    private Object data;
    private String dataString;
    private String langCode;
    private String checksum;
    private String serviceEndpointName;
    private Boolean isDebug = false;
    private String requestId;
}
