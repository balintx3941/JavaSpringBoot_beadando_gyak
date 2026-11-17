package org.example.demo.repository;

import org.example.demo.entity.Suti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SutiRepository extends JpaRepository<Suti, Integer> {


    List<Suti> findByDijazottTrueOrderByNev();


    @Query("select distinct s.tipus from Suti s order by s.tipus")
    List<String> findDistinctTipus();


    @Query("select s.tipus, count(s) from Suti s group by s.tipus order by count(s) desc")
    List<Object[]> darabTipusSzerint();
}
