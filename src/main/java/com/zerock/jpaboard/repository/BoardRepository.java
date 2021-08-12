package com.zerock.jpaboard.repository;

import com.zerock.jpaboard.entity.Board;
import com.zerock.jpaboard.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {
    // 한개의 로우(Object) 내에 Object[ ]로 나옴
    // 연관관계가 없는 엔티티 조인 처리에는 on
    @Query("select b, w from Board b left outer join b.writer w where b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    // 댓글 조회
    // '특정 게시물과 해당 게시물에 속한 댓글들을 조회' 해야 하는 상황을 생각하면
    // board와 reply 테이블을 조인해서 쿼리를 작성하게 됩니다.
    // 순수 sql문
    // select board.bno, board.title, board.writer_email, rno, text
    // from board left outer join reply on reply.board_bno = board.bno
    // where board.bno = 100;
    @Query("SELECT b, r FROM Board b left outer join Reply r ON r.board = b WHERE b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    // 페이징 처리
    @Query(value = "SELECT b, w, count(r) " +
            " FROM Board b " +
            " LEFT OUTER JOIN b.writer w " +
            " LEFT OUTER JOIN Reply r ON r.board = b " +
            " GROUP BY b",
            countQuery = "SELECT count(b) FROM Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable); // 목록화면에 피료한 데이터

    // 조회 화면에서 필요한 JQPL 구성하기
    @Query("SELECT b, w, count(r)" +
            " FROM Board b LEFT OUTER JOIN b.writer w " +
            " LEFT OUTER JOIN Reply r ON r.board = b" +
            " WHERE b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);
}
