package ag.greekcards.model;

import java.util.Collection;

import ag.greekcards.model.base.IdElement;

public class Sustantive extends IdElement {
	private String spanishText;
	private String greekText;
	private Collection<SustantiveCategory> categories;
	
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
	public Collection<SustantiveCategory> getCategories() {
		return categories;
	}
	public void setCategories(Collection<SustantiveCategory> categories) {
		this.categories = categories;
	}
}
