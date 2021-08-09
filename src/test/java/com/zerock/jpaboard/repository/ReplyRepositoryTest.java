package com.zerock.jpaboard.repository;

import com.zerock.jpaboard.entity.Board;
import com.zerock.jpaboard.entity.Member;
import com.zerock.jpaboard.entity.Reply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    @DisplayName("300개의 댓글을 1~100사이의 번호로 추가합니다")
    public void insertBoard() {
        IntStream.rangeClosed(1, 300).forEach(i -> {

            long bno = (long) (Math.random() * 100) + 1;

            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("Reply....." + i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);
        });
    }

    @Test
    @DisplayName("다대일로 참조하고 있는 댓글 조회")
    public void readReply1() {
        Optional<Reply> result = replyRepository.findById(1L);

        Reply reply = result.get();

        System.out.println(reply);
        System.out.println(reply.getBoard());
    }
}