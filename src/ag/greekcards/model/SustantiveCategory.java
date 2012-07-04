package ag.greekcards.model;

import ag.greekcards.model.base.IdDescElement;

public class SustantiveCategory extends IdDescElement {
	public static final SustantiveCategory CATEGORY_ALL;
	public static final Integer CATEGORY_ALL_ID = -1;
	
	static {
		CATEGORY_ALL = new SustantiveCategory();
		CATEGORY_ALL.setDescription("[Todas las categorías]");
		CATEGORY_ALL.setId(CATEGORY_ALL_ID);
	}

	public boolean isCategoryAll() {
		return CATEGORY_ALL_ID.equals(getId());
	}
	
	@Override
	public String toString() {
		return getDescription();
	}
}
