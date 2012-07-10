package ag.greekcards.activities.vocabulary;

import java.util.Collections;
import java.util.List;

import ag.greekcards.R;
import ag.greekcards.db.GreekCardsDataSource;
import ag.greekcards.model.VocabularyCategory;
import ag.greekcards.model.VocabularyEntry;
import ag.greekcards.model.enums.TranslationMode;
import ag.greekcards.utils.BundleData;
import ag.greekcards.utils.NavigationUtils;
import ag.greekcards.utils.RequestCodes;
import ag.greekcards.utils.ResultCodes;
import ag.greekcards.utils.VocabularyUtils;
import ag.greekcards.utils.gui.Animations;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
	private static final int RESTART_DIALOG_ID = 1;
	private static final int NO_ENTRIES_DIALOG_ID = 2;
	private static final String TAG = VocabularyTranslationActivity.class.getSimpleName();
	
	private GreekCardsDataSource greekCardsDataSource;
	
	private TextView questionText;
	private TextView answerText;
	private Button showTranslation;
	private VocabularyCategory vocabularyCategory;
	private TextView currentCategory;
	private List<VocabularyEntry> vocabularyEntries;
	private int veIndex = 0;
	private VocabularyEntry vocabularyEntry;
	private TranslationMode translationMode;
	
	private Dialog restartDialog;
	private Dialog noEntriesDialog;
	
	private final OnClickListener onClickShowTranslation = new OnClickListener() {
		@Override
		public void onClick(View view) {
			showTranslation();
			showTranslation.setText(getString(R.string.next));
			if (thereAreMoreSustantivesToShow()) {
				showTranslation.setOnClickListener(onClickGoToNextVocabularyEntry);
			} else {
				showTranslation.setOnClickListener(onClickShowRestartDialog);
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
	
	private final OnClickListener onClickShowRestartDialog = new OnClickListener() {
		@Override
		public void onClick(View view) {
			showDialog(RESTART_DIALOG_ID);
		}
	};
	
	private final DialogInterface.OnClickListener onClickDialogExit = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialoginterface, int i) {
			setResult(ResultCodes.FINISH_PREVIOUS);
			VocabularyTranslationActivity.this.finish();
		}
	};
	
	private final DialogInterface.OnClickListener onClickDialogRestart = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialoginterface, int i) {
			restartTranslation();
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
		shuffleVocabularyEntries();
	}
	
	private void restartTranslation() {
		veIndex = 0;
		shuffleVocabularyEntries();
		loadNextVocabularyEntry();
	}

	private void shuffleVocabularyEntries() {
		Collections.shuffle(this.vocabularyEntries);
	}

	private void initBundleData() {
		final Bundle extra = getIntent().getExtras();
		this.vocabularyCategory = (VocabularyCategory)extra.getParcelable(BundleData.VOCABULARY_CATEGORY);
		currentCategory = (TextView)findViewById(R.id.currentCategory);
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
		
		if (this.vocabularyEntry == null) {
			
		}
		
		Log.d(TAG, "Configurando entrada de vocabulario [" + vocabularyEntry + "]");
		showCurrentVocabularyEntry();
	}

	private void showCurrentVocabularyEntry() {
		questionText.setText(VocabularyUtils.getQuestionText(vocabularyEntry, translationMode));
		
		answerText.startAnimation(Animations.FADE_OUT_NOW);
		answerText.setText(VocabularyUtils.getAnswerText(vocabularyEntry, translationMode));
		
		showTranslation.setText(getString(R.string.show_translation));
		showTranslation.setOnClickListener(onClickShowTranslation);
		
		currentCategory.setText(this.vocabularyCategory.getDescription() + " (" + veIndex + " / " + this.vocabularyEntries.size() + ")");
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
		case RequestCodes.EDIT_ENTRY:
			processEditEntryResult(resultCode, data);
			break;
		default:
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
	
    @Override
	protected Dialog onCreateDialog(int id) {
    	Dialog dialog;
        switch(id) {
        case RESTART_DIALOG_ID:
        	if (restartDialog == null) {
				initializeRestartDialog();
			}
        	
        	return this.restartDialog;
        case NO_ENTRIES_DIALOG_ID:
        	if (noEntriesDialog == null) {
        		initializeNoEntriesDialog();
        	}
        	
        	return this.noEntriesDialog;
        default:
            dialog = null;
        }
        return dialog;
	}

	private void initializeRestartDialog() {
		final AlertDialog.Builder resetDialogBuilder = new AlertDialog.Builder(this);
		resetDialogBuilder.setTitle(getText(R.string.app_name));
		resetDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
		resetDialogBuilder.setMessage(getText(R.string.end_of_translation_activity));
		resetDialogBuilder.setPositiveButton(R.string.restart, onClickDialogRestart);
		resetDialogBuilder.setNegativeButton(R.string.exit, onClickDialogExit);
		restartDialog = resetDialogBuilder.create();
	}
	
	private void initializeNoEntriesDialog() {
		final AlertDialog.Builder noEntriesDialogBuilder = new AlertDialog.Builder(this);
		noEntriesDialogBuilder.setTitle(getText(R.string.app_name));
		noEntriesDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
		noEntriesDialogBuilder.setMessage(getText(R.string.no_vocabulary_entry));
		noEntriesDialogBuilder.setCancelable(false);
		noEntriesDialogBuilder.setPositiveButton(R.string.exit, onClickDialogExit);
		noEntriesDialog = noEntriesDialogBuilder.create();
	}

	private void processEditEntryResult(int resultCode, Intent data) {
		if (resultCode == ResultCodes.EDIT_ENTRY_OK) {
			VocabularyEntry ve = data.getParcelableExtra(BundleData.VOCABULARY_ENTRY);
			this.vocabularyEntry.setSpanishText(ve.getSpanishText());
			this.vocabularyEntry.setGreekText(ve.getGreekText());
			showCurrentVocabularyEntry();
		} else if (resultCode == ResultCodes.DELETE_ENTRY_OK) {
			loadVocabularyEntries();
	        loadNextVocabularyEntry();
		}
	}
}
