package com.carrot.backend.user.dao;

import com.carrot.backend.realty.dao.CustomizedRealtyRepository;
import com.carrot.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, CustomizedUserRepository {
    Optional<User> findByUserid(String userid);

}
