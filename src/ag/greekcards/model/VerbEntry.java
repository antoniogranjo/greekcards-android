package ag.greekcards.model;

import ag.greekcards.model.base.IdElement;
import ag.greekcards.model.enums.VerbForm;
import ag.greekcards.model.enums.VerbPerson;

public class VerbEntry extends IdElement {
	private int verbId;
	private String greekText;
	private VerbForm form;
	private VerbPerson person;
	
	public int getVerbId() {
		return verbId;
	}
	public void setVerbId(int verbId) {
		this.verbId = verbId;
	}
	public String getGreekText() {
		return greekText;
	}
	public void setGreekText(String greekText) {
		this.greekText = greekText;
	}
	public VerbForm getForm() {
		return form;
	}
	public void setForm(VerbForm form) {
		this.form = form;
	}
	public VerbPerson getPerson() {
		return person;
	}
	public void setPerson(VerbPerson person) {
		this.person = person;
	}
}
