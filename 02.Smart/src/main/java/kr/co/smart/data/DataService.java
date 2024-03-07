package kr.co.smart.data;

import kr.co.smart.common.PageVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    @Autowired @Qualifier("TeamA") private SqlSession sql;

    public int insert(DataVO vo){
        return sql.insert("data.insert", vo);
    }

//    신규 공지 글 저장
    public int notice_register(DataVO vo){
        return sql.insert("notice.register", vo);
    }

//    공지 글 목록 조회
    public List<DataVO> notice_list(){
        return sql.selectList("notice.list");
    }

//    페이징 처리 후 목록 조회
    public PageVO notice_list(PageVO page){
       page.setTotalList(sql.selectOne("notice.totalList", page));
       page.setList(sql.selectList("notice.list", page));
        return page;

    }



//    공지 글 정보 조회
    public DataVO notice_info(int id){
        return sql.selectOne("notice.info", id);
    }
//    공지 글 정보 조회 수 변경
    public void notice_read(int id){
        sql.update("notice.read",id);
    }

//    공지 글 정보 변경 저장
    public int notice_update(DataVO vo){
        return sql.update("notice.update", vo);
    }

//    공지 글 정보 삭제
    public int notice_delete(int id){
        return sql.delete("notice.delete", id);
    }

    //신규답글저장
    public int notice_replyRegister(DataVO vo) {
        return sql.insert("notice.replyRegister", vo);
    }

}
