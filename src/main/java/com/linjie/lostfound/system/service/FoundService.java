package com.linjie.lostfound.system.service;

import com.linjie.lostfound.system.model.Found;
import com.linjie.lostfound.system.model.Lost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoundService {

    Page<Found> pageFound(String name, String retrieve, Pageable pageable);

    List<Found> findAllByContacts(String contacts);

    List<Found> findAll();

    Found findById(Long id);

    void delete(Found found);

    void save(Found foud);
}
