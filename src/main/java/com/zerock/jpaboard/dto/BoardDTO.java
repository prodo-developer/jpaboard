package com.zerock.jpaboard.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO를 구성하는 이유
 * 화면에 전달하는 데이터이거나, 반대로 화면쪽에서 전달되는 데이터를 기준으로 하기 때문에
 * 엔티티 클래스의 구성과 일치하지 않는 경우가 많습니다.
 * BoardDTO의 경우 Member에 대한 차조는 구성하지 않고 작성합니다.
 */

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long bno;

    private String title;

    private String content;

    private String writerEmail; // 작성자의 이메일(id)

    private String writerName; // 작성자의 이름

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    private int replyCount; // 해당 게시글의 댓글 수
}
