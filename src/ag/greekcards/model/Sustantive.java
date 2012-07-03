package ag.greekcards.model;

import ag.greekcards.model.base.IdElement;

public class Sustantive extends IdElement {
	private String spanishText;
	private String greekText;
	
	public String getSpanishText() {
		return spanishText;
	}
	public void setSpanishText(String spanishText) {
		this.spanishText = spanishText;
	}
	public String getGreekText() {
		return greekText;
	}
	public void setGreekText(String greekText) {
		this.greekText = greekText;
	}
}
