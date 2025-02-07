package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 repositoryV1 = new MemberRepositoryV1();

    @Test
    void crud(){
        // save
        Member member = new Member("memberV1", 10000);
        repositoryV1.save(member);

        // findById
        Member findMember = repositoryV1.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        log.info("member == findMember {}", member == findMember); // 당연히 서로 다른 객체이다
        log.info("member equals findMember {}", member.equals(findMember)); // 객체는 equals 비교!
        Assertions.assertEquals(member,findMember);

        //update : money: 10000 -> 200000
        repositoryV1.update(member.getMemberId(), 20000);
        Member updatedMember = repositoryV1.findById(member.getMemberId());

        // delete
        repositoryV1.delete(member.getMemberId());
        assertThatThrownBy(() -> repositoryV1.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);

    }



}