package com.example.demo.repository;

import com.example.demo.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {
    @Query("select b from Word b where b.first_form = :name or b.second_form = :name or b.third_form = :name or b.meaning = :name")
    Word findByWord(@Param("name") String name);
}
