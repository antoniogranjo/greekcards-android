package ag.greekcards.activities.vocabulary;

import java.util.List;

import ag.greekcards.R;
import ag.greekcards.db.GreekCardsDataSource;
import ag.greekcards.model.VocabularyCategory;
import ag.greekcards.model.enums.TranslationMode;
import ag.greekcards.utils.BundleData;
import ag.greekcards.utils.NavigationUtils;
import ag.greekcards.utils.RequestCodes;
import ag.greekcards.utils.ResultCodes;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class VocabularyTranslationOptionsActivity extends Activity {
	private GreekCardsDataSource greekCardsDataSource;
	private Spinner vocabularyCategories;
	private ArrayAdapter<VocabularyCategory> vocabularyCategoriesAdapter;
	private RadioGroup translationTypeRadioGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        greekCardsDataSource = new GreekCardsDataSource(this);
        setContentView(R.layout.activity_vocabulary_translation_options);
        bindViewComponents();
        setupSustantiveCategoriesSpinner();
    }
    
    private void bindViewComponents() {
    	vocabularyCategories = (Spinner)findViewById(R.id.vocabularyCategories);
    	translationTypeRadioGroup = (RadioGroup)findViewById(R.id.translationType);
	}

	public void startTranslation(View v) {
    	final int selectedRadioId = translationTypeRadioGroup.getCheckedRadioButtonId();
    	final boolean spanishToGreek = R.id.radioSpanishToGreek == selectedRadioId;
    	final TranslationMode translationMode = spanishToGreek ? TranslationMode.SPANISH_TO_GREEK : TranslationMode.GREEK_TO_SPANISH;
    	
    	final int selectedCategoryPosition = vocabularyCategories.getSelectedItemPosition();
    	final VocabularyCategory category = vocabularyCategoriesAdapter.getItem(selectedCategoryPosition);
    	
		NavigationUtils.startVocabularyTranslation(this, category, translationMode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_vocabulary_translation_options, menu);
        return true;
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case RequestCodes.START_VOCABULARY_TRANSLATION_ACTIVITY:
			if (resultCode == ResultCodes.FINISH_PREVIOUS) {
				this.finish();
			}
			break;
		default:
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	private void setupSustantiveCategoriesSpinner() {
		final List<VocabularyCategory> sc = greekCardsDataSource.findVocabularyCategoriesWithCategoryAll();
		vocabularyCategoriesAdapter = new ArrayAdapter<VocabularyCategory>(this, android.R.layout.simple_spinner_item, sc);
		vocabularyCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    vocabularyCategories.setAdapter(vocabularyCategoriesAdapter);
	}
}
