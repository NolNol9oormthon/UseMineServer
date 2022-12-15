package com.nolnol.useminserver.domain.item;

import com.nolnol.useminserver.domain.member.Member;
import com.nolnol.useminserver.web.item.model.ItemCreateRequestDto;

import java.io.IOException;

public interface ItemService {

    Long create(Member member, ItemCreateRequestDto itemCreateRequestDto) throws IOException;
}