package com.example.sample.domain.repository;


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
public interface UserRepository extends CrudRepository<Users, String> {

    /**
     * login用ユーザ情報を取得する
     *
     * @param loginId LoginID or メールアドレス
     * @return Optional<Users>
     */
    @Query("SELECT * FROM USERS WHERE mail_address = :loginId OR login_id = :loginId")
    Optional<Users> findByMailAddressOrLoginId(@Param("loginId") String loginId);

    /**
     * Account情報を取得する
     *
     * @param userId UserID
     * @return Users ユーザー情報
     */
    @Query("SELECT * FROM USERS WHERE USER_ID = 1000")
    Users getAccountInfo(@Param("userId") String userId);
}
