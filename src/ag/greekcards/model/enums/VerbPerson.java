package ag.greekcards.model.enums;

import ag.greekcards.model.base.IntEncoded;

public enum VerbPerson implements IntEncoded {
	S1("εγώ", 11), S2("", 12), S3("", 13), P1("", 21), P2("", 22), P3("", 23);
	
	private String greekText;
	private int code;
	
	public String getGreekText() {
		return this.greekText;
	}
	
	public int getCode() {
		return this.code;
	}
	
	private VerbPerson(String greekText, int code) {
		this.greekText = greekText;
	}
	
	public int getNumber() {
		return this.code % 10;
	}
	
	public boolean isSingular() {
		return this.code < 20;
	}
	
	public boolean isPlural() {
		return !isSingular();
	}
}
