package com.rungroop.web.dto;

import com.rungroop.web.models.UserEntity;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ClothesDto {
    private Long id;
    @NotEmpty(message = "Cloth title should not be empty")
    private String title;
    @NotEmpty(message = "Price link should not be empty")
    private String price; // price can have discount syntax: 199 dis 99
    @NotEmpty(message = "Colors should not be empty")
    private String colors;
    @NotEmpty(message = "Collection should not be empty")
    private String collection;
    @NotEmpty(message = "Size should not be empty")
    private String size;
    @NotEmpty(message = "Material should not be empty")
    private String material;
    @NotEmpty(message = "Photo link should not be empty")
    private String photoUrl;
    @NotEmpty(message = "Content should not be empty")
    private String content;
    private UserEntity createdBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private List<EventDto> events;
    private String blockChain;
}
