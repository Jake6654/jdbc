package hello.jdbc.domain;


import lombok.Data;

@Data // toString()을 적절히 오버라이딩 하여 객체의 참조값이 아닌 실제 데이터를 보여준다
// 따라서 객체의 모든 필드를 사용하도록 equals를 오버라이딩 하기 때문이다
public class Member {


    private String memberId;
    private int money;

    public Member(){

    }

    public Member(String memberId, int money){
        this.memberId = memberId;
        this.money = money;
    }
}
