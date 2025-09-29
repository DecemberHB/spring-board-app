package kr.co.sboard.service;

import kr.co.sboard.dto.UserDTO;
import kr.co.sboard.entity.User;
import kr.co.sboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mail.MailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper; // 모델 매퍼 주입
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화 주입
    private final EmailService emailService;
    public void save(UserDTO userDTO) {

        // 비밀번호 암호화
        String encodedPass =passwordEncoder.encode(userDTO.getPass());
        userDTO.setPass(encodedPass); // DTO에서 다시 Entity로 저장
        // Entityl에 setter가 없어서 user.set~ 불가

        // DTO를 Entity로 변환처리해서 저장
        User user= modelMapper.map(userDTO,User.class);
        userRepository.save(user);


    }
    public UserDTO getUser(String usid)
    {
        Optional<User> optuser = userRepository.findById(usid);

        if(optuser.isPresent()){
            User user = optuser.get();
            return modelMapper.map(user,UserDTO.class);

        }
        return null;
    }
    public void getUserAll() {}
    public void modify(){}
    public void remove(){}

    public int conutUser(String type, String value){

        int count = 0;
        if(type.equals("usid")){
            count = userRepository.countByUsid(value);
        }else if(type.equals("nick")){
            count = userRepository.countByNick(value);
        }else if(type.equals("email")){
            count = userRepository.countByEmail(value);

            if(count == 0){


                // 인증코드 이메일 전송
                emailService.sendCode(value);


            }
        }else if(type.equals("hp")){
            count = userRepository.countByHp(value);
        }


         return count;

    }

}
