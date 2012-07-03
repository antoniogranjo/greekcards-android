package ag.greekcards.model;

import ag.greekcards.model.base.IdDescElement;

public class SustantiveCategory extends IdDescElement {
	public static final SustantiveCategory CATEGORY_ALL;
	public static final int CATEGORY_ALL_ID = -1;
	
	static {
		CATEGORY_ALL = new SustantiveCategory();
		CATEGORY_ALL.setDescription("[Todas las categorías]");
		CATEGORY_ALL.setId(CATEGORY_ALL_ID);
	}

	@Override
	public String toString() {
		return getDescription();
	}
}
