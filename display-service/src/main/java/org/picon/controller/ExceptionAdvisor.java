package org.picon.controller;

import org.picon.dto.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExceptionAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> processValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("{");
            builder.append(String.format("\"%s\"", fieldError.getField()));
            builder.append(": {");
            builder.append(String.format("\"message\" : \"%s\"", fieldError.getDefaultMessage()));
            builder.append(String.format("\"value\" : \"%s\"", fieldError.getRejectedValue()));
            builder.append("}");
            builder.append("}");
        }

        String errors = builder.toString();

        BaseResponse baseResponse = new BaseResponse(400, errors, "0001", "요청값이 잘못되었습니다.");

        return ResponseEntity.ok().body(baseResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> processIllegalStateError(RuntimeException exception) {
        Throwable cause = exception.getCause();

        String errors = cause.toString();

        BaseResponse baseResponse = new BaseResponse(500, errors, "0002", "서버 오류");

        return ResponseEntity.ok().body(baseResponse);
    }
}
