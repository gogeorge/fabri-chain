package com.rungroop.web.mapper;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rungroop.web.dto.ClothesDto;
import com.rungroop.web.models.Cloth;

public class ClothMapper {
        public static String getBlockChainJSON() {
            try {
                // Create ObjectMapper object
                ObjectMapper objectMapper = new ObjectMapper();

                // Read JSON data from file
                JsonNode jsonData = objectMapper.readTree(new File("src/main/java/com/rungroop/web/blockchain/data.json"));

                String source = jsonData.get("transactions").get(0).get("source").asText();
                String dest = jsonData.get("transactions").get(0).get("dest").asText();
                long quantity = jsonData.get("transactions").get(0).get("quantity").asLong();


                // Print the extracted source
                System.out.println("Source: " + source);
                return source + " -> " + dest + ": " + quantity + " quantity wasted";
            } catch (IOException e) {
                e.printStackTrace();
                return "no-chain-found";
            }
        }

        public static Cloth mapToClothes(ClothesDto cloth) {
            Cloth clothDto = Cloth.builder()
                    .id(cloth.getId())
                    .title(cloth.getTitle())
                    .photoUrl(cloth.getPhotoUrl())
                    .content(cloth.getContent())
                    .blockChain(getBlockChainJSON())
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
                    .blockChain(getBlockChainJSON())
                    .createdBy(cloth.getCreatedBy())
                    .createdOn(cloth.getCreatedOn())
                    .updatedOn(cloth.getUpdatedOn())
                    .build();
            return clothDto;
        }
    }
