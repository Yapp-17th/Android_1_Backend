package org.picon.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.picon.common.BaseResponse;
import org.picon.global.exception.ForbiddenException;
import org.picon.global.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity handleForbiddenException(ForbiddenException e) {
        String message = e.getReason();
        Integer status = e.getStatus().value();
        String errors = e.getStatus().toString();
        BaseResponse baseResponse = new BaseResponse(status, errors, "0001", message);

        return ResponseEntity.ok().body(baseResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(NotFoundException e) {
        String message = e.getReason();
        Integer status = e.getStatus().value();
        String errors = e.getStatus().toString();
        BaseResponse baseResponse = new BaseResponse(status, errors, "0002", message);

        return ResponseEntity.ok().body(baseResponse);
    }
}
