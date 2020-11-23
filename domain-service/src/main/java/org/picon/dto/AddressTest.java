package org.picon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.picon.domain.Address;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressTest {
    private Address address;
    private int count;
}
