package gongmo.community.model;

import java.sql.Timestamp;

public class MakeTeamDTO {
	private int num;
	private int targetGM;
	private String id;
	private String title;
	private String name;
	private int ptotal;
	private String job;
	private int view;
	private String content;
	private String pnow;
	private String done;
	private Timestamp reg;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getTargetGM() {
		return targetGM;
	}
	public void setTargetGM(int targetGM) {
		this.targetGM = targetGM;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPtotal() {
		return ptotal;
	}
	public void setPtotal(int ptotal) {
		this.ptotal = ptotal;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public int getView() {
		return view;
	}
	public void setView(int view) {
		this.view = view;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPnow() {
		return pnow;
	}
	public void setPnow(String pnow) {
		this.pnow = pnow;
	}
	public String getDone() {
		return done;
	}
	public void setDone(String done) {
		this.done = done;
	}
	public Timestamp getReg() {
		return reg;
	}
	public void setReg(Timestamp reg) {
		this.reg = reg;
	}
	
	
}
