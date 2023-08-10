package com.mango.courses.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AppFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        if(HttpStatus.valueOf(response.status()).is4xxClientError()){
            throw new CourseException(CourseError.STUDENT_CANNOT_BE_ENROLL);
        }

        return defaultErrorDecoder.decode("methodKey", response);

    }
}
