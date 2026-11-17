package org.example.demo.repository;

import org.example.demo.entity.Tartalom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TartalomRepository extends JpaRepository<Tartalom, Integer> {


    void deleteBySutiId(Integer sutiId);


    @Query("select t.mentes, count(t) from Tartalom t group by t.mentes order by count(t) desc")
    List<Object[]> darabMentesSzerint();
}
