package com.rungroop.web.mapper;

import com.rungroop.web.dto.ClothesDto;
import com.rungroop.web.models.Cloth;

public class ClothMapper {
        public static Cloth mapToClothes(ClothesDto cloth) {
            Cloth clothDto = Cloth.builder()
                    .id(cloth.getId())
                    .title(cloth.getTitle())
                    .photoUrl(cloth.getPhotoUrl())
                    .content(cloth.getContent())
                    .createdBy(cloth.getCreatedBy())
                    .createdOn(cloth.getCreatedOn())
                    .updatedOn(cloth.getUpdatedOn())
                    .build();
            return clothDto;
        }
    
        public static ClothesDto mapToClothesDto(Cloth cloth) {
            ClothesDto clothDto = ClothesDto.builder()
                    .id(cloth.getId())
                    .title(cloth.getTitle())
                    .photoUrl(cloth.getPhotoUrl())
                    .content(cloth.getContent())
                    .createdBy(cloth.getCreatedBy())
                    .createdOn(cloth.getCreatedOn())
                    .updatedOn(cloth.getUpdatedOn())
                    .build();
            return clothDto;
        }
    }
