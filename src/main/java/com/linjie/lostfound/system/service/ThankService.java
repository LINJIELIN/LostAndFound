package com.linjie.lostfound.system.service;

import com.linjie.lostfound.system.model.Lost;
import com.linjie.lostfound.system.model.Notice;
import com.linjie.lostfound.system.model.Thank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ThankService {

    Page<Thank> findAll(Pageable pageable);

    List<Thank> findAll();

    Thank findById(Long id);

    void delete(Thank thank);

    void save(Thank thank);
}
