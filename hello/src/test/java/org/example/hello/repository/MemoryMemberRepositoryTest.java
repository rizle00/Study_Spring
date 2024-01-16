package org.example.hello.repository;

import org.example.hello.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

     MemoryMemberRepository repository = new MemoryMemberRepository();
     @AfterEach // 테스트 하나가 끝날 때 마다 처리
     public void afterEach(){
        repository.clearStore();
     }
    @Test
     public void save(){
        Member member = new Member();
        member.setName("spring");
        repository.save(member);
       Member result = repository.findById(member.getId()).get();
//       System.out.println("result="+(result == member));
//        Assertions.assertEquals(result, member);
        assertThat(member).isEqualTo(result);//static import


     }

     @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

         Member member2 = new Member();
         member2.setName("spring2");
         repository.save(member2);

         Member result = repository.findByName("spring2").get();

         assertThat(result).isEqualTo(member2);
     }

     @Test
    public void findAll(){
         Member member1 = new Member();
         member1.setName("spring1");
         repository.save(member1);

         Member member2 = new Member();
         member2.setName("spring2");
         repository.save(member2);

         Member member3 = new Member();
         member3.setName("spring3");
         repository.save(member3);

         List<Member> result = repository.findAll();
         assertThat(result.size()).isEqualTo(3);

     }
 }
