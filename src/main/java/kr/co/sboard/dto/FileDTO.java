package kr.co.sboard.dto;

import lombok.*;
import org.springframework.core.io.Resource;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class FileDTO {

    private int fno;
    private int ano;
    private String oname;
    private String sname;
    private int download;
    private String rdate;

    // 추가 필드
    private String contentType;
    private Resource resource;



}
