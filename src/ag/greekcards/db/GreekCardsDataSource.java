package ag.greekcards.db;

import java.util.List;

import ag.greekcards.model.VocabularyCategory;
import ag.greekcards.model.VocabularyEntry;
import android.content.Context;

public class GreekCardsDataSource {
	private GreekCardsDbHelper dbHelper;
	
	public GreekCardsDataSource(Context context) {
		dbHelper = new GreekCardsDbHelper(context);
	}
	
	public List<VocabularyEntry> findVocabularyEntries(VocabularyCategory categoryFilter) {
		return dbHelper.findVocabularyEntries(categoryFilter);
	}
	
	public List<VocabularyEntry> findVocabularyEntries() {
		return dbHelper.findVocabularyEntries(VocabularyCategory.CATEGORY_ALL);
	}

	public void close() {
		dbHelper.close();
	}

	public List<VocabularyCategory> findVocabularyCategoriesWithCategoryAll() {
		final List<VocabularyCategory> categories = findVocabularyCategories();
		categories.add(0, VocabularyCategory.CATEGORY_ALL);
		return categories;
	}
	
	public List<VocabularyCategory> findVocabularyCategoriesWithNoCategoryElement() {
		final List<VocabularyCategory> categories = findVocabularyCategories();
		categories.add(0, VocabularyCategory.NO_CATEGORY);
		return categories;
	}
	
	public List<VocabularyCategory> findVocabularyCategories() {
		return dbHelper.findVocabularyCategories();
	}

	public VocabularyEntry newVocabularyEntry(VocabularyEntry sustantive) {
		return dbHelper.newVocabularyEntry(sustantive);
	}

	public void updateVocabularyEntry(VocabularyEntry sustantive) {
		dbHelper.updateVocabularyEntry(sustantive);
	}
}
