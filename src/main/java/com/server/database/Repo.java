package com.server.database;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface Repo extends CrudRepository<PlayerAccount, Long> {
    List<PlayerAccount> findPlayerAccountByLogin(String login);
    @Transactional
    @Modifying
    @Query("update PLAYERS u set u.fineNumber = u.fineNumber + 1 where u.login = :name")
    void updatePlayer(@Param("name")String name);



    @Transactional
    @Modifying
    @Query("update PLAYERS u set u.fineNumber = u.fineNumber - 1 where u.login = :name")
    void decFineAmount(@Param("name")String name);
}