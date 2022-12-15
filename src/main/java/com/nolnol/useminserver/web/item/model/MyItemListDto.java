package com.nolnol.useminserver.web.item.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MyItemListDto {
    private List<ItemDto> notCompletedItems;
    private List<ItemDto> completedItems;

    public MyItemListDto() {
        this.notCompletedItems = new ArrayList<>();
        this.completedItems = new ArrayList<>();
    }

    public void addCompletedItem(ItemDto itemDto) {
        this.completedItems.add(itemDto);
    }

    public void addNotCompletedItem(ItemDto itemDto) {
        this.notCompletedItems.add(itemDto);
    }
}
