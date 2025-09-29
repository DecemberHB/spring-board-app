package kr.co.sboard.repository;

import kr.co.sboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // 사용자 정의 쿼리 메서드(유저서비스 CountUser)
    public int countByUsid(String usid);
    public int countByNick(String nick);
    public int countByEmail(String email);
    public int countByHp(String hp);
}
