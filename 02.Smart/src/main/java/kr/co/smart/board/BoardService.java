package kr.co.smart.board;

import kr.co.smart.common.FileVO;
import kr.co.smart.common.PageVO;
import kr.co.smart.notice.NoticeVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    @Qualifier("hanul")
    private SqlSession sql;

    //    신규 방명록 글 저장
    public int board_register(BoardVO vo) {
        int dml = sql.insert("board.register", vo);
        if (dml == 1 && vo.getFileList() != null) {
            sql.insert("board.fileInsert", vo);
        }
        return dml;
    }

    //    방명록 글 목록 조회
    public PageVO board_list(PageVO page) {
        page.setTotalList(sql.selectOne("board.totalList", page));
        page.setList(sql.selectList("board.list", page));
        return page;
    }

    //   선택한 방명록 글 정보 조회
    public BoardVO board_info(int id) {

        BoardVO vo = sql.selectOne("board.info", id);
        vo.setFileList(sql.selectList("board.fileList", id));
        return vo;
    }
//  파일 정보 조회

    public FileVO board_file_info(int id) {
        return sql.selectOne("board.fileInfo", id);
    }

    //    파일 목록 조회
    public List<FileVO> board_file_list(int id) {
        return sql.selectList("board.fileList", id);
    }

    //    파일 목록 조회 다수
    public List<FileVO> board_file_list(String remove) {
        return sql.selectList("board.removeFileList", remove);
    }

    public int board_file_delete(String remove) {
        return sql.delete("board.fileDelete", remove);
    }

    //    방명록 글 정보 변경 저장
    public int board_update(BoardVO vo) {
        int dml = sql.update("board.update", vo);
//        첨부파일 저장
        if (dml == 1 && vo.getFileList() != null) {
            sql.insert("board.fileInsert", vo);
        }
        return dml;
    }

    //    방명록 글 조회수 변경 저장
    public int board_read(int id) {
        return sql.update("board.read", id);
    }

    //    방명록 글 정보 삭제
    public int board_delete(int id) {
        return sql.delete("board.delete", id);
    }

//    댓글 등록 저장 처리
    public int board_comment_register(BoardCommentVO vo){
        return sql.insert("board.commentRegister", vo);
    }
    //    댓글  변경 저장 처리
    public int board_comment_update(BoardCommentVO vo){
        return sql.update("board.commentUpdate", vo);
    }
//    댓글 삭제 처리
    public int board_comment_delete(int id){
        return sql.delete("board.commentDelete", id);
    }
//    댓글 목록 조회
    public List<BoardCommentVO> board_comment_list(int board_id){
        return sql.selectList("board.commentList", board_id);
    }

}
