package com.carrot.backend.realty.dao;

import com.carrot.backend.realty.dto.RealtyDto;

public interface CustomizedRealtyRepository {

    RealtyDto getQslRealtyAndImagesByRealtyId(Integer realtyId);
    void deleteQslRealtyAndImagesByRealtyId(Integer realtyId);
}
