package kr.co.sboard.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
@Component
@SessionScope // 각 세션(사용자) 마다 생성
public class SessionDataDTO {

    // 세션을 어노테이션으로 사용하기 위해 생성함

    private String code;

}
