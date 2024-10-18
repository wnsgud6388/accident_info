package com.projectITS.accident_info.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.projectITS.accident_info.dto.Accident_infoDTO;
import com.projectITS.accident_info.dto.PageRequestDTO;
import com.projectITS.accident_info.dto.PageResultDTO;
import com.projectITS.accident_info.entity.Accident_info;
import com.projectITS.accident_info.repository.Accident_infoRepository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class Accident_infoServiceImple implements Accident_infoService{
    private final Accident_infoRepository accident_infoRepository;

    @PersistenceContext
    private EntityManager em; // Entity를 수행할 객체

    @Override
    public Long register(Accident_infoDTO accident_infoDTO) {
        
        log.info(accident_infoDTO.toString());
        Accident_info entity = dtoToEntity(accident_infoDTO);

        entity = accident_infoRepository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<Accident_infoDTO, Accident_info> getList(PageRequestDTO pageRequestDTO) {
        // 페이징 정렬 방식
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("gno").descending());

        String jpql = "select a from Accident_info a where ";
        
        Page<Accident_info> result = null;

        // 검색어가 있을 겨우의 페이징 --------------------------------------------------------------------
        if(StringUtils.hasText(pageRequestDTO.getSearchType()) && StringUtils.hasText(pageRequestDTO.getSearchValue())){
            switch (pageRequestDTO.getSearchType()) {
                case "title":
                    jpql += "a.title like ";  
                    break;  
                case "writer":
                    jpql += "a.writer like ";
                    break;
                case "content":
                    jpql += "a.content like ";
                    break;
            }

            jpql += "concat('%',:searchValue,'%')order by a.gno desc" ;
       

            System.out.println("jpql = " +jpql);

            TypedQuery<Accident_info> query =  em.createQuery(jpql, Accident_info.class);     // 문자열로 된 JPQL쿼리문을 쿼리객체로 생성

            // searchValue값을 parameter에 대입
            query = query.setParameter("searchValue", pageRequestDTO.getSearchValue());

            // 페이징 되지 않은 결과
            List<Accident_info> list = query.getResultList();
            int start = (int)pageable.getOffset();
            int end = Math.min(list.size(), start + pageRequestDTO.getSize());

            List<Accident_info> pageContent = list.subList(start, end);
            result = new PageImpl<>(pageContent, pageable, list.size());
    }   else{
        // 페이징에 필요한 매개변수(PageRequestDTO) + 정렬방식) 을 넘겨주며 페이징하여 결과를 가져옴
        result = accident_infoRepository.findAll(pageable);
    }
       
        

        // 람다식을 이용한 방법
        // Entity를 DTO로 변환하는 함수를 전달하기 위해 준비
        Function<Accident_info, Accident_infoDTO> fn = (entity -> entityToDTO(entity));

        // Page<GuestBook>을 함수에 전달하여, Entity 각각을 DTO로 변환한 후 List로 만들고, 그 List를
        return new PageResultDTO<>(result, fn);

        // 람다식을 이용하지 않은 방법
        // List<GuestBook> guestBookList = result.getContent();
        
        // List<GuestBookDTO> guestBookDTOList = new ArrayList<>();
        // for(GuestBook g : guestBookList){
        //     GuestBookDTO dto = entityToDTO(g);
        //         guestBookDTOList.add(dto);
        // };
       
        // return new PageResultDTO<>(guestBookDTOList::new);
    }

    @Override
    public Accident_infoDTO read(Long gno) {
        
        Accident_infoDTO guestBookDTO = null;

        Optional<Accident_info> result = accident_infoRepository.findById(gno);
        if(result.isPresent()){
            Accident_info guestBook = result.get();
            guestBookDTO = entityToDTO(guestBook);
        }
        return guestBookDTO;
    }   
}
