package com.zerock.jpaboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Reply를 컨트롤러와 서비스 영역에서 처리하기 위한 용도
 *
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyDTO {

    private Long rno;

    private String text;

    private String replyer;

    private Long bno; // 게시글 번호

    private LocalDateTime regDate, modDate;
}