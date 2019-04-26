package com.linjie.lostfound.system.dao;

import com.linjie.lostfound.system.model.Lost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LostRepository extends JpaRepository<Lost,Long> {

    Page<Lost> findAllByTitleContainingAndRetrieve(String title, boolean retrieve, Pageable pageable);

    Page<Lost> findAllByTitleContaining(String title,Pageable pageable);

    List<Lost> findAllByContacts(String contacts);

    /**
     * 删除日期过久的失物信息
     * @param dataString
     * @return
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Lost AS lost WHERE lost.lostTime < ?1")
    //@Query(value = "SELECT lost FROM Lost AS lost WHERE lost.lostTime < ?1")
    void deleteAllByLostTime(String dataString);
}
