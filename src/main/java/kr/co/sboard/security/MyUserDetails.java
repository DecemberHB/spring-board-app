package kr.co.sboard.security;

import kr.co.sboard.entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
public class MyUserDetails implements UserDetails { // 자동 임포트로 선택

    // 권한 목록
    private User user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 계정 목록 리스트 생성, 인가 처리에 사용
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUs_role())); // 계정 권한 앞에 접두어 ROLE_ 작성
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPass();
    }

    @Override
    public String getUsername() {
        return user.getUsid(); // Username => ID를 뜻함
    }

    @Override
    public boolean isAccountNonExpired() {

        // 계정 만료 여부(true: 만료 안됨 , false : 만료) 개발용이라 일단 true
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
       // 계정 잠김 여부
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 비밀번호 만료 여부
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 계정 활성화 여부
        return true;
    }
}
