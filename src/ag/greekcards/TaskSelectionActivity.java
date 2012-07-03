package ag.greekcards;

import java.util.List;

import ag.greekcards.db.GreekCardsDataSource;
import ag.greekcards.model.SustantiveCategory;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TaskSelectionActivity extends Activity {
	private GreekCardsDataSource greekCardsDataSource;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        greekCardsDataSource = new GreekCardsDataSource(this);
        
        setContentView(R.layout.activity_task_selection);
        
        setupSustantiveCategoriesSpinner();
    }

	private void setupSustantiveCategoriesSpinner() {
		Spinner sustantiveCategories = (Spinner)findViewById(R.id.sustantiveCategories);
		final List<SustantiveCategory> sc = greekCardsDataSource.findSustantiveCategories();
        final ArrayAdapter<SustantiveCategory> adapter = new ArrayAdapter<SustantiveCategory>(this, android.R.layout.simple_spinner_item, sc);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    sustantiveCategories.setAdapter(adapter);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_task_selection, menu);
        return true;
    }
    
	@Override
	protected void onPause() {
		greekCardsDataSource.close();
		super.onPause();
	}

	@Override
	protected void onStop() {
		greekCardsDataSource.close();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		greekCardsDataSource.close();
		super.onDestroy();
	}
}
