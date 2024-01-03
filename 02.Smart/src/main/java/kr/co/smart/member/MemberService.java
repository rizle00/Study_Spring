package kr.co.smart.member;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired @Qualifier("hanul") private SqlSession sql;
//     CRUD
//    회원가입시 회원정보 신규 삽입 저장
    public int member_join(MemberVO vo){
        return sql.insert("member.join", vo);
    }

//    회원 목록 조회(관리자)
    public List<MemberVO> member_list(){
        return null;
    }

//    회원 정보 조회
    public MemberVO member_info(String user_id){
        return sql.selectOne("member.info", user_id);
    }

//    회원 정보 변경 저장(마이페이지)
    public int member_update(MemberVO vo){
        return sql.update("member.update", vo);
    }

    public int member_delete(String user_id){
        return 0;
    }

//    아이디와 이메일이 일치하는 회원정보 조회
    public MemberVO member_userid_email(MemberVO vo){
        return sql.selectOne("member.useridEmail", vo);
    }

//    비밀번호를 변경 저장하는 처리
    public int member_resetPassword(MemberVO vo){
        return sql.update("member.resetPassword", vo);
    }
}
