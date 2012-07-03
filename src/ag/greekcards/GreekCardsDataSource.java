package ag.greekcards;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ag.greekcards.db.GreekCardsDbHelper;
import ag.greekcards.model.Sustantive;
import ag.greekcards.model.SustantiveCategory;
import android.content.Context;

public class GreekCardsDataSource {
	private GreekCardsDbHelper dbHelper;
	
	public GreekCardsDataSource(Context context) {
		dbHelper = new GreekCardsDbHelper(context);
	}
	
	public List<Sustantive> findSustantives(Collection<SustantiveCategory> categoryFilter) {
		return dbHelper.findSustantives(categoryFilter);
	}
	
	public List<Sustantive> findSustantives() {
		return dbHelper.findSustantives(Collections.EMPTY_LIST);
	}

	public void close() {
		dbHelper.close();
	}

	public List<SustantiveCategory> findSustantiveCategories() {
		return dbHelper.findSustantiveCategories();
	}
}
