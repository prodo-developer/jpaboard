package com.zerock.jpaboard.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer") // @ToString는 항상 exclude 합니다. 지연로딩 사용시 반드시 선언
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    //    @ManyToOne // 다대일 디폴트 (FetchType.EAGER)
    @ManyToOne(fetch = FetchType.LAZY) // 다대일 명시적으로 지연 로딩 지정
    private Member writer; // 연관관계 지정

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }
}
