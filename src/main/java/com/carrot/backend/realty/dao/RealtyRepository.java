package com.carrot.backend.realty.dao;

import com.carrot.backend.realty.domain.Realty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RealtyRepository extends JpaRepository<Realty, Integer>, CustomizedRealtyRepository {
    Optional<Realty> findByRealtyId(Integer realtyid);


    List<Realty> findByRealtyAddressContaining(String search);

    List<Realty> findByRealtyAddressContainingOrRealtyCategoryContainingOrRealtyContentContainingOrRealtyDealingContainingOrRealtyIntroduceContaining(String keyword,String keyword1,String keyword2,String keyword3,String keyword4);
}
