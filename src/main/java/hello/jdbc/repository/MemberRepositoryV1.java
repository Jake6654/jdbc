package hello.jdbc.repository;


import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

/**
 *  JDBC - DriverManager 사용
 */

@Slf4j
public class MemberRepositoryV1 {

    public Member save(Member member){
        String sql = "insert into member(member_id, money) values (?,?)";

        Connection con = null;
        PreparedStatement pstmt = null;

    try {
        // 이때 sql 에러를 잡아줘야한다
        con = getConnection();
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, member.getMemberId()); // values 의 첫번째 값에 멤버 아이디가 들어감
        pstmt.setInt(2,member.getMoney());
        pstmt.executeUpdate();
        return member;
    } catch (SQLException e) {
        log.error("db error", e);
        throw new IllegalArgumentException(e);
    } finally {
        close(con, pstmt, null);
    }

    }

    public Member findById(String memberId) {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

           rs = pstmt.executeQuery();
            if (rs.next()){ // next 를 호출하면 데이터가 있는지 없는지 체크
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney((rs.getInt("money")));
                return member;
            }else {
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }
        }catch (SQLException e){
            log.error("db error", e);
            throw new IllegalArgumentException(e);
        }finally {
            close(con, pstmt, rs);
            // 시작할대는 con -? pstmt -> rs
            // 종료할때는 rs -> pstmt -> con

        }
    }

    public void update(String memberId, int money){
        String sql = "update member set money=? where member_id =?";


        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            // 이때 sql 에러를 잡아줘야한다
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize= {}", resultSize);
        } catch (SQLException e) {
            log.error("db error", e);
            throw new IllegalArgumentException(e);
        } finally {
            close(con, pstmt, null);
        }

    }

    public void delete(String memberId) {
        String sql = "delete from member where member_id =?";


        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            // 이때 sql 에러를 잡아줘야한다
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize= {}", resultSize);
        } catch (SQLException e) {
            log.error("db error", e);
            throw new IllegalArgumentException(e);
        } finally {
            close(con, pstmt, null);
        }

    }





    private void close(Connection con, Statement stmt, ResultSet rs) {
        // JDBC 는 직접 사용하는 코드를 모두 닫아줘야한다
        if (rs != null) {
            try {
                stmt.close(); // Exception
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
        if (stmt != null) {
            try {
                stmt.close(); // Exception
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if (con != null) {
            try {stmt.close(); // Exception
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
    }

    private static Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
