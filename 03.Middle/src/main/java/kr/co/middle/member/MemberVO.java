package kr.co.middle.member;

import lombok.Getter;
import lombok.Setter;

public class MemberVO {
    @Getter @Setter
    private String user_id, user_pw, name, gender, email, birth, phone, address, post, social, role, profile;
}
