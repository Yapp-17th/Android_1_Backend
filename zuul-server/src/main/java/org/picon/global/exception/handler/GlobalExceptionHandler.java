package org.picon.global.exception.handler;

import org.picon.common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity handleForbiddenException(ResponseStatusException e) {
        String message = e.getReason();
        Integer status = e.getStatus().value();
        String errors = e.getStatus().toString();
        BaseResponse baseResponse = new BaseResponse(status, errors, "0001", message);

        return ResponseEntity.ok().body(baseResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> processValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("{");
            builder.append("error field: "+fieldError.getField());
            builder.append(" message: "+fieldError.getDefaultMessage());
            builder.append(" value: "+fieldError.getRejectedValue());
            builder.append("}, ");
        }

        String errors = builder.toString();

        BaseResponse baseResponse = new BaseResponse(400, errors, "0002", "요청값이 잘못되었습니다.");

        return ResponseEntity.ok().body(baseResponse);
    }
}
