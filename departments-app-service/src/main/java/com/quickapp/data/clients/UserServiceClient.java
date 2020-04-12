package com.quickapp.data.clients;

import com.quickapp.data.entities.UserEntity;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "users-ws",fallbackFactory = UsersFallbackFactory.class)
public interface UserServiceClient {
    @GetMapping("/users/")
    List<UserEntity> getUsers();
}
@Component
class UsersFallbackFactory implements FallbackFactory<UserServiceClient> {

    @Override
    public UserServiceClient create(Throwable cause) {
        return new UserServiceClientFallBack(cause);
    }
}

class UserServiceClientFallBack implements UserServiceClient {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Throwable cause;

    public UserServiceClientFallBack(Throwable throwable) {
        this.cause = throwable;
    }

    @Override
    public List<UserEntity> getUsers() {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            logger.error("404 error took place when getUsers was called. Error message: "
                    + cause.getLocalizedMessage());
        } else {
            logger.error("Other error took place: " + cause.getLocalizedMessage());
        }
        return new ArrayList<>();
    }
}