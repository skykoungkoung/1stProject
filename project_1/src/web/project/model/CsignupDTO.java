package web.project.model;

import java.sql.Timestamp;

public class CsignupDTO {
	private String userc_id;
	private String userc_pw;
	private String userc_name;
	private String userc_type;
	private String userc_num;
	private String userc_url;
	private String userc_email;
	private Timestamp userc_reg;
	
	public String getUserc_id() {
		return userc_id;
	}
	public void setUserc_id(String userc_id) {
		this.userc_id = userc_id;
	}
	public String getUserc_pw() {
		return userc_pw;
	}
	public void setUserc_pw(String userc_pw) {
		this.userc_pw = userc_pw;
	}
	public String getUserc_name() {
		return userc_name;
	}
	public void setUserc_name(String userc_name) {
		this.userc_name = userc_name;
	}
	public String getUserc_type() {
		return userc_type;
	}
	public void setUserc_type(String userc_type) {
		this.userc_type = userc_type;
	}
	public String getUserc_num() {
		return userc_num;
	}
	public void setUserc_num(String userc_num) {
		this.userc_num = userc_num;
	}
	public String getUserc_url() {
		return userc_url;
	}
	public void setUserc_url(String userc_url) {
		this.userc_url = userc_url;
	}
	public String getUserc_email() {
		return userc_email;
	}
	public void setUserc_email(String userc_email) {
		this.userc_email = userc_email;
	}
	public Timestamp getUserc_reg() {
		return userc_reg;
	}
	public void setUserc_reg(Timestamp userc_reg) {
		this.userc_reg = userc_reg;
	}
	
}
