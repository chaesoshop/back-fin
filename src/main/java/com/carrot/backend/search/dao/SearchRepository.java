package com.carrot.backend.search.dao;

import com.carrot.backend.search.domain.Search;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SearchRepository extends JpaRepository<Search, Integer> {
    Optional<Search> findBySearchWord(String search);
}
