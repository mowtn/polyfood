package com.Lotus.polyFood.Repository;

import com.Lotus.polyFood.Model.Decentralization;
import com.Lotus.polyFood.Model.EDecentralization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DecentralizationRepository extends JpaRepository<Decentralization,Integer> {
    Optional<Decentralization> findByName(EDecentralization name);
}
