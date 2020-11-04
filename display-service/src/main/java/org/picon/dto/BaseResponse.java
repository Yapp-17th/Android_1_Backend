package org.picon.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
    private Integer status = 200;
    private String errors = "";
    private String errorCode = "";
    private String errorMessage = "";
}
