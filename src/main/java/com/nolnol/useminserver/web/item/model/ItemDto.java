package com.nolnol.useminserver.web.item.model;

import com.nolnol.useminserver.domain.item.State;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemDto {
    private Long itemId;
    private String itemName;
    private String imageUrl;
    private String state;

    @Builder

    public ItemDto(Long itemId, String itemName, String imageUrl, State state) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.imageUrl = imageUrl;
        this.state = state.getValue();
    }
}
