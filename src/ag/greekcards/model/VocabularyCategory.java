package ag.greekcards.model;

import ag.greekcards.model.base.IdDescElement;

public class VocabularyCategory extends IdDescElement {
	public static final VocabularyCategory CATEGORY_ALL;
	public static final VocabularyCategory NO_CATEGORY;
	public static final Integer CATEGORY_ALL_ID = -1;
	public static final Integer NO_CATEGORY_ID = -2;
	
	static {
		CATEGORY_ALL = new VocabularyCategory();
		CATEGORY_ALL.setDescription("[Todas las categorías]");
		CATEGORY_ALL.setId(CATEGORY_ALL_ID);
		
		NO_CATEGORY = new VocabularyCategory();
		NO_CATEGORY.setDescription("[Sin categoría]");
		NO_CATEGORY.setId(NO_CATEGORY_ID);
	}

	public boolean isCategoryAll() {
		return CATEGORY_ALL_ID.equals(getId());
	}
	
	@Override
	public String toString() {
		return getDescription();
	}
}
