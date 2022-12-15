package com.nolnol.useminserver.web.item;

import com.nolnol.useminserver.domain.item.Item;
import com.nolnol.useminserver.domain.item.ItemService;
import com.nolnol.useminserver.domain.item.State;
import com.nolnol.useminserver.domain.member.Member;
import com.nolnol.useminserver.domain.member.MemberService;
import com.nolnol.useminserver.web.item.model.*;
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
        Member owner = memberService.findById(Long.parseLong(itemCreateRequestDto.getOwnerId()));
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
    public ResponseEntity<List<ItemListResponseDto>> getItems(@RequestParam(name = "category") String category, @RequestParam(name = "cursorId") Long cursorId) {
        List<Item> itemList = itemService.getItems(category, cursorId);
        List<ItemListResponseDto> response = itemList.stream()
                                                     .map(ItemListResponseDto::new)
                                                     .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@RequestHeader("Logined-User") Long memberId, @PathVariable Long itemId) {
        Member loginedMember = memberService.findById(memberId);
        Item item = itemService.findById(itemId);

        if (!loginedMember.getId().equals(item.getOwner().getId())) {
            return ResponseEntity.badRequest().build();
        }

        itemService.delete(item);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/items/{itemId}")
    public ResponseEntity<Void> updateState(@RequestHeader("Logined-User") Long memberId, @PathVariable Long itemId, @RequestParam(name = "state") String state) {
        Member loginedMember = memberService.findById(memberId);
        Item item = itemService.findById(itemId);

        if (!loginedMember.getId().equals(item.getOwner().getId())) {
            return ResponseEntity.badRequest().build();
        }

        itemService.updateState(item, state);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my-items")
    public ResponseEntity<MyItemListDto> getMyItemList(@RequestHeader("Logined-User") Long memberId) {
        return ResponseEntity.ok(itemService.findAllByOwnerId(memberId));
    }
}
