package kr.co.sboard.service;

import kr.co.sboard.dto.CommentDTO;
import kr.co.sboard.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentMapper commentMapper;

    public List<CommentDTO> getCommentAll(int ano) {
        return commentMapper.selectAll(ano);
    }

    public CommentDTO getComment(int cno) {
        return commentMapper.select(cno);
    }

    public void save(CommentDTO commentDTO) {
        commentMapper.insert(commentDTO); // 얕은복사 ?ㅁ?
    }

    public void update(CommentDTO commentDTO){
        commentMapper.update(commentDTO);
    }

    public void delete(int cno){
        commentMapper.delete(cno);
    }
}