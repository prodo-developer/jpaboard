package com.zerock.jpaboard.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * PageResultDTO 역할 (페이지 결과 처리)
 * Repository에서는 페이지 처리 결과를 Page<Entity> 타입으로 반환하게 됩니다.
 * Page<Entity>의 엔티티 객체들을 DTO객체로 변환해서 자료구조로 담아 주어야 합니다.
 * 화면 출력에 필요한 페이지 정보들을 구성해 주어야 합니다.
 * DTO, EN -> DTO와 Entity를 의미
 */
@Data
public class PageResultDTO<DTO, EN> {

    // DTO 리스트
    private List<DTO> dtoList;

    // 총 페이지 번호
    private int totalPage;

    // 현재 페이지 번호
    private int page;

    // 목록 사이즈
    private int size;

    // 시작 페이지 번호, 끝 페이지 번호
    private int start, end;

    // 이전, 다음
    private boolean prev, next;

    // 페이지 번호 목록
    private List<Integer> pageList;

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {

        dtoList = result.stream().map(fn).collect(Collectors.toList());

        totalPage = result.getTotalPages();

        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {

        this.page = pageable.getPageNumber() + 1;// 0부터 시작하므로 1을 추가
        this.size = pageable.getPageSize();

        // temp end page
        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;

        start = tempEnd - 9;

        prev = start > 1;

        end = totalPage > tempEnd ? tempEnd : totalPage;

        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
