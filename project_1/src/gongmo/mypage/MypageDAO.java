package gongmo.mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import web.project.model.CsignupDTO;
import web.project.model.NsignupDTO;

public class MypageDAO {
	
	private Connection getConnection() throws Exception {
		Context ctx = new InitialContext();
		Context env = (Context)ctx.lookup("java:comp/env");
		DataSource ds = (DataSource)env.lookup("jdbc/orcl");
		return ds.getConnection();
	}
	
	// 일반 비밀번호 변경
	   public int updateNuserPw(NsignupDTO dto, String pw, String newPw) {
		   int result = -1;
		   Connection conn = null;
		   PreparedStatement pstmt = null;
		   ResultSet rs = null;
		   
		   try {
			   conn = getConnection();
			   
			   String sql = "select usern_pw from usern where usern_id=?";
			   pstmt = conn.prepareStatement(sql);
			   pstmt.setString(1, dto.getUsern_id());
			   
			   rs = pstmt.executeQuery();
			   
			   if(rs.next()) {
				   String dbpw = rs.getString("usern_pw");
				   if(dbpw.equals(pw)) {
					   sql = "update usern set usern_pw=? where usern_id=?";
					   pstmt = conn.prepareStatement(sql);
					   pstmt.setString(1, newPw);
					   pstmt.setString(2, dto.getUsern_id());
					   result = pstmt.executeUpdate();
					   
					   System.out.println(result);
				   }else {
					   result = 0;
				   }
			   }
			   
		   }catch(Exception e) {
			   e.printStackTrace();
		   }finally {
			   if(rs != null) try {rs.close();} catch(Exception e) {e.printStackTrace();}
			   if(pstmt != null) try {pstmt.close();} catch(Exception e) {e.printStackTrace();}
			   if(conn != null) try {conn.close();} catch(Exception e) {e.printStackTrace();}			   
		   }
		   
		   return result;
	   }
	   // 기업 비밀번호 변경
	   public int updateCuserPw(web.project.model.CsignupDTO dto, String pw, String newPw) {
		   int result = -1;
		   Connection conn = null;
		   PreparedStatement pstmt = null;
		   ResultSet rs = null;
		   
		   try {
			   conn = getConnection();
			   
			   String sql = "select userc_pw from userc where userc_id=?";
			   pstmt = conn.prepareStatement(sql);
			   pstmt.setString(1, dto.getUserc_id());
			   
			   rs = pstmt.executeQuery();
			   
			   if(rs.next()) {
				   String dbpw = rs.getString("userc_pw");
				   if(dbpw.equals(pw)) {
					   sql = "update userc set userc_pw=? where userc_id=?";
					   pstmt = conn.prepareStatement(sql);
					   pstmt.setString(1, newPw);
					   pstmt.setString(2, dto.getUserc_id());
					   result = pstmt.executeUpdate();
					   
					   System.out.println(result);
				   }else {
					   result = 0;
				   }
			   }
			   
		   }catch(Exception e) {
			   e.printStackTrace();
		   }finally {
			   if(rs != null) try {rs.close();} catch(Exception e) {e.printStackTrace();}
			   if(pstmt != null) try {pstmt.close();} catch(Exception e) {e.printStackTrace();}
			   if(conn != null) try {conn.close();} catch(Exception e) {e.printStackTrace();}			   
		   }
		   
		   return result;
	   }
	
	// 받은 메세지 있, 없 확인
	public int RecMsgGetCount(String id) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select count(*) from msg where msg_rid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
				System.out.println("받은 메세지 개수 " + count);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
			if(pstmt!=null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
			if(conn!=null)try {conn.close();}catch(Exception e) {e.printStackTrace();}
		}

		return count;
	}
	
	// 보낸 메세지 있, 없
	public int SendMsgGetCount(String id) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select count(*) from msg where msg_sid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
				System.out.println("보낸 메세지 개수 " + count);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
			if(pstmt!=null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
			if(conn!=null)try {conn.close();}catch(Exception e) {e.printStackTrace();}
		}

		return count;
	}
	
	// 받은 메세지 가져오기
	public List RecMsgGetArticles(int start, int end, String id) {
		List articlelist = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select B.*, r from (select rownum r, A.* from "
					+ "(select * from msg where msg_rid = ? order by msg_reg desc) A order by msg_reg desc) B "
					+ "where r >= ? and r <= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				articlelist = new ArrayList();
				do {
					MsgDTO article = new MsgDTO();
					article.setMsg_sid(rs.getString("msg_sid"));
					article.setMsg_content(rs.getString("msg_content"));
					article.setMsg_reg(rs.getTimestamp("msg_reg"));
					articlelist.add(article);
				}while(rs.next());
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
			if(pstmt!=null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
			if(conn!=null)try {conn.close();}catch(Exception e) {e.printStackTrace();}

		}
		
		return articlelist;
	}
	
	// 보낸 메세지 가져오기
	public List SendMsgGetArticles(int start, int end, String id) {
		List articlelist = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select * from (select rownum r, msg_sid, msg_rid, msg_content, msg_reg from "
					+ "(select * from msg where msg_sid=? order by msg_reg desc)) "
					+ "where r >= ? and r <= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				articlelist = new ArrayList();
				do {
					MsgDTO article = new MsgDTO();
					article.setMsg_rid(rs.getString("msg_rid"));
					article.setMsg_content(rs.getString("msg_content"));
					article.setMsg_reg(rs.getTimestamp("msg_reg"));
					articlelist.add(article);
				}while(rs.next());
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
			if(pstmt!=null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
			if(conn!=null)try {conn.close();}catch(Exception e) {e.printStackTrace();}

		}
		
		return articlelist;
	}
	// 내 파티 글 있, 없 확인
		public int makeGetArticleCount(String id) {
			int count = 0;
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				conn = getConnection();
				String sql = "select count(*) from maketeam where maketeam_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				
				rs = pstmt.executeQuery();
				if(rs.next()) {
					// count(*)는 결과를 숫자로 가져오기 때문에 번호로 담으세요
					count = rs.getInt(1); // count(*) 갯수
					System.out.println(count);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(rs!=null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
				if(pstmt!=null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
				if(conn!=null)try {conn.close();}catch(Exception e) {e.printStackTrace();}
			}
			
			return count;
		}
		
		// 내파티 게시글 가져오기 -> 파티명, 제목만 가져오면 되잖아?
		// 제목 눌렀을 때 공모전으로 넘어가야 함
		public List makeGetArticles(int start, int end, String id) {
			List articlelist = null;
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				conn = getConnection();
				String sql = "select * from (select rownum r, maketeam_name, maketeam_title, maketeam_targetgm from "+ 
						"(select * from maketeam where maketeam_id=? order by maketeam_reg desc)) "+ 
						"where r > = ? and r <= ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					articlelist = new ArrayList();
					do {
						MakeTeamDTO article = new MakeTeamDTO();
						article.setMaketeam_targetGM(rs.getInt("maketeam_targetgm"));
						article.setMaketeam_title(rs.getString("maketeam_title"));
						article.setMaketeam_name(rs.getString("maketeam_name"));
						articlelist.add(article);
					}while(rs.next());
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(rs!=null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
				if(pstmt!=null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
				if(conn!=null)try {conn.close();}catch(Exception e) {e.printStackTrace();}
			}
			
			return articlelist;
		}
	
	// 내가 참가한 글 있, 없 확인 -> id확인 result확인
		public int applyGetArticleCount(String id) {
			int count = 0;
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				conn = getConnection();
				String sql = "select count(*) from applyteam where applyteam_id=? and applyteam_result=1";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				
				rs = pstmt.executeQuery();
				if(rs.next()) {
					count = rs.getInt(1);
					System.out.println("참가count" + count);
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(rs!=null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
				if(pstmt!=null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
				if(conn!=null)try {conn.close();}catch(Exception e) {e.printStackTrace();}
			}
			
			return count;
		}
		
		// 내가 참가한 파티 가져오기 -> 파티명, 제목은 applyteam_postNum으로 maketeam에서
		public List applyGetArticles(int start, int end, String id) {
			List articlelist = null;
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				conn = getConnection();
				String sql = "select * from (select rownum r, maketeam_name, maketeam_title, maketeam_targetgm from "
						+ "(select * from maketeam, applyteam where maketeam.maketeam_num = applyteam.applyteam_postnum "
						+ "and applyteam_id=? and applyteam_result=1 "
					    + "order by maketeam_reg desc)) where r>= ? and r <= ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					articlelist = new ArrayList();
					do {
						MakeTeamDTO article = new MakeTeamDTO();
						article.setMaketeam_targetGM(rs.getInt("maketeam_targetgm"));
						article.setMaketeam_title(rs.getString("maketeam_title"));
						article.setMaketeam_name(rs.getString("maketeam_name"));
						articlelist.add(article);
					}while(rs.next());
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(rs!=null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
				if(pstmt!=null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
				if(conn!=null)try {conn.close();}catch(Exception e) {e.printStackTrace();}			
			}
			
			return articlelist;
		}
	
		// 내 참가내역 있는지 없는지 -> 있으면 무조건 1, 없으면 0
	      public int historyCount(String id) {
	         int count = 0;
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
	              if(rs.getString(1).equals("0")) {
	            	  count = 0;
	              }else {
	            	  count =1;
	              }
	            }
	            
	         }catch(Exception e) {
	            e.printStackTrace();
	         }finally {
	            if(rs!=null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
	            if(pstmt!=null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
	            if(conn!=null)try {conn.close();}catch(Exception e) {e.printStackTrace();}
	         }

	         return count;
	      }
	      
	      // 내 참가내역 리스트로 만들어줘 -> 제목 뽑을거야
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
	      
	      // 제목 가져와잇!
	      public String histitle(int start, int end, String his) {
	         Connection conn = null;
	         PreparedStatement pstmt = null;
	         ResultSet rs = null;
	         String title = "";
	         
	         try {
	            conn = getConnection();
	            String sql = "select * from (select rownum r, gmboard_title from " + 
	                  "(select * from gmboard where gmboard_num=? order by gmboard_reg desc)) " + 
	                  "where r >= ? and r <= ?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, his);
	            pstmt.setInt(2, start);
	            pstmt.setInt(3, end);
	            
	            rs = pstmt.executeQuery();
	            if(rs.next()) {
	               title = rs.getString("gmboard_title");
	               System.out.println("제목" + title);
	            }
	            
	         }catch(Exception e) {
	            e.printStackTrace();
	         }finally {
	            if(rs!=null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
	            if(pstmt!=null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
	            if(conn!=null)try {conn.close();}catch(Exception e) {e.printStackTrace();}
	         }
	         
	         return title;
	      }
	      
	      // 수상 여부
	      public int historyresult(String his, String id) {
	         Connection conn = null;
	         PreparedStatement pstmt = null;
	         ResultSet rs = null;
	         int result = 0;
	         
	         try {
	            conn = getConnection();
	            String sql = "select * from review where review_targetgm=? and review_id=?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, his);
	            pstmt.setString(2, id);
	            
	            rs = pstmt.executeQuery();
	            if(rs.next()) {
	               result = 1;
	               System.out.println("수상여부 result " + result);
	            }
	            
	         }catch(Exception e) {
	            e.printStackTrace();
	         }finally {
	            if(rs!=null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
	            if(pstmt!=null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
	            if(conn!=null)try {conn.close();}catch(Exception e) {e.printStackTrace();}
	         }
	         
	         return result;
	      }
	      
	      // maketeam에 아이디 있으면 팀
	      public int maketeamresult(String his, String id) {
	         Connection conn = null;
	         PreparedStatement pstmt = null;
	         ResultSet rs = null;
	         int result = 0;
	         
	         try {
	            conn = getConnection();
	            String sql = "select * from maketeam where maketeam_targetgm=? and maketeam_id=?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, his);
	            pstmt.setString(2, id);
	            
	            rs = pstmt.executeQuery();
	            if(rs.next()) {
	               result = 1;
	               System.out.println("메이크 팀 result " + result);
	            }
	            
	         }catch(Exception e) {
	            e.printStackTrace();
	         }finally {
	            if(rs!=null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
	            if(pstmt!=null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
	            if(conn!=null)try {conn.close();}catch(Exception e) {e.printStackTrace();}
	         }
	         
	         return result;
	      }
	      
	      // applyteam에 아이디도 있고 참가도 된거면? -> 언제 강퇴 당할 줄 알고;;
	      public int applyteamresult(String his, String id) {
	         Connection conn = null;
	         PreparedStatement pstmt = null;
	         ResultSet rs = null;
	         int result = 0;
	         
	         try {
	            conn = getConnection();
	            String sql = "select * from applyteam where applyteam_result=1 and applyteam_postnum=? and applyteam_id=?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, his);
	            pstmt.setString(2, id);
	            
	            rs = pstmt.executeQuery();
	            if(rs.next()) {
	               result = 1;
	               System.out.println("어플라이팀 result " + result);
	            }
	            
	         }catch(Exception e) {
	            e.printStackTrace();
	         }finally {
	            if(rs!=null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
	            if(pstmt!=null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
	            if(conn!=null)try {conn.close();}catch(Exception e) {e.printStackTrace();}
	         }
	         
	         return result;
	      }
	
	
}
