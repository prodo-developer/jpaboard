package com.zerock.jpaboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * PageRequestDTO 역할 (페이지 요청 처리)
 * 화면에서 전달되는 page라는 파라미터와 size라는 파라미터를 수집하는 역할을 합니다.
 * JPA쪽에서 사용하는 Pageable타입의 객체를 생성하는 것입니다.
 * 페이지가 0부터 시작한다는 점을 감안해서 1페이지 경우 0이 될 수 있도록 page-1을 하는 형태로 작성합니다.
 */

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page;
    private int size;

    // 서버측 검색 처리에 필요한 필드
    private String type;
    private String keyword;

    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page -1, size, sort);
    }
}
