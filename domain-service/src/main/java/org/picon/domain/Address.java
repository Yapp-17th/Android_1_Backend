package org.picon.domain;

import lombok.Getter;

@Getter
public class Address {
    private final String address;
    private final String addrCity;
    private final String addrDo;
    private final String addrGu;

    public Address(String address, String addrCity, String addrDo, String addrGu) {
        this.address = address;
        this.addrCity = addrCity;
        this.addrDo = addrDo;
        this.addrGu = addrGu;
    }

    @Override public String toString() {
        return "Address{" +
                "address='" + address + '\'' +
                ", addrCity='" + addrCity + '\'' +
                ", addrDo='" + addrDo + '\'' +
                ", addrGu='" + addrGu + '\'' +
                '}';
    }
}
