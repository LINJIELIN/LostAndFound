package com.linjie.lostfound.system.service.Imp;

import com.linjie.lostfound.system.dao.NoticeRepository;
import com.linjie.lostfound.system.model.Lost;
import com.linjie.lostfound.system.model.Notice;
import com.linjie.lostfound.system.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeServiceImp implements NoticeService {
    @Autowired
    NoticeRepository noticeRepository;

    @Override
    public Page<Notice> findAllByTitleContaining(String title, Pageable pageable) {
        return noticeRepository.findAllByTitleContaining(title,pageable);
    }

    @Override
    public Page<Notice> findAllPage(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }

    @Override
    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }

    @Override
    public Notice findById(Long id) {
        Optional<Notice> noticeOption = noticeRepository.findById(id);
        return noticeOption.isPresent() ? noticeOption.get() : null;
    }

    @Override
    public void delete(Notice notice) {
        noticeRepository.delete(notice);
    }

    @Override
    public void save(Notice notice) {
        noticeRepository.save(notice);
    }




}
