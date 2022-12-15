package com.nolnol.useminserver.web.item.model;

import com.nolnol.useminserver.domain.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ItemListResponseDto {

    private Long itemId;
    private String itemName;
    private Long ownerId;
    private String ownerNickname;
    private LocalDateTime availableStartTime;
    private LocalDateTime availableEndTime;
    private String imageUrl;
    private String category;
    private String state;

    public ItemListResponseDto(Item item) {
        this.itemId = item.getId();
        this.itemName = item.getItemName();
        this.ownerId = item.getOwner().getId();
        this.ownerNickname = item.getOwner().getNickname();
        this.availableStartTime = item.getAvailableStartTime();
        this.availableEndTime = item.getAvailableEndTime();
        this.imageUrl = item.getImageUrl();
        this.category = item.getCategory().getValue();
        this.state = item.getState().getValue();
    }
}
