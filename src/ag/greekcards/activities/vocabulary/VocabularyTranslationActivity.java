package ag.greekcards.activities.vocabulary;

import java.util.List;

import ag.greekcards.R;
import ag.greekcards.model.VocabularyEntry;
import ag.greekcards.model.enums.TranslationMode;
import ag.greekcards.utils.VocabularyUtils;
import ag.greekcards.utils.gui.Animations;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class VocabularyTranslationActivity extends Activity {
	private static final String TAG = VocabularyTranslationActivity.class.getSimpleName();
	
	private TextView questionText;
	private TextView answerText;
	private Button showTranslation;
	private List<VocabularyEntry> vocabularyEntries;
	private int veIndex = 0;
	private VocabularyEntry vocabularyEntry;
	private TranslationMode translationMode;
	
	public static final class BundleData {
		private BundleData() {}
		public static final String VOCABULARY_ENTRIES = "sustantives";
		public static final String TRANSLATION_MODE = "translation_mode";
	}
	
	private OnClickListener onClickShowTranslation = new OnClickListener() {
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
	
	private OnClickListener onClickGoToNextVocabularyEntry = new OnClickListener() {
		@Override
		public void onClick(View view) {
			loadNextVocabularyEntry();
			showTranslation.setText(getString(R.string.show_translation));
			showTranslation.setOnClickListener(onClickShowTranslation);
		}
	};
	
	private OnClickListener onClickGoBackToMainMenu = new OnClickListener() {
		@Override
		public void onClick(View view) {
			VocabularyTranslationActivity.this.finish();
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        initLayoutComponents();
        initBundleData();
        loadNextVocabularyEntry();
    }

    @SuppressWarnings("unchecked")
	private void initBundleData() {
		final Bundle extra = getIntent().getExtras();
		this.vocabularyEntries = (List<VocabularyEntry>)extra.get(BundleData.VOCABULARY_ENTRIES);
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
}
