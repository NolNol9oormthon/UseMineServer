package com.nolnol.useminserver.web.item;

import com.nolnol.useminserver.domain.item.ItemService;
import com.nolnol.useminserver.domain.member.Member;
import com.nolnol.useminserver.domain.member.MemberService;
import com.nolnol.useminserver.web.item.model.ItemCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

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
}
