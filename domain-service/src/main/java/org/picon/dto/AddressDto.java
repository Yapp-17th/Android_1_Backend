package org.picon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.picon.domain.Address;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    private String address;
    private String addrCity;
    private String addrDo;
    private String addrGu;

    public static AddressDto AddressDtoOf(Address address) {
        return AddressDto.builder()
                .address(address.getAddress())
                .addrCity(address.getAddrCity())
                .addrDo(address.getAddrDo())
                .addrGu(address.getAddrGu())
                .build();
    }
}
