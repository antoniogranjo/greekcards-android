package ag.greekcards.activities.base;

import ag.greekcards.GreekCards;
import ag.greekcards.db.GreekCardsDataSource;
import android.app.ListActivity;

public class GreekCardsListActivity extends ListActivity {
	public GreekCardsDataSource getGreekCardsDataSource() {
		return ((GreekCards)getApplication()).getGreekCardsDataSource();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			((GreekCards)getApplication()).closeDatabaseHelper();
		}
	}
}
