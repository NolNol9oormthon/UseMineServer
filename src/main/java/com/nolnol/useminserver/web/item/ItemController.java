package com.nolnol.useminserver.web.item;

import com.nolnol.useminserver.domain.item.Item;
import com.nolnol.useminserver.domain.item.ItemService;
import com.nolnol.useminserver.domain.member.Member;
import com.nolnol.useminserver.domain.member.MemberService;
import com.nolnol.useminserver.web.item.model.ItemCreateRequestDto;
import com.nolnol.useminserver.web.item.model.ItemDetailResponseDto;
import com.nolnol.useminserver.web.item.model.ItemListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final MemberService memberService;

    @PostMapping("/items")
    public ResponseEntity<Void> createItem(@Valid @ModelAttribute ItemCreateRequestDto itemCreateRequestDto) throws IOException {
        Member owner = memberService.findById(itemCreateRequestDto.getOwnerId());
        itemService.create(owner, itemCreateRequestDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/items/{itemId}")
    public ResponseEntity<ItemDetailResponseDto> getItemDetails(@RequestHeader("Logined-User") Long memberId, @PathVariable Long itemId) {
        Member loginedMember = memberService.findById(memberId);
        ItemDetailResponseDto response = itemService.getDetails(itemId);
        response.checkOwner(loginedMember);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemListResponseDto>> getItems(@RequestParam(name = "category", required = false) String category, @RequestParam(name="cursorId", required = false) Long cursorId) {
        List<Item> itemList = itemService.getItems(category, cursorId);
        List<ItemListResponseDto> response = itemList.stream()
                                                     .map(ItemListResponseDto::new)
                                                     .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
