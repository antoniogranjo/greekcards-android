package ag.greekcards.db;

import java.util.List;

import ag.greekcards.model.Sustantive;
import ag.greekcards.model.SustantiveCategory;
import android.content.Context;

public class GreekCardsDataSource {
	private GreekCardsDbHelper dbHelper;
	
	public GreekCardsDataSource(Context context) {
		dbHelper = new GreekCardsDbHelper(context);
	}
	
	public List<Sustantive> findSustantives(SustantiveCategory categoryFilter) {
		return dbHelper.findSustantives(categoryFilter);
	}
	
	public List<Sustantive> findSustantives() {
		return dbHelper.findSustantives(SustantiveCategory.CATEGORY_ALL);
	}

	public void close() {
		dbHelper.close();
	}

	public List<SustantiveCategory> findSustantiveCategories() {
		final List<SustantiveCategory> categories = dbHelper.findSustantiveCategories();
		categories.add(0, SustantiveCategory.CATEGORY_ALL);
		return categories;
	}
}
