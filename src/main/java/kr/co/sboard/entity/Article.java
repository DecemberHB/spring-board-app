package kr.co.sboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
//@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "SB_ARTICLE")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ano;

    private String cate;
    private String title;
    private String content;
    private int comment_cnt;
    private int file_cnt;
    private int hit_cnt;
    private String writer;


    private String reg_ip;

    @CreationTimestamp
    private LocalDateTime wdate;

    // File - view

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ano")
    private List<File> fileList;


    // 추가 필드 (컬럼에서는 제외 시키기 위해)
    @Transient
    private String nick;


    // nick만 setter 직접 작성
    public void setNick(String nick) {
        this.nick = nick;
    }
}