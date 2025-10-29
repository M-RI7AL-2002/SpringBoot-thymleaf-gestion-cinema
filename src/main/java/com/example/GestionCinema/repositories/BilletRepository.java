package com.example.GestionCinema.repositories;

import com.example.GestionCinema.entities.Billet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BilletRepository extends JpaRepository<Billet, Long> {

@Query("select b from Billet b where b.status = :status")
public List<Billet> findByStatus(@Param("status") String status);

@Query("select b.status from Billet b group by b.status")
    public List<String> getStatus();

@Query("SELECT SUM(b.prix) FROM Billet b WHERE b.status = :status")
    Double totalPriceByStatus(@Param("status") String status);

}
