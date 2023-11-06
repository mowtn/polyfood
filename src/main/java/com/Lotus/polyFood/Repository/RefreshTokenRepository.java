package com.Lotus.polyFood.Repository;

import com.Lotus.polyFood.Model.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByToken(String token);

    @Query(value = "delete FROM refreshtoken where user_id =:userid",nativeQuery = true)
    @Modifying
    @Transactional
    int deleteByUserId(@Param("userid") int userid);
    @Query(value = "SELECT * FROM refreshtoken where user_id =:userId",nativeQuery = true)
    Optional<RefreshToken> findByUserId(@Param("userId") int userId);
}
