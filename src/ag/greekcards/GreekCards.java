package ag.greekcards;

import ag.greekcards.db.GreekCardsDataSource;
import android.app.Application;

public class GreekCards extends Application {
	private GreekCardsDataSource greekCardsDataSource;

	@Override
	public void onCreate() {
		this.greekCardsDataSource = new GreekCardsDataSource(getApplicationContext());
	}
	
	public GreekCardsDataSource getGreekCardsDataSource() {
		return this.greekCardsDataSource;
	}
	
	
	public void closeDatabaseHelper(){
		greekCardsDataSource.close();
	}
}
