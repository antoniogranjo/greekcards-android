package ag.greekcards.activities;

import java.util.ArrayList;
import java.util.List;

import ag.greekcards.R;
import ag.greekcards.activities.verbs.VerbsOptionsActivity;
import ag.greekcards.activities.vocabulary.VocabularyTranslationActivity;
import ag.greekcards.activities.vocabulary.VocabularyTranslationOptionsActivity;
import ag.greekcards.db.GreekCardsDataSource;
import ag.greekcards.model.VocabularyCategory;
import ag.greekcards.model.VocabularyEntry;
import ag.greekcards.model.enums.TranslationMode;
import ag.greekcards.utils.NavigationUtils;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class TaskSelectionActivity extends Activity {
	private static final int ABOUT_DIALOG_ID = 1;
	
	private GreekCardsDataSource greekCardsDataSource;
	private Button vocabularyTranslationButton;
	private Button verbsButton;
	private Button adminButton;
	private Button aboutButton;
	private Dialog aboutDialog;
	
	private final OnClickListener goToVocabularyTranslation = new OnClickListener() {
		@Override
		public void onClick(View view) {
			final Intent vocabularyTranslation = new Intent(getBaseContext(), VocabularyTranslationActivity.class);
			final List<VocabularyEntry> sustantives = greekCardsDataSource.findVocabularyEntries();
			vocabularyTranslation.putExtra(VocabularyTranslationActivity.BundleData.VOCABULARY_ENTRIES, new ArrayList<VocabularyEntry>(sustantives));
			vocabularyTranslation.putExtra(VocabularyTranslationActivity.BundleData.TRANSLATION_MODE, TranslationMode.GREEK_TO_SPANISH.name());
			startActivity(vocabularyTranslation);
		}
	};
	
	private final OnClickListener goToVocabularyTranslationOptions = new OnClickListener() {
		@Override
		public void onClick(View view) {
			NavigationUtils.startSimpleNavigaton(TaskSelectionActivity.this, VocabularyTranslationOptionsActivity.class);
		}
	};
	
	private final OnClickListener goToVerbsOptions = new OnClickListener() {
		@Override
		public void onClick(View view) {
			NavigationUtils.startSimpleNavigaton(TaskSelectionActivity.this, VerbsOptionsActivity.class);
		}
	};
	
	private final OnClickListener goToAdmin = new OnClickListener() {
		@Override
		public void onClick(View view) {
			NavigationUtils.startSimpleNavigaton(TaskSelectionActivity.this, AdminTaskSelectionActivity.class);
		}
	};
	
	private final OnClickListener showAbout = new OnClickListener() {
		@Override
		public void onClick(View view) {
			showDialog(ABOUT_DIALOG_ID);
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        greekCardsDataSource = new GreekCardsDataSource(this);
        
        setContentView(R.layout.activity_task_selection);
        bindViewComponents();
        setupEventHandlers();
        
        setupSustantiveCategoriesSpinner();
        setupSustantivesTranslationButton();
    }

	private void bindViewComponents() {
		vocabularyTranslationButton = (Button)findViewById(R.id.vocabularyTranslationButton);
		verbsButton = (Button)findViewById(R.id.verbsButton);
		adminButton = (Button)findViewById(R.id.adminButton);
		aboutButton = (Button)findViewById(R.id.aboutButton);
	}
	
	private void setupEventHandlers() {
		adminButton.setOnClickListener(goToAdmin);
		verbsButton.setOnClickListener(goToVerbsOptions);
		vocabularyTranslationButton.setOnClickListener(goToVocabularyTranslationOptions);
		aboutButton.setOnClickListener(showAbout);
	}

	private void setupSustantivesTranslationButton() {
		
		vocabularyTranslationButton.setOnClickListener(goToVocabularyTranslation);
	}

	private void setupSustantiveCategoriesSpinner() {
		Spinner vocabularyCategories = (Spinner)findViewById(R.id.vocabularyCategories);
		final List<VocabularyCategory> sc = greekCardsDataSource.findVocabularyCategoriesWithCategoryAll();
        final ArrayAdapter<VocabularyCategory> adapter = new ArrayAdapter<VocabularyCategory>(this, android.R.layout.simple_spinner_item, sc);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    vocabularyCategories.setAdapter(adapter);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_task_selection, menu);
        return true;
    }
    
    @Override
	protected Dialog onCreateDialog(int id) {
    	Dialog dialog;
        switch(id) {
        case ABOUT_DIALOG_ID:
        	if (aboutDialog == null) {
				initializeAboutDialog();
			}
        	
        	return this.aboutDialog;
        default:
            dialog = null;
        }
        return dialog;
	}

	private void initializeAboutDialog() {
		aboutDialog = new Dialog(getApplicationContext());
		aboutDialog.setContentView(R.layout.about_dialog);
		aboutDialog.setTitle(getText(R.string.app_name));
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
