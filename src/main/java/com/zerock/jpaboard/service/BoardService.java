package com.zerock.jpaboard.service;

import com.zerock.jpaboard.dto.BoardDTO;
import com.zerock.jpaboard.dto.PageRequestDTO;
import com.zerock.jpaboard.dto.PageResultDTO;
import com.zerock.jpaboard.entity.Board;
import com.zerock.jpaboard.entity.Member;

/**
 * Java 8 버전부터는 인터페이스의 실제 내용을 가지는 코드를 'default'라는 키워드로 생성할 수 있습니다.
 * 이를 이용하면 기존에 추상 클래스를 통해서 전달해야 하는 실제 코드를 인터페이스에 선언할 수 있습니다.
 * '인터페이스 -> 추상 클래스 -> 구현 클래스'의 형태로 구현되던 방식에서 추상 클래스를 생략하는것이 가능해 집니다.
 */

public interface BoardService {

    // 등록하기
    Long register(BoardDTO dto);

    // 목록 처리
    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    // 게시물 조회 처리
    BoardDTO get(Long bno);

    // 삭제 기능
    void removeWithReplies(Long bno);

    // 수정기능
    void modify(BoardDTO boardDTO);

    // 저장 처리
    default Board dtoToEntity(BoardDTO dto) {

        Member member = Member.builder().email(dto.getWriterEmail()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();

        return board;
    }

    // 페이징 처리
    default BoardDTO entityToDTO(Board board, Member member, Long replyCount) {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue()) // long으로 나오므로 int로 처리
                .build();

        return boardDTO;
    }
}
