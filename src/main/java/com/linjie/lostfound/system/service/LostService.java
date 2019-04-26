package com.linjie.lostfound.system.service;

import com.alibaba.fastjson.JSONArray;
import com.linjie.lostfound.system.model.Lost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LostService {

    JSONArray findHistory(String contacts);

    boolean permission(String account, Long id);

    List<Lost> findAllByContacts(String contacts);

    Page<Lost> pageLost(String name, String retrieve, Pageable pageable);

    Page<Lost> pageLost(Pageable pageable);
    List<Lost> findAll();

    Lost findById(Long id);

    void delete(Lost lost);

    void save(Lost lost);
}
