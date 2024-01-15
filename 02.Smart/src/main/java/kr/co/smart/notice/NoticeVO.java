package kr.co.smart.notice;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class NoticeVO {
    private int id, readcnt, no;
    private String title, content, writer, filepath, filename, name;
    private Date writedate;
}