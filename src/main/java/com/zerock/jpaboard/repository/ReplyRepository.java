package com.zerock.jpaboard.repository;

import com.zerock.jpaboard.entity.Board;
import com.zerock.jpaboard.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Java 8 이상을 사용하는 경우 오류 메시지에 제공된 다른 옵션을 적용 할 수 있습니다.
 * 매개변수 앞에 반드시 @Param("bno") 이런식으로 선언해야 합니다.
 * @Param("bno") Long bno
 *
 * 자바 8이상에서 interface 파라미터 이름을 알아내려면 자바 컴파일러에 -parameters 옵션이 필요합니다.
 * 아마 이 옵션이 메이븐 또는 그레이들 통해서 설정 가능합니다.
 * 아직까지는 이 옵션없이 사용하는 경우가 많으므로 @Param은 꼭 사용해주세요 :)
 */
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    
    //Board 삭제시에 댓글들 삭제
    @Modifying
    @Query("delete from Reply r where r.board.bno =:bno ")
    void deleteByBno(@Param("bno") Long bno);

    //게시물로 댓글 목록 가져오기
    List<Reply> getRepliesByBoardOrderByRno(Board board);
}
