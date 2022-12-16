package com.nolnol.useminserver.domain.item;

import com.nolnol.useminserver.domain.member.Member;
import com.nolnol.useminserver.global.s3.S3Utils;
import com.nolnol.useminserver.web.item.model.ItemCreateRequestDto;
import com.nolnol.useminserver.web.item.model.ItemDetailResponseDto;
import com.nolnol.useminserver.web.item.model.ItemDto;
import com.nolnol.useminserver.web.item.model.MyItemListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final S3Utils s3Utils;

    @Override
    public Long create(Member member, ItemCreateRequestDto itemCreateRequestDto) throws IOException {
        MultipartFile imageFile = itemCreateRequestDto.getImageFile();
        String fileName = member.getId() + "-" + LocalDateTime.now() + "-" + imageFile.getOriginalFilename();
        String imageUrl = s3Utils.upload(imageFile, fileName);

        Item item = Item.builder()
                        .itemName(itemCreateRequestDto.getItemName())
                        .content(itemCreateRequestDto.getContent())
                        .owner(member)
                        .category(Category.valueOf(itemCreateRequestDto.getCategory()))
                        .availableStartTime(itemCreateRequestDto.getAvailableStartTime())
                        .availableEndTime(itemCreateRequestDto.getAvailableEndTime())
                        .chatUrl(itemCreateRequestDto.getChatUrl())
                        .imageUrl(imageUrl)
                        .build();

        item = itemRepository.save(item);
        return item.getId();
    }

    @Override
    public ItemDetailResponseDto getDetails(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(NoSuchElementException::new);

        return ItemDetailResponseDto.builder()
                                    .itemId(itemId)
                                    .itemName(item.getItemName())
                                    .owner(item.getOwner())
                                    .category(item.getCategory())
                                    .content(item.getContent())
                                    .chatUrl(item.getChatUrl())
                                    .imageUrl(item.getImageUrl())
                                    .availableStartTime(item.getAvailableStartTime())
                                    .availableEndTime(item.getAvailableEndTime())
                                    .state(item.getState())
                                    .build();
    }

    @Override
    public List<Item> getItems(String category, Long cursorId) {
        Item cursorItem;
        if (cursorId == 0) {
            cursorItem = itemRepository.findByMinStartTime().orElseThrow(NoSuchElementException::new);
            cursorId = cursorItem.getId();
        } else {
            cursorItem = itemRepository.findById(cursorId).orElseThrow(NoSuchElementException::new);
        }

        category = category.toUpperCase();

        if (category.equals("ALL")) {
            return itemRepository.findAllBeforeExpiration(
                    LocalDateTime.now(),
                    cursorItem.getAvailableStartTime(),
                    cursorId,
                    10);
        }

        return itemRepository.findAllByCategoryBeforeExpiration(
                category,
                LocalDateTime.now(),
                cursorItem.getAvailableStartTime(),
                cursorId,
                10);
    }

    @Override
    public Item findById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    @Override
    public MyItemListDto findAllByOwnerId(Long memberId) {
        List<Item> items = itemRepository.findAllByOwnerIdOrderByIdDesc(memberId);
        MyItemListDto myItemListDto = new MyItemListDto();
        LocalDateTime now = LocalDateTime.now();

        items.forEach(item -> {
            if (item.getAvailableEndTime().isBefore(now)) {
                item.complete();
            }

            if (item.getState().equals(State.COMPLETE)) {
                myItemListDto.addCompletedItem(ItemDto.builder()
                                                      .itemId(item.getId())
                                                      .itemName(item.getItemName())
                                                      .imageUrl(item.getImageUrl())
                                                      .state(item.getState())
                                                      .build()
                );
            } else {
                myItemListDto.addNotCompletedItem(ItemDto.builder()
                                                         .itemId(item.getId())
                                                         .itemName(item.getItemName())
                                                         .imageUrl(item.getImageUrl())
                                                         .state(item.getState())
                                                         .build()
                );
            }
        });

        return myItemListDto;
    }

    @Override
    public void updateState(Item item, String state) {
        state = state.toUpperCase();
        if (State.AVAILABLE.getValue().equals(state)) {
            item.available();
        } else if (State.RESERVED.getValue().equals(state)) {
            item.reserved();
        } else {
            item.complete();
        }

        itemRepository.save(item);
    }

    @Transactional
    @Override
    public void delete(Item item) {
        itemRepository.delete(item);
        s3Utils.remove(item.getImageUrl());
    }
}
