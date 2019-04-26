package com.linjie.sortfound.system.service;

import com.linjie.lostfound.system.model.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ClassName: SortService
 * Description: TODO
 * author: LINJIE
 * date: 2019/4/21 10:45
 * version V1.0
 */
public interface SortService {

    Sort findByName(String name);

    Page<Sort> pageSort(String name,Pageable pageable);

    List<Sort> findAll();

    Sort findById(Long id);

    void delete(Sort sort);

    void save(Sort sort);
}
