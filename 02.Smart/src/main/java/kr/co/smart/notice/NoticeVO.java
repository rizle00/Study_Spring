package kr.co.smart.notice;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class NoticeVO {
    private int id, readcnt, no, root, step, indent, rid;
    private String title, content, writer, filepath, filename, name;
    private Date writedate;
}