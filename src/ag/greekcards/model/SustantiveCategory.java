package ag.greekcards.model;

import ag.greekcards.model.base.IdDescElement;

public class SustantiveCategory extends IdDescElement {
	@Override
	public String toString() {
		return getDescription();
	}
}
