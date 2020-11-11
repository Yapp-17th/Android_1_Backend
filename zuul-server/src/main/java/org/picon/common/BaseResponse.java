package org.picon.common;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
    private Integer status = 200;
    private String errors = "";
    private String errorCode = "";
    private String errorMessage = "";
}
