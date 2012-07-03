package ag.greekcards.model.base;

public abstract class IdDescElement extends IdElement {
	protected String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
