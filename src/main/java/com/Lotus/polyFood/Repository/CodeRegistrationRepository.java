package com.Lotus.polyFood.Repository;

import com.Lotus.polyFood.Model.CodeRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeRegistrationRepository extends JpaRepository<CodeRegistration,Integer> {

    Optional<CodeRegistration> findByCode(String code);
}
