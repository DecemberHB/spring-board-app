package kr.co.sboard.mapper;

import kr.co.sboard.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    public CommentDTO select(int cno);
    public List<CommentDTO> selectAll(int ano);
    public void insert(CommentDTO commentDTO);
    public void update(CommentDTO commentDTO);
    public void delete(int cno);
}