package kr.co.smart.board;

import kr.co.smart.common.FileVO;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter @Setter
public class BoardVO {
    private int id, readcnt, no;
    private String title, content, writer, name;
    private Date writedate;
//    파일 여러 건
    private List<FileVO> fileList;
}