package com.example.demo.Repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.searchKeywords;

public interface searchKeywordRepository extends JpaRepository<searchKeywords, Integer> {

  Optional<searchKeywords> findByKeyword(String keyword);

}

