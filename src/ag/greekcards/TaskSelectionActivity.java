package ag.greekcards;

import java.util.ArrayList;
import java.util.List;

import ag.greekcards.db.GreekCardsDataSource;
import ag.greekcards.model.Sustantive;
import ag.greekcards.model.SustantiveCategory;
import ag.greekcards.model.enums.SustantiveTranslationMode;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class TaskSelectionActivity extends Activity {
	private GreekCardsDataSource greekCardsDataSource;
	private Button sustantivesTranslationButton;
	
	private final OnClickListener goToSustantiveTranslation = new OnClickListener() {
		@Override
		public void onClick(View view) {
			final Intent sustantiveTranslation = new Intent(getBaseContext(), SustantiveTranslationActivity.class);
			final List<Sustantive> sustantives = greekCardsDataSource.findSustantives();
			sustantiveTranslation.putExtra(SustantiveTranslationActivity.IntentCodes.SUSTANTIVES, new ArrayList<Sustantive>(sustantives));
			sustantiveTranslation.putExtra(SustantiveTranslationActivity.IntentCodes.TRANSLATION_MODE, SustantiveTranslationMode.GREEK_TO_SPANISH.name());
			startActivity(sustantiveTranslation);
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        greekCardsDataSource = new GreekCardsDataSource(this);
        
        setContentView(R.layout.activity_task_selection);
        
        setupSustantiveCategoriesSpinner();
        setupSustantivesTranslationButton();
    }

	private void setupSustantivesTranslationButton() {
		sustantivesTranslationButton = (Button)findViewById(R.id.sustantivesTranslationButton);
		sustantivesTranslationButton.setOnClickListener(goToSustantiveTranslation);
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
