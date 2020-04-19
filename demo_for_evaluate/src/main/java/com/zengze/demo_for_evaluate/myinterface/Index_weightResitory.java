package com.zengze.demo_for_evaluate.myinterface;

import com.zengze.demo_for_evaluate.entity.Index_weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Index_weightResitory extends JpaRepository<Index_weight,Integer> {
    @Query(value = "select * from index_weight order by id desc limit 1",nativeQuery =true)
    Index_weight findLastOne();
}
