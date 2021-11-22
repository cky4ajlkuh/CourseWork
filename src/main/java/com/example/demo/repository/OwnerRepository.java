package com.example.demo.repository;

import com.example.demo.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    @Query("select b from Owner b where b.name = :name")
    Owner findByName(@Param("name") String name);

}
