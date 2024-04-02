package com.rungroop.web.repository;

import com.rungroop.web.models.Cloth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClothRepository extends JpaRepository<Cloth, Long> {
    Optional<Cloth> findByTitle(String url);
    @Query("SELECT c from Cloth c WHERE c.title LIKE CONCAT('%', :query, '%')")
    List<Cloth> searchClothes(String query);
}
