package com.carrot.backend.realtyImage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RealtyImageDto {
    Long realtyImageId;

    String realtyPath;

    Integer realtyId;

    public RealtyImageDto(Integer realtyId, String realtyPath){
        this.realtyId = realtyId;
        this.realtyPath = realtyPath;
    }
}
