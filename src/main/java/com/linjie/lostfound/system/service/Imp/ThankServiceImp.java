package com.linjie.lostfound.system.service.Imp;

import com.linjie.lostfound.system.dao.ThankRepository;
import com.linjie.lostfound.system.model.Thank;
import com.linjie.lostfound.system.service.FoundService;
import com.linjie.lostfound.system.service.ThankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThankServiceImp implements ThankService {

    @Autowired
    ThankRepository thankRepository;

    @Override
    public Page<Thank> findAll(Pageable pageable) {
        return thankRepository.findAll(pageable);
    }

    public List<Thank> findAll(){
        return thankRepository.findAll();
    }

    @Override
    public Thank findById(Long id) {
        Optional<Thank> thankOption = thankRepository.findById(id);
        return thankOption.isPresent() ? thankOption.get() : null;
    }

    @Override
    public void delete(Thank thank) {
        thankRepository.delete(thank);
    }

    @Override
    public void save(Thank thank) {
        thankRepository.save(thank);
    }
}
