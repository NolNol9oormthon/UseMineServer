package com.nolnol.useminserver.web.item.model;

import com.nolnol.useminserver.domain.item.Category;
import com.nolnol.useminserver.domain.item.State;
import com.nolnol.useminserver.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ItemDetailResponseDto {

    private Long itemId;

    private String content;

    private String chatUrl;

    private Long ownerId;

    private String ownerNickname;

    private State state;

    private Category category;

    private String itemName;

    private String imageUrl;

    private LocalDateTime availableStartTime;

    private LocalDateTime availableEndTime;

    private boolean isOwner;

    @Builder
    public ItemDetailResponseDto(Long itemId, String content, String chatUrl, Member owner, State state, Category category, String itemName, String imageUrl, LocalDateTime availableStartTime, LocalDateTime availableEndTime) {
        this.itemId = itemId;
        this.content = content;
        this.chatUrl = chatUrl;
        this.ownerId = owner.getId();
        this.ownerNickname = owner.getNickname();
        this.state = state;
        this.category = category;
        this.itemName = itemName;
        this.imageUrl = imageUrl;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
        this.isOwner = false;
    }

    public void checkOwner(Member loginedMember) {
        if (this.ownerId.equals(loginedMember.getId())) {
            this.isOwner = true;
            return;
        }
        this.isOwner = false;
    }
}
