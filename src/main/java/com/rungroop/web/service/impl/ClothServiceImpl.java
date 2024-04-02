package com.rungroop.web.service.impl;

import com.rungroop.web.dto.ClothesDto;
import com.rungroop.web.models.Cloth;
import com.rungroop.web.models.UserEntity;
import com.rungroop.web.repository.ClothRepository;
import com.rungroop.web.repository.UserRepository;
import com.rungroop.web.security.SecurityUtil;
import com.rungroop.web.service.ClothService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

 import static com.rungroop.web.mapper.ClothMapper.mapToClothes;
 import static com.rungroop.web.mapper.ClothMapper.mapToClothesDto;

@Service
public class ClothServiceImpl implements ClothService {
    private ClothRepository clothRepository;
    private UserRepository userRepository;

    @Autowired
    //initializes the constructor
    public ClothServiceImpl(ClothRepository clothRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.clothRepository = clothRepository;
    }

    @Override
    public List<ClothesDto> findAllClothes() {
        List<Cloth> clothes = clothRepository.findAll();
        return clothes.stream().map((cloth) -> mapToClothesDto(cloth)).collect(Collectors.toList());
        // return clothes.stream().toList().map((cloth) -> mapClothToClothesDto(cloth));
    }

    @Override
    public Cloth saveCloth(ClothesDto clothesDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username.split("@")[0]);
        System.out.println(":-> username: " + username);
        System.out.println(":-> user: " + user);
        Cloth cloth = mapToClothes(clothesDto);
        cloth.setCreatedBy(user);
        return clothRepository.save(cloth);       
    }

    @Override
    public ClothesDto findClothById(Long clothId) {
        Cloth cloth = clothRepository.findById(clothId).get();
        return mapToClothesDto(cloth);
    }

    @Override
    public void updateCloth(ClothesDto clubDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Cloth cloth = mapToClothes(clubDto);
        cloth.setCreatedBy(user);
        clothRepository.save(cloth);
    }

    @Override
    public void delete(Long clothId) {
        clothRepository.deleteById(clothId);
    }

    @Override
    public List<ClothesDto> searchClothes(String query) {
        List<Cloth> clubs = clothRepository.searchClothes(query);
        return clubs.stream().map(cloth -> mapToClothesDto(cloth)).collect(Collectors.toList());
    }
}
