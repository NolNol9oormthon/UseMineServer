package com.nolnol.useminserver.domain.member;

import com.nolnol.useminserver.domain.post.Item;
import lombok.AllArgsConstructor;
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
    private String id;

    @Column(length = 30, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profileUrl;

    @OneToMany(mappedBy = "owner")
    private List<Item> items = new ArrayList<>();
}
