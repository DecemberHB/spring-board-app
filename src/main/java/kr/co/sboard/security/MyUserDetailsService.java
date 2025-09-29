package kr.co.sboard.security;

import kr.co.sboard.entity.User;
import kr.co.sboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService { // 임포트설정으로 생성


    private final UserRepository userRepository; // userRepository 를 생성하고 가져온거
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자가 입력한 아이디로 사용자 조회, 비밀번호에 대한 검증은 이전 컴포넌트인 AuthenticationProvider에서 수행
       Optional<User> optuser = userRepository.findById(username); // select는 Option

               if(optuser.isPresent()){
                 User user =  optuser.get(); // 안전하게 꺼내는거
                   // 인증 객체 생성
                    MyUserDetails myUserDetails = MyUserDetails
                                        .builder()
                                        .user(user)
                                        .build();
                    // 반환 되는 인증 객체가 Security Context Holder에 Autgenication 으로 저장
                    return myUserDetails;
               }
        return null;
    }
}
