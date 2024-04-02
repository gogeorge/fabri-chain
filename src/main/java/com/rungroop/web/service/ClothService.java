package com.rungroop.web.service;

import com.rungroop.web.dto.ClothesDto;
import com.rungroop.web.models.Cloth;

import java.util.List;

public interface ClothService {
    List<ClothesDto> findAllClothes();
    Cloth saveCloth(ClothesDto clubDto);
    ClothesDto findClothById(Long clubId);
    void updateCloth(ClothesDto club);
    void delete(Long clubId);
    List<ClothesDto> searchClothes(String query);
}
