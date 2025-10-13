package kr.co.sboard.mapper;

import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.CommentDTO;
import kr.co.sboard.dto.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {
    public ArticleDTO select(int cno);



    // @Param => ibatis~ 로 Mybatis SQL 매퍼파일(XML)에서 해당 매개변수를 참조할 수 있는 어노테이션 , 반드시 선언
    // 검색, 페이징처리 까지 한번에 처리할꺼임
    public List<ArticleDTO> selectAll(@Param("pageRequestDTO") PageRequestDTO pageRequestDTO);
    public int selectCountTotal(@Param("pageRequestDTO") PageRequestDTO pageRequestDTO);


}