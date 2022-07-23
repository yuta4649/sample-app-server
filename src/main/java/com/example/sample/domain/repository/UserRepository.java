package com.example.sample.domain.repository;


import com.example.sample.domain.entity.User;
import com.example.sample.domain.entity.Users;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Userを管理するリポジトリ
 *
 * @author YutaMori
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {

    /**
     * メールアドレスからUser情報を取得する
     *
     * @param mailAddress メールアドレス
     * @return Optional<User>
     */
    @Query("SELECT * FROM USERS WHERE mail_address = :mailAddress AND deleted_flg = 0")
    Optional<User> getUserByMailAddress(@Param("mailAddress") String mailAddress);

    /**
     * login用ユーザ情報を取得する
     *
     * @param loginId LoginID or メールアドレス
     * @return Optional<Users>
     */
    @Query("SELECT * FROM USERS WHERE mail_address = :loginId OR login_id = :loginId")
    Optional<Users> findByMailAddressOrLoginId(@Param("loginId") String loginId);
}
