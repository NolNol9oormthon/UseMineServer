package com.nolnol.useminserver.domain.member;

import com.nolnol.useminserver.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @Column(name = "member_id")
    private Long id;

    @Column(length = 30, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profileUrl;

    @OneToMany(mappedBy = "owner")
    private List<Item> items = new ArrayList<>();

    @Builder
    public Member(Long id, String nickname, String profileUrl) {
        this.id = id;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
    }
}
