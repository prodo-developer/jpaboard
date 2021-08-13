package com.zerock.jpaboard.service;

import com.zerock.jpaboard.dto.BoardDTO;
import com.zerock.jpaboard.dto.PageRequestDTO;
import com.zerock.jpaboard.dto.PageResultDTO;
import com.zerock.jpaboard.entity.Board;
import com.zerock.jpaboard.entity.Member;
import com.zerock.jpaboard.repository.BoardRepository;
import com.zerock.jpaboard.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepository repository; // 자동 생성자 주입 final

    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {

        log.info(dto);

        Board board = dtoToEntity(dto);

        repository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);

        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board) en[0],(Member)en[1],(Long)en[2]));

//      내림차순
//      Page<Object[]> result = repository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));
//      검색조건 & 내림차순
        Page<Object[]> result = repository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending())
        );

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) {
        Object result = repository.getBoardByBno(bno);

        Object[] arr = (Object[]) result;

        return entityToDTO((Board)arr[0], (Member)arr[1], (Long)arr[2]);
    }

    // 게시글과 댓글을 함께 삭제 해야하기 때문에 트랙잭션 꼭 선언!
    // JPQL을 이용해서 update, delete를 실행하기 때문에 @Modifying 필수!
    @Transactional
    @Override
    public void removeWithReplies(Long bno) {

        // 댓글 부터 삭제
        replyRepository.deleteByBno(bno);

        // 게시글 삭제
        repository.deleteById(bno);
    }

    // getOne : Lazy Evaluation을 적용하기 위해 참조만 리턴해요. 해당 엔티티가 없을 경우 EntityNotFoundException이 발생합니다.
    // -> JPA는 @Transactional 로 묶인 곳에서 Entity 를 관리
    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {
        Board board = repository.getOne(boardDTO.getBno());

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        repository.save(board);
    }
}