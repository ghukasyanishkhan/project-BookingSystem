package com.booksystem.repository;

import com.booksystem.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
     List<UserEntity> getByEmail(String email);

    void deleteById(Integer id);

    UserEntity getByEmailAndResetToken(String email, String resetToken);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update users set password=?2 where email=?1")
    void update(String email, String password);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity>findByEmailAndIdNot(String email,Integer id);
}
