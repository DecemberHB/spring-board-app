package kr.co.sboard.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import kr.co.sboard.dto.SessionDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender; // 메일 센더 주입
    private final SessionDataDTO sessionData;

    @Value("${spring.mail.username}") // 설정값으로 초기화
    private String sender;
    // 이메일 전송 !

    //@Autowired - 이렇게 하면 안됨
    //private  HttpSession session;

    public void sendCode(String receiver){ // 수신자

        MimeMessage message = mailSender.createMimeMessage(); // 메일

        int code = ThreadLocalRandom.current().nextInt(100000, 1000000); //6자리 코드

        String title = "sboard 인증 코드 입니다. ";
        String content = "<h1>인증코드는" + code + "입니다.</h1>";

        try {
            message.setFrom(new InternetAddress(sender,"보내는 사람","UTF-8"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject(title);
            message.setContent(content, "text/html;charset=UTF-8");

            // 메일 전송
            mailSender.send(message);

            //현재 세션 저장(code)-  현재 클라이언트
            sessionData.setCode(String.valueOf(code));

        }catch (Exception e){
            log.error(e.getMessage());
        }


    }

    public boolean verifyCode(String code){

        // 현재 세션 코드 가져오기
        String sessCode = sessionData.getCode();


        if(sessCode.equals(code)){
            return true;
        }

        return false;
    }
}
