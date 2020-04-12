package com.quickapp.shared;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 400:
                break;
            case 404:
            {
                if(methodKey.contains("getUsers")){
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()),"Users are not found");
                }
                break;
            }
            default:
                return new Exception(response.reason());
        }
        return null;
    }
}
