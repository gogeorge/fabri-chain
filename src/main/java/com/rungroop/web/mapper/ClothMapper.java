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
    
                // Read JSON data
                JsonNode jsonNode = objectMapper.readTree(new File("src/main/java/com/rungroop/web/blockchain/data.json"));
    
                // Iterate over each block
                for (JsonNode blockNode : jsonNode) {
                    // Get transactions array for each block
                    JsonNode transactionsNode = blockNode.get("transactions");
                    // System.out.println("Transactions for Block:");
                    // Iterate over each transaction in the transactions array
                    for (JsonNode transactionNode : transactionsNode) {
                        String source = transactionNode.get("source").asText();
                        String destination = transactionNode.get("destination").asText();
                        long quantity = transactionNode.get("quantity").asLong();
                        // System.out.println("Source: " + source + ", Destination: " + destination + ", Quantity: " + quantity);
                    }
                }

                String source = jsonNode.get(0).get("transactions").get(0).get("source").asText();
                String destination = jsonNode.get(0).get("transactions").get(0).get("destination").asText();
                long quantity = jsonNode.get(0).get("transactions").get(0).get("quantity").asLong();
                return "Source: " + source + ", Destination: " + destination + ", Quantity: " + quantity;

            } catch (Exception e) {
                e.printStackTrace();
                return "no-chain";
            }
        }

        public static Cloth mapToClothes(ClothesDto cloth) {
            Cloth clothDto = Cloth.builder()
                    .id(cloth.getId())
                    .title(cloth.getTitle())
                    .price(cloth.getPrice())
                    .colors(cloth.getColors())
                    .collection(cloth.getCollection())
                    .material(cloth.getMaterial())
                    .size(cloth.getSize())
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
                    .price(cloth.getPrice())
                    .colors(cloth.getColors())
                    .collection(cloth.getCollection())
                    .material(cloth.getMaterial())
                    .size(cloth.getSize())
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
