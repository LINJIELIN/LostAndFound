package com.linjie.lostfound.system.dao;

import com.linjie.lostfound.system.model.Found;
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
public interface FoundRepository extends JpaRepository<Found,Long> {

    Page<Found> findAllByTitleContainingAndRetrieve(String title, boolean retrieve, Pageable pageable);

    Page<Found> findAllByTitleContaining(String title,Pageable pageable);

    List<Found> findAllByContacts(String contacts);

    /**
     * 删除日期过久的失物信息
     * @param dataString
     * @return
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Found AS found WHERE found.foundTime < ?1")
    //@Query(value = "SELECT found FROM Found AS found WHERE found.foundTime < ?1")
    void deleteAllByFoundTime(String dataString);
}
