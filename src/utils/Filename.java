package utils;

public class Filename {
	
	private String fileName = null;
	private String fiiePath = null;
	
	public Filename(String fileName, String fiiePath) {
		super();
		this.fileName = fileName;
		this.fiiePath = fiiePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFiiePath() {
		return fiiePath;
	}
	public void setFiiePath(String fiiePath) {
		this.fiiePath = fiiePath;
	}
	

}
