package com.nolnol.useminserver.domain.item;

import com.nolnol.useminserver.domain.member.Member;
import com.nolnol.useminserver.global.s3.S3Utils;
import com.nolnol.useminserver.web.item.model.ItemCreateRequestDto;
import com.nolnol.useminserver.web.item.model.ItemDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final S3Utils s3Utils;

    @Override
    public Long create(Member member, ItemCreateRequestDto itemCreateRequestDto) throws IOException {
        MultipartFile imageFile = itemCreateRequestDto.getImageFile();
        String fileName = member.getId() + "-" + imageFile.getOriginalFilename();
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
}
