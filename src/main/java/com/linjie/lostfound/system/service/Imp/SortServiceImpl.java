package com.linjie.lostfound.system.service.Imp;

import com.linjie.lostfound.system.dao.SortRepository;
import com.linjie.lostfound.system.model.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName: SortServiceImpl
 * Description: TODO
 * author: LINJIE
 * date: 2019/4/21 10:46
 * version V1.0
 */
@Service
public class SortServiceImpl implements com.linjie.sortfound.system.service.SortService {
    
    @Autowired
    SortRepository sortRepository;

    @Override
    public Sort findByName(String name) {
        return sortRepository.findByName(name);
    }

    @Override
    public Page<Sort> pageSort(String name, Pageable pageable) {
        return sortRepository.findAllByNameContaining(name,pageable);
    }

    @Override
    public List<Sort> findAll() {
        return sortRepository.findAll();
    }

    @Override
    public Sort findById(Long id) {
        Optional<Sort> sortOption = sortRepository.findById(id);
        return sortOption.isPresent() ? sortOption.get() : null;
    }

    @Override
    public void delete(Sort sort) {
        sortRepository.delete(sort);
    }

    @Override
    public void save(Sort sort) {
        sortRepository.save(sort);
    }
}
