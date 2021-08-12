package com.zerock.jpaboard.repository;

import com.zerock.jpaboard.entity.Board;
import com.zerock.jpaboard.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("회원 100명이 게시글 100개 작성합니다.")
    public void insertBoard() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder().email("user" + i + "@aaa.com").build();

            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        });
    }

    @Transactional
    @Test
    @DisplayName("다대일로 참조하고 있는 게시판 조회")
    public void testRead1() {
        Optional<Board> result = boardRepository.findById(100L);// DB에 존재하는 번호

        Board board = result.get();

        System.out.println(board);

        // 지연로딩으로 변경함으로써 board 테이블만 가져오기 때문이고, 이미 db와의 연결은 끝난 상태 입니다.
        // 이를 해결 하기 위해 다시 한번 데이터베이스 연결이 필요합니다.
        // @Transactional이 바로 이러한 처리에 도움을 줍니다.
        System.out.println(board.getWriter());
    }

    @Test
    @DisplayName("@Query를 이용한 left join 확인")
    public void testReadWithWriter() {
        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[]) result;

        System.out.println("-----------------------------------");
        System.out.println(Arrays.toString(arr));
    }

    @Test
    @DisplayName("@Query를 이용한 댓글 100번 조회")
    public void testGetBoardWithReply() {
        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    @DisplayName("페이징 처리 JPQL")
    public void testWithReplyCount() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {
            Object[] arr = (Object[]) row;
            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    @DisplayName("조회 화면에서 필요한 JQPL 구성하기")
    public void testRead3() {
        Object result = boardRepository.getBoardByBno(100L);

        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testSearch1() {

        boardRepository.search1();

    }

    @Test
    public void testSearchPage() {

        Pageable pageable =
                PageRequest.of(0,10,
                        Sort.by("bno").descending()
                                .and(Sort.by("title").ascending()));

        Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);

    }
}