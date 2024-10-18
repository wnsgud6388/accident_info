package com.projectITS.accident_info.service;

import com.projectITS.accident_info.dto.Accident_infoDTO;
import com.projectITS.accident_info.dto.PageRequestDTO;
import com.projectITS.accident_info.dto.PageResultDTO;
import com.projectITS.accident_info.entity.Accident_info;


public interface Accident_infoService {

    // 방명록을 신규 저장하는 메서드
    Long register(Accident_infoDTO guestBookDTO);

    // 방명록을 페이징하여 가져오는 메서드
    PageResultDTO<Accident_infoDTO, Accident_info> getList(PageRequestDTO pageRequestDTO);

    // GuestBookDTO(DTO)를 GuestBook(Entity)로 변환하는 메서드 (default메서드로 작성)
    // default 메서드 : 인터페이스에 실제 내용(body)을 가지는 메서드를 작성할 수 있는데, 
    // 이 때 메서드 앞에 default라는 키워드를 붙여야 하고, default메서드에서는 반드시 반환값을 정해 반환을 해야한다.

    default Accident_info dtoToEntity(Accident_infoDTO guestBookDTO){
        Accident_info entity = Accident_info.builder()
            .gno(guestBookDTO.getGno())
            .title(guestBookDTO.getTitle())
            .content(guestBookDTO.getContent())
            .writer(guestBookDTO.getWriter())
            .build();
        
        return entity;
        // 위 메서드는 유저가 입력한 데이터(방명록 신규저장, 수정)를 DB에 저장하기 위해 entity로 바꾸는 메서드
        // regDate, modDate는 entity가 DB에 저장될 때 자동으로 감지하여 저장되는 값이므로 DTO에는 그 값이 없다.
        
    }

    // GuestBook(Entity) 를 GuestBookDTO(DTO)로 변환하는 메서드 (default 메서드로 작성)
    default Accident_infoDTO entityToDTO(Accident_info guestBook){

        Accident_infoDTO guestBookDTO = Accident_infoDTO.builder()
            .gno(guestBook.getGno())
            .title(guestBook.getTitle())
            .content(guestBook.getContent())
            .writer(guestBook.getWriter())
            .regDate(guestBook.getRegDate())
            .modDate(guestBook.getModDate())
            .build();

        return guestBookDTO;
    }

    // 방명록 gno를 가지고 해당 번호의 방명록을 조회하는 메서드
    Accident_infoDTO read(Long gno);
}
