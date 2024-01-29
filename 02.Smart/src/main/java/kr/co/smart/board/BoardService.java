package kr.co.smart.board;

import kr.co.smart.common.PageVO;
import kr.co.smart.notice.NoticeVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired @Qualifier("hanul") private SqlSession sql;

//    신규 방명록 글 저장
    public int board_register(BoardVO vo){
        return sql.insert("board.register", vo);
    }

//    방명록 글 목록 조회
    public PageVO board_list(PageVO page){
       page.setTotalList(sql.selectOne("board.totalList"));
       page.setList(sql.selectList("board.list", page));
        return page;
    }

//   선택한 방명록 글 정보 조회
    public BoardVO board_info(int id){
        return sql.selectOne("board.info", id);
    }


//    방명록 글 정보 변경 저장
    public int board_update(BoardVO vo){
        return sql.update("notice.update", vo);
    }

    //    방명록 글 조회수 변경 저장
    public int board_read(int id){
        return sql.update("board.read", id);
    }

//    방명록 글 정보 삭제
    public int board_delete(int id){
        return sql.delete("notice.delete", id);
    }

}
