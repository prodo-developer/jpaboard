package com.zerock.jpaboard.service;

import com.zerock.jpaboard.dto.BoardDTO;
import com.zerock.jpaboard.dto.PageRequestDTO;
import com.zerock.jpaboard.dto.PageResultDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("데이터 추가 로직")
    public void testRegister() {
        BoardDTO dto = BoardDTO.builder()
                .title("Test.")
                .content("Test...")
                .writerEmail("user55@aaa.com")
                .build();

        Long bno = boardService.register(dto);
    }

    @Test
    @DisplayName("1페이지에 해당하는 10개 게시글,회원,댓글의 수 처리")
    public void testList() {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for (BoardDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }
    }

    @Test
    @DisplayName("게시물 조회 처리")
    public void testGet() {

        Long bno = 100L;

        BoardDTO boardDTO = boardService.get(bno);

        System.out.println(boardDTO);
    }

    @Test
    @DisplayName("1번 게시글 삭제")
    public void testRemove() {
        Long bno = 1L;

        boardService.removeWithReplies(bno);
    }

    @Test
    @DisplayName("게시글 수정 : 조회하고 수정합니다.")
    public void testModify() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(2L)
                .title("제목 변경합니다.")
                .content("내용 변경합니다.")
                .build();

        boardService.modify(boardDTO);
    }
}