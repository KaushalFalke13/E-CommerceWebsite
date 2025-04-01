package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entities.Brands;

@Repository
public interface BrandRepo extends JpaRepository<Brands,Integer> {

  List<Brands> findAllByBrandName(String BrandName);

}
