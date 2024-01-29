package kr.co.smart.common;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class FileVO {
    private int id;
    private String filepath, filename;

}