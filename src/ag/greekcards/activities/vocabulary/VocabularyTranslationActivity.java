package ag.greekcards.activities.vocabulary;

import java.util.Collections;
import java.util.List;

import ag.greekcards.R;
import ag.greekcards.db.GreekCardsDataSource;
import ag.greekcards.model.VocabularyCategory;
import ag.greekcards.model.VocabularyEntry;
import ag.greekcards.model.enums.TranslationMode;
import ag.greekcards.utils.NavigationUtils;
import ag.greekcards.utils.VocabularyUtils;
import ag.greekcards.utils.gui.Animations;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class VocabularyTranslationActivity extends Activity {
	private static final String TAG = VocabularyTranslationActivity.class.getSimpleName();
	
	private GreekCardsDataSource greekCardsDataSource;
	
	private TextView questionText;
	private TextView answerText;
	private Button showTranslation;
	private VocabularyCategory vocabularyCategory;
	private List<VocabularyEntry> vocabularyEntries;
	private int veIndex = 0;
	private VocabularyEntry vocabularyEntry;
	private TranslationMode translationMode;
	
	public static final class BundleData {
		private BundleData() {}
		public static final String TRANSLATION_MODE = "translation_mode";
		public static final String VOCABULARY_CATEGORY = "voc_categ";
	}
	
	public static final class ResultCodes {
		private ResultCodes() {}
		public static final int EDIT_ENTRY_REQUEST = 101;
		public static final int EDIT_ENTRY_RESULT_OK = 101;
	}
	
	private final OnClickListener onClickShowTranslation = new OnClickListener() {
		@Override
		public void onClick(View view) {
			showTranslation();
			if (thereAreMoreSustantivesToShow()) {
				showTranslation.setText(getString(R.string.next));
				showTranslation.setOnClickListener(onClickGoToNextVocabularyEntry);
			} else {
				showTranslation.setText(getString(R.string.exit));
				showTranslation.setOnClickListener(onClickGoBackToMainMenu);
			}
		}
	};
	
	private final OnClickListener onClickGoToNextVocabularyEntry = new OnClickListener() {
		@Override
		public void onClick(View view) {
			loadNextVocabularyEntry();
			showTranslation.setText(getString(R.string.show_translation));
			showTranslation.setOnClickListener(onClickShowTranslation);
		}
	};
	
	private final OnClickListener onClickGoBackToMainMenu = new OnClickListener() {
		@Override
		public void onClick(View view) {
			VocabularyTranslationActivity.this.finish();
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        greekCardsDataSource = new GreekCardsDataSource(this);
        initLayoutComponents();
        initBundleData();
        loadVocabularyEntries();
        loadNextVocabularyEntry();
    }

	private void loadVocabularyEntries() {
		this.vocabularyEntries = greekCardsDataSource.findVocabularyEntries(this.vocabularyCategory);
		Collections.shuffle(this.vocabularyEntries);
	}

	private void initBundleData() {
		final Bundle extra = getIntent().getExtras();
		this.vocabularyCategory = (VocabularyCategory)extra.getParcelable(BundleData.VOCABULARY_CATEGORY);
		final TextView currentCategory = (TextView)findViewById(R.id.currentCategory);
		currentCategory.setText(this.vocabularyCategory.getDescription());
		this.translationMode = TranslationMode.valueOf((String)extra.get(BundleData.TRANSLATION_MODE));
	}

	private void initLayoutComponents() {
		setContentView(R.layout.activity_vocabulary_entry_translation);
        questionText = (TextView)findViewById(R.id.questionText);
        answerText = (TextView)findViewById(R.id.answerText);
        showTranslation = (Button)findViewById(R.id.showTranslation);
	}
	
	private void showTranslation() {
		answerText.startAnimation(Animations.FADE_IN);
	}
	
	private boolean thereAreMoreSustantivesToShow() {
		return this.veIndex < vocabularyEntries.size();
	}
	
	private void loadNextVocabularyEntry() {
		this.vocabularyEntry = vocabularyEntries.get(veIndex++);
		Log.d(TAG, "Configurando entrada de vocabulario [" + vocabularyEntry + "]");
		showCurrentVocabularyEntry();
	}

	private void showCurrentVocabularyEntry() {
		questionText.setText(VocabularyUtils.getQuestionText(vocabularyEntry, translationMode));
		answerText.startAnimation(Animations.FADE_OUT_NOW);
		answerText.setText(VocabularyUtils.getAnswerText(vocabularyEntry, translationMode));
		showTranslation.setText(getString(R.string.show_translation));
		showTranslation.setOnClickListener(onClickShowTranslation);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_vocabulary_entry_translation, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.edit_vocabulary_entry:
        	NavigationUtils.startVocabularyEntryEdit(VocabularyTranslationActivity.this, vocabularyEntry);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ResultCodes.EDIT_ENTRY_REQUEST:
			processEditEntryResult(resultCode, data);
			break;
		default:
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	private void processEditEntryResult(int resultCode, Intent data) {
		if (resultCode == ResultCodes.EDIT_ENTRY_RESULT_OK) {
			VocabularyEntry ve = data.getParcelableExtra(ag.greekcards.activities.vocabulary.EditVocabularyEntryActivity.BundleData.VOCABULARY_ENTRY);
			this.vocabularyEntry.setSpanishText(ve.getSpanishText());
			this.vocabularyEntry.setGreekText(ve.getGreekText());
			showCurrentVocabularyEntry();
		}
	}
}
