package com.linjie.lostfound.system.dao;


import com.linjie.lostfound.system.model.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: SortResponsitory
 * Description: TODO
 * author: LINJIE
 * date: 2019/4/21 10:43
 * version V1.0
 */
@Repository
public interface SortRepository extends JpaRepository<Sort,Long> {
    
    Page<Sort> findAllByNameContaining(String name, Pageable pageable);

    Sort findByName(String name);
}
