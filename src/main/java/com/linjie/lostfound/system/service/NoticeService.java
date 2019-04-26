package com.linjie.lostfound.system.service;

import com.linjie.lostfound.system.model.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeService {

    Page<Notice> findAllByTitleContaining(String title, Pageable pageable);

    Page<Notice> findAllPage(Pageable pageable);

    List<Notice> findAll();

    Notice findById(Long id);

    void delete(Notice notice);

    void save(Notice notice);
}
