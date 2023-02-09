package web.project.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import gongmo.GongmoWriteDTO;


public class MypageDAO {

	//싱글턴
	private static MypageDAO instance = new MypageDAO();
	private MypageDAO() {}
	public static MypageDAO getInstance() {return instance;}
		
	//커넥션메서드(private 접근제어자)
	private Connection getConnection() throws Exception{
		Context ctx = new InitialContext();
		Context env = (Context)ctx.lookup("java:comp/env");
		DataSource ds = (DataSource)env.lookup("jdbc/orcl");
		return ds.getConnection();
	}
		
	// 일반회원 탈퇴
	public int deleteUserN(String id, String pw) {
		int result = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = getConnection();
			// id, pw 확인 처리
			String sql = "select * from usern where usern_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
					
			rs = pstmt.executeQuery();
			if(rs.next()) {
				
				String dbpw = rs.getString("usern_pw"); // db에서 해당 id의 pw 꺼내서 변수에 저장
				if(dbpw.equals(pw)) { // db상의 pw와 사용자가  입력한 pw가 동일하면
					// 회원 탈퇴 로직 처리
					sql = "delete from usern where usern_id=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, id);
					result = pstmt.executeUpdate(); // 삭제 잘되면 1, 안되면 0
				}else { // 비번 안 맞으면 0
					result = 0;
				}
			}
			
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(rs != null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
				if(pstmt != null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
				if(conn != null)try {conn.close();}catch(Exception e) {e.printStackTrace();}
			}
			System.out.println("dao delete resultN : " + result);
			return result;
		}
	
	// 일반회원 탈퇴
		public int deleteUserC(String id, String pw) {
			int result = -1;
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
					
			try {
				conn = getConnection();
				// id, pw 확인 처리
				String sql = "select * from userc where userc_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
						
				rs = pstmt.executeQuery();
				if(rs.next()) {
					
					String dbpw = rs.getString("userc_pw"); // db에서 해당 id의 pw 꺼내서 변수에 저장
					if(dbpw.equals(pw)) { // db상의 pw와 사용자가  입력한 pw가 동일하면
						// 회원 탈퇴 로직 처리
						sql = "delete from userc where userc_id=?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, id);
						result = pstmt.executeUpdate(); // 삭제 잘되면 1, 안되면 0
					}else { // 비번 안 맞으면 0
						result = 0;
					}
				}
				
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					if(rs != null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
					if(pstmt != null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
					if(conn != null)try {conn.close();}catch(Exception e) {e.printStackTrace();}
				}
				System.out.println("dao delete resultC : " + result);
				return result;
			}
	
	//내정보확인-일반회원 
	public NsignupDTO infoN(String id) {
		NsignupDTO infoN = null; 
		Connection conn = null; 
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		
		try {
			conn = getConnection();
			String sql = "select * from usern where usern_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				infoN = new NsignupDTO();
				infoN.setUsern_id(rs.getString("usern_id"));
				infoN.setUsern_name(rs.getString("usern_name"));
				infoN.setUsern_email(rs.getString("usern_email"));
				infoN.setUsern_phone(rs.getString("usern_phone"));
				infoN.setUsern_attach(rs.getString("usern_attach"));
				infoN.setUsern_local(rs.getString("usern_local"));
				infoN.setUsern_favor(rs.getString("usern_favor"));
				infoN.setUsern_job(rs.getString("usern_job"));
				infoN.setUsern_popul(rs.getInt("usern_popul"));
				infoN.setUsern_history(rs.getString("usern_history"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try { rs.close(); } catch(Exception e) { e.printStackTrace();}
			if(pstmt != null) try { pstmt.close(); } catch(Exception e) { e.printStackTrace();}
			if(conn != null) try { conn.close(); } catch(Exception e) { e.printStackTrace();}	
		}
		return infoN;
	}
	
	//내정보확인-기업회원 
	public CsignupDTO infoC(String id) {
		CsignupDTO infoC = null; 
		Connection conn = null; 
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		
		try {
			conn = getConnection();
			String sql = "select * from userc where userc_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				infoC = new CsignupDTO();
				infoC.setUserc_id(rs.getString("userc_id"));
				infoC.setUserc_name(rs.getString("userc_name"));
				infoC.setUserc_type(rs.getString("userc_type"));
				infoC.setUserc_num(rs.getString("userc_num"));
				infoC.setUserc_url(rs.getString("userc_url"));
				infoC.setUserc_email(rs.getString("userc_email"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try { rs.close(); } catch(Exception e) { e.printStackTrace();}
			if(pstmt != null) try { pstmt.close(); } catch(Exception e) { e.printStackTrace();}
			if(conn != null) try { conn.close(); } catch(Exception e) { e.printStackTrace();}	
		}
		return infoC;
	}
	
	// 일반회원 수정시 필요정보 가져오기.
	public NsignupDTO getUserN(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		NsignupDTO dto = null;
			
		try {
			conn = getConnection();
			String sql = "select * from usern where usern_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
				
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new NsignupDTO();
				dto.setUsern_name(rs.getString("usern_name"));
				dto.setUsern_email(rs.getString("usern_email"));
				dto.setUsern_phone(rs.getString("usern_phone"));
				dto.setUsern_attach(rs.getString("usern_attach"));
				dto.setUsern_local(rs.getString("usern_local"));
				dto.setUsern_favor(rs.getString("usern_favor"));
				dto.setUsern_job(rs.getString("usern_job"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
			if(pstmt != null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
			if(conn != null)try {conn.close();}catch(Exception e) {e.printStackTrace();}
		}
		return dto;
	}
	
	// 일반회원 정보수정
		public int updateUserN (NsignupDTO dto) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			int result = -1;
			
			try {
				conn = getConnection();
				String sql = "update usern set usern_name=?, usern_email=?, usern_phone=?, usern_attach=?, usern_local=?, usern_favor=?, usern_job=? where usern_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, dto.getUsern_name());
				pstmt.setString(2, dto.getUsern_email());
				pstmt.setString(3, dto.getUsern_phone());
				pstmt.setString(4, dto.getUsern_attach());
				pstmt.setString(5, dto.getUsern_local());
				pstmt.setString(6, dto.getUsern_favor());
				pstmt.setString(7, dto.getUsern_job());
				pstmt.setString(8, dto.getUsern_id());
				
				result = pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(pstmt != null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
				if(conn != null)try {conn.close();}catch(Exception e) {e.printStackTrace();}
			}
			return result;
		}
		
	// 기업회원 수정시 필요정보 가져오기.
		public CsignupDTO getUserC(String id) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			CsignupDTO dto = null;
					
			try {
				conn = getConnection();
				String sql = "select * from userc where userc_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
						
				rs = pstmt.executeQuery();
				if(rs.next()) {
					dto = new CsignupDTO();
					dto.setUserc_name(rs.getString("userc_name"));
					dto.setUserc_type(rs.getString("userc_type"));
					dto.setUserc_num(rs.getString("userc_num"));
					dto.setUserc_url(rs.getString("userc_url"));
					dto.setUserc_email(rs.getString("userc_email"));
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(rs != null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
				if(pstmt != null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
				if(conn != null)try {conn.close();}catch(Exception e) {e.printStackTrace();}
			}
			return dto;
		}
		
		
	// 기업회원 정보수정
		public int updateUserC (CsignupDTO dto) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			int result = -1;
				
			try {
				conn = getConnection();
				String sql = "update userc set userc_name=?, userc_type=?, userc_num=?, userc_url=?, userc_email=? where userc_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, dto.getUserc_name());
				pstmt.setString(2, dto.getUserc_type());
				pstmt.setString(3, dto.getUserc_num());
				pstmt.setString(4, dto.getUserc_url());
				pstmt.setString(5, dto.getUserc_email());
				pstmt.setString(6, dto.getUserc_id());
					
				result = pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(pstmt != null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
				if(conn != null)try {conn.close();}catch(Exception e) {e.printStackTrace();}
			}
			return result;
		}
		
	// 내 스크랩 전체 개수 가져오기	
	public int getScrapCount(String id) {
		int count = 0;
		Connection conn = null; 
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		
		try {
			conn = getConnection(); 
			String sql = "select count(*) from scrap where scrap_id =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);		 
			rs = pstmt.executeQuery(); 
			if(rs.next()) {
				// count(*)은 결과를 숫자로 가져오며, 컬럼명대신 컬럼번호로 꺼내서 count 변수에 담기 
				count = rs.getInt(1);  
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try { rs.close(); } catch(Exception e) { e.printStackTrace();}
			if(pstmt != null) try { pstmt.close(); } catch(Exception e) { e.printStackTrace();}
			if(conn != null) try { conn.close(); } catch(Exception e) { e.printStackTrace();}
		}
		
		return count;
	}	
		
	// 게시글 범위만큼 가져오는 메서드 
	public List getScraps(int start, int end, String id) {
		Connection conn = null; 
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		ResultSet rs1 = null; 
		List scrapList = null; 
		try {
			conn = getConnection();
			String sql = "select B.*, r from (select A.*, rownum r from (select * from scrap where scrap_id = ?) A) B where r >= ? and r <= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery(); 
			if(rs.next()) {
				scrapList = new ArrayList();  // 결과가 있으면 list 객체생성해서 준비 (나중에 null로 확인하기 위하여)
				do { // if문에서 rs.next() 실행되서 커서가 내려가버렸으니 do-while로 먼저 데이터 꺼내게 하기.
					GongmoWriteDTO scrap = new GongmoWriteDTO(); 
					sql = "select * from gmboard where gmboard_num = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, rs.getInt("scrap_num"));
					rs1 = pstmt.executeQuery();
					if(rs1.next()) {
						scrap.setGmboard_num(rs1.getInt("gmboard_num"));
						scrap.setGmboard_eday(rs1.getString("gmboard_eday"));
						scrap.setGmboard_title(rs1.getString("gmboard_title"));
					}
					scrapList.add(scrap); // 리스트에 추가 scrapList[0] scrap9 scrapList[1] scrap7 
				}while(rs.next());
			}// if
		}catch(Exception e) {
			e.printStackTrace(); 
		}finally {
			if(rs1 != null) try { rs1.close(); } catch(Exception e) { e.printStackTrace();}
			if(rs != null) try { rs.close(); } catch(Exception e) { e.printStackTrace();}
			if(pstmt != null) try { pstmt.close(); } catch(Exception e) { e.printStackTrace();}
			if(conn != null) try { conn.close(); } catch(Exception e) { e.printStackTrace();}
		}
		return scrapList;
	}	
	
	// 회원정보 - 참여횟수
		public String historyList(String id) {
			String history = "";
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        
	        try {
	           conn = getConnection();
	           String sql = "select usern_history from usern where usern_id=?";
	           pstmt = conn.prepareStatement(sql);
	           pstmt.setString(1, id);
	           
	           rs = pstmt.executeQuery();
	           if(rs.next()) {
	              history = rs.getString("usern_history");
	              System.out.println(history);
	           }
	           
	        }catch(Exception e) {
	           e.printStackTrace();
	        }finally {
	           if(rs!=null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
	           if(pstmt!=null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
	           if(conn!=null)try {conn.close();}catch(Exception e) {e.printStackTrace();}
	        }

	        return history;
		}
	
		
}
