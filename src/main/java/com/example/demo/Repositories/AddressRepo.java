package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entities.Address;
import com.example.demo.entities.Users;

@Repository
public interface AddressRepo extends JpaRepository<Address,Integer>{

  Address findByUser(Users user);

  @SuppressWarnings({ "null", "unchecked" })
  Address save( Address address);
}
