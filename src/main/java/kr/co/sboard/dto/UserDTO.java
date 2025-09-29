package kr.co.sboard.dto;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.sboard.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String usid;
    private String pass;
    private String us_name;
    private String nick;
    private String email;
    private String hp;

    @Builder.Default
    private String us_role = "Member"; // 기본 Member로 줘야함
    private String zip;

    private String addr1;
    private String addr2;
    private String reg_ip; // 클라이언트아이피


    private String reg_date;
    private String leave_date; // 회원탈퇴떄 사용할꺼라서 저장할때도 들어감
    // 변환메서드가 너무 많다 ... 모델매퍼라는걸 이용해서 처리할거




}
