package com.example.demo.Repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entities.Categories;

@Repository
public interface CatagoriesRepo extends JpaRepository<Categories,Integer>{

  @SuppressWarnings({ "null" })
  Categories save(Categories catagory);

}
