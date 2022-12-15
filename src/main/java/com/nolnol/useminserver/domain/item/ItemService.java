package com.nolnol.useminserver.domain.item;

import com.nolnol.useminserver.domain.member.Member;
import com.nolnol.useminserver.web.item.model.ItemCreateRequestDto;
import com.nolnol.useminserver.web.item.model.ItemDetailResponseDto;
import com.nolnol.useminserver.web.item.model.MyItemListDto;

import java.io.IOException;
import java.util.List;

public interface ItemService {

    Long create(Member member, ItemCreateRequestDto itemCreateRequestDto) throws IOException;

    ItemDetailResponseDto getDetails(Long itemId);

    List<Item> getItems(String category, Long cursorId);

    Item findById(Long itemId);

    MyItemListDto findAllByOwnerId(Long memberId);

    void updateState(Item item, String state);
}