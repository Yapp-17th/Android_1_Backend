package org.picon.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    @NotEmpty
    private String address;
    @NotNull
    private String addrCity;
    @NotNull
    private String addrDo;
    @NotNull
    private String addrGu;
}
