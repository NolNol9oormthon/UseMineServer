package com.nolnol.useminserver.domain.item;

import com.nolnol.useminserver.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String chatUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member owner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(length = 30, nullable = false)
    private String itemName;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime availableStartTime;

    @Column(nullable = false)
    private LocalDateTime availableEndTime;

    @Builder
    public Item(String content, String chatUrl, Member owner, Category category, String itemName, String imageUrl, LocalDateTime availableStartTime, LocalDateTime availableEndTime) {
        this.content = content;
        this.chatUrl = chatUrl;
        this.owner = owner;
        this.category = category;
        this.itemName = itemName;
        this.imageUrl = imageUrl;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
        this.state = State.AVAILABLE;
    }
}

