package kr.co.sboard.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.sboard.dto.PageRequestDTO;
import kr.co.sboard.entity.QArticle;
import kr.co.sboard.entity.QUser;
import kr.co.sboard.repository.custom.ArticleRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    /**
    *    반드시 이름을 ArticleRepositoryImpl로 해야함
     *    ArticleRepositoryCustomImpl로 하면 QueryDSL 생성 에러가 발생함
    *
    * */

    private final JPAQueryFactory jpaQueryFactory; // 쿼리 DSL에서 Bean 등록 함
    private QArticle qArticle = QArticle.article;
    private QUser qUser = QUser.user;

    @Override
    public Page<Tuple> selectArticleAllForList(PageRequestDTO pageRequestDTO, Pageable pageable) {
       List<Tuple> tupleList = jpaQueryFactory.select(qArticle, qUser.nick) // qarticle, qUser(nick)만 필요 전부
                .from(qArticle)
                .join(qUser)
                .on(qArticle.writer.eq(qUser.usid)) // 같은거 ==
                .offset(pageable.getOffset()) // jpa 페이징
                .limit(pageable.getPageSize()) // jpa 페이징 10까지
                .orderBy(qArticle.ano.desc())
                .fetch();

       // 전체 게시물 갯 수
       long total = jpaQueryFactory.select(qArticle.count()).from(qArticle).fetchOne();



        return new PageImpl<Tuple>(tupleList, pageable, total);
    }

    @Override
    public Page<Tuple> selectArticleAllForSearch(PageRequestDTO pageRequestDTO, Pageable pageable) {


        String serarchType = pageRequestDTO.getSearchType();
        String keyword = pageRequestDTO.getKeyword();

        // 검색 타입에 따라 where 조건 표현식 생성(동적 쿼리) * 검색 쿼리 중점*
        BooleanExpression expression = null;

        if(serarchType.equals("title")){
            expression = qArticle.title.contains(keyword);
        }else if(serarchType.equals("content")){
            expression = qArticle.content.contains(keyword);
        }else if(serarchType.equals("writer")){
            //expression = qArticle.nick.contains(keyword);
        }



        // 전체 게시물 갯 수
        List<Tuple> tupleList = jpaQueryFactory.select(qArticle, qUser.nick) // qarticle, qUser(nick)만 필요 전부
                .from(qArticle)
                .join(qUser)
                .where(expression)
                .on(qArticle.writer.eq(qUser.usid)) // 같은거 ==
                .offset(pageable.getOffset()) // jpa 페이징
                .limit(pageable.getPageSize()) // jpa 페이징 10까지
                .orderBy(qArticle.ano.desc())
                .fetch();

        // 전체 게시물 갯 수
        long total = jpaQueryFactory.select(qArticle.count()).from(qArticle).fetchOne();



        return new PageImpl<Tuple>(tupleList, pageable, total);

    }
}
