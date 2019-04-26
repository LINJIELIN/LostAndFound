package com.linjie.lostfound.system.dao;

import com.linjie.lostfound.system.model.Thank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThankRepository extends JpaRepository<Thank,Long> {
}
