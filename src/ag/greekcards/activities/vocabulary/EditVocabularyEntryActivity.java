package ag.greekcards.activities.vocabulary;

import java.util.List;

import ag.greekcards.R;
import ag.greekcards.db.GreekCardsDataSource;
import ag.greekcards.model.VocabularyCategory;
import ag.greekcards.model.VocabularyEntry;
import ag.greekcards.model.enums.VocabularyEntryEditionMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EditVocabularyEntryActivity extends Activity {
	private static final int DIALOG_SAVED_OK = 0;
	private GreekCardsDataSource greekCardsDataSource;
	private VocabularyEntry vocabularyEntry;
	private VocabularyEntryEditionMode editionMode;
	private Button saveButton;
	private Button cancelButton;
	private EditText spanishText;
	private EditText greekText;
	private Spinner vocabularyCategories;
	private ArrayAdapter<VocabularyCategory> vocabularyCategoriesAdapter;
	private AlertDialog savedOkDialog;
	
	public static final class BundleData {
		private BundleData() {}
		public static final String VOCABULARY_ENTRY = "voc_entry";
	}
	
	private final OnClickListener onClickSaveNewSustantive = new OnClickListener() {
		@Override
		public void onClick(View v) {
			fromFieldsToVocabularyEntry();
			vocabularyEntry = greekCardsDataSource.newVocabularyEntry(vocabularyEntry);
			showSavedOkMessage();
		}
	};
	
	private final OnClickListener onClickSaveEditSustantive = new OnClickListener() {
		@Override
		public void onClick(View v) {
			fromFieldsToVocabularyEntry();
			greekCardsDataSource.updateVocabularyEntry(vocabularyEntry);
			showSavedOkMessage();
		}
	};
	
	private OnClickListener onClickGoBackToMainMenu = new OnClickListener() {
		@Override
		public void onClick(View view) {
			EditVocabularyEntryActivity.this.finish();
		}
	};
	
	private void showSavedOkMessage() {
		showDialog(DIALOG_SAVED_OK);
	}
	
    private AlertDialog initSavedOkDialog() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(getString(R.string.vocabulary_entry_saved_ok))
    	       .setCancelable(false)
    	       .setPositiveButton(R.string.back, new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	                EditVocabularyEntryActivity.this.finish();
    	           }
    	       });
    	return builder.create();
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        greekCardsDataSource = new GreekCardsDataSource(this);
        setContentView(R.layout.activity_edit_vocabulary_entry);
        bindViewComponents();
        initBundleData();
        setupViewComponentsBasingOnEditionMode();
        savedOkDialog = initSavedOkDialog();
    }

    @Override
	protected Dialog onCreateDialog(int id) {
    	Dialog dialog;
        switch(id) {
        case DIALOG_SAVED_OK: return this.savedOkDialog;
        default:
            dialog = null;
        }
        return dialog;
	}

	private void bindViewComponents() {
		cancelButton = (Button)findViewById(R.id.cancelButton);
		saveButton = (Button)findViewById(R.id.saveButton);
		greekText = (EditText)findViewById(R.id.greekText);
		spanishText = (EditText)findViewById(R.id.spanishText);
		vocabularyCategories = (Spinner)findViewById(R.id.vocabularyCategories);
	}

	private void setupViewComponentsBasingOnEditionMode() {
		setupCategoriesSpinner();
		
		if (VocabularyEntryEditionMode.ADD.equals(this.editionMode)) {
			saveButton.setOnClickListener(onClickSaveNewSustantive);
		} else {
			saveButton.setOnClickListener(onClickSaveEditSustantive);
		}
		
		cancelButton.setOnClickListener(onClickGoBackToMainMenu);
	}

	private void setupCategoriesSpinner() {
		final List<VocabularyCategory> sc = greekCardsDataSource.findVocabularyCategoriesWithCategoryAll();
		vocabularyCategoriesAdapter = new ArrayAdapter<VocabularyCategory>(this, android.R.layout.simple_spinner_item, sc);
		vocabularyCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    vocabularyCategories.setAdapter(vocabularyCategoriesAdapter);
	}

	private void initBundleData() {
    	final Bundle extra = getIntent().getExtras();
    	if (extra != null && extra.containsKey(BundleData.VOCABULARY_ENTRY)) {
    		this.editionMode = VocabularyEntryEditionMode.EDIT;
    		this.vocabularyEntry = (VocabularyEntry)extra.getParcelable(BundleData.VOCABULARY_ENTRY);
			fromVocabularyEntryToFields();
    	} else {
			this.editionMode = VocabularyEntryEditionMode.ADD;
			this.vocabularyEntry = new VocabularyEntry();
		}
	}
	
	private void fromVocabularyEntryToFields() {
		this.spanishText.setText(this.vocabularyEntry.getSpanishText());
		this.greekText.setText(this.vocabularyEntry.getGreekText());
		
		vocabularyCategories.setSelection(getCategoryPositionInSpinner(this.vocabularyEntry.getCategoryId()));
	}
	
	private int getCategoryPositionInSpinner(int categoryId) {
		for (int i = 0; i < vocabularyCategories.getCount(); i++) {
			if (categoryId == vocabularyCategoriesAdapter.getItem(i).getId().intValue()) {
				return i;
			}
		}
		
		return 0;
	}
	
	private void fromFieldsToVocabularyEntry() {
		this.vocabularyEntry.setSpanishText(this.spanishText.getText().toString());
		this.vocabularyEntry.setGreekText(this.greekText.getText().toString());
		
		final int selectedCategoryPosition = vocabularyCategories.getSelectedItemPosition();
    	final VocabularyCategory category = vocabularyCategoriesAdapter.getItem(selectedCategoryPosition);
    	this.vocabularyEntry.setCategoryId(category.getId());
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_vocabulary_entry, menu);
        return true;
    }
    
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}