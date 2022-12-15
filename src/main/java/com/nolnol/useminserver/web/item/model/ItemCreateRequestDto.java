package com.nolnol.useminserver.web.item.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ItemCreateRequestDto {

    @NotBlank
    @Size(min = 1, max = 30)
    private String itemName;

    @NotBlank
    private String content;

    @NotBlank
    private String ownerId;

    @NotBlank
    private String category;

    @NotBlank
    private String chatUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime availableStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime availableEndTime;

    private MultipartFile imageFile;
}
