package ag.greekcards.activities.vocabulary;

import java.util.List;

import ag.greekcards.R;
import ag.greekcards.db.GreekCardsDataSource;
import ag.greekcards.model.VocabularyCategory;
import ag.greekcards.model.VocabularyEntry;
import ag.greekcards.model.enums.VocabularyEntryEditionMode;
import ag.greekcards.utils.BundleData;
import ag.greekcards.utils.ResultCodes;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
	private static final int CONFIRM_ENTRY_DELETION = 1;
	private static final int DIALOG_DELETION_OK = 2;
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
	private AlertDialog deleteDialog;
	private AlertDialog deletionOkDialog;
	
	private final OnClickListener onClickSaveNewSustantive = new OnClickListener() {
		@Override
		public void onClick(View v) {
			fromFieldsToVocabularyEntry();
			vocabularyEntry = greekCardsDataSource.newVocabularyEntry(vocabularyEntry);
			updateEditResultOk();
			showSavedOkMessage();
		}
	};
	
	private final OnClickListener onClickSaveEditSustantive = new OnClickListener() {
		@Override
		public void onClick(View v) {
			fromFieldsToVocabularyEntry();
			greekCardsDataSource.updateVocabularyEntry(vocabularyEntry);
			updateEditResultOk();
			showSavedOkMessage();
		}
	};
	
	private OnClickListener onClickGoBackToMainMenu = new OnClickListener() {
		@Override
		public void onClick(View view) {
			EditVocabularyEntryActivity.this.finish();
		}
	};
	
	private final DialogInterface.OnClickListener onClickDialogExit = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialoginterface, int i) {
			dialoginterface.dismiss();
		}
	};
	
	private final DialogInterface.OnClickListener onClickDialogDelete = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialoginterface, int i) {
			greekCardsDataSource.delete(vocabularyEntry);
			setResult(ResultCodes.DELETE_ENTRY_OK);
			showDialog(DIALOG_DELETION_OK);
		}
	};
	
	private void updateEditResultOk() {
		Intent i = new Intent();
		i.putExtra(BundleData.VOCABULARY_ENTRY, this.vocabularyEntry);
		this.setResult(ResultCodes.EDIT_ENTRY_OK, i);
	}
	
	private void showSavedOkMessage() {
		showDialog(DIALOG_SAVED_OK);
	}
	
	public void deleteVocabularyEntry(View v) {
		showDialog(CONFIRM_ENTRY_DELETION);
	}
	
    private void initSavedOkDialog() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(getString(R.string.vocabulary_entry_saved_ok))
    	       .setCancelable(false)
    	       .setPositiveButton(R.string.back, new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	                EditVocabularyEntryActivity.this.finish();
    	           }
    	       });
    	savedOkDialog = builder.create();
	}
    
    private void initDeletionOkDialog() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(getString(R.string.vocabulary_entry_deleted_ok))
    	       .setCancelable(false)
    	       .setPositiveButton(R.string.back, new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	                EditVocabularyEntryActivity.this.finish();
    	           }
    	       });
    	savedOkDialog = builder.create();
	}
    
	private void initDeleteDialog() {
		final AlertDialog.Builder deleteDialogBuilder = new AlertDialog.Builder(this);
		deleteDialogBuilder.setTitle(getText(R.string.app_name));
		deleteDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
		deleteDialogBuilder.setMessage(getText(R.string.delete_vocabulary_entry_confirmation));
		deleteDialogBuilder.setPositiveButton(R.string.delete, onClickDialogDelete);
		deleteDialogBuilder.setNegativeButton(R.string.cancel, onClickDialogExit);
		deleteDialog = deleteDialogBuilder.create();
	}
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        greekCardsDataSource = new GreekCardsDataSource(this);
        setContentView(R.layout.activity_edit_vocabulary_entry);
        bindViewComponents();
        initBundleData();
        setupViewComponentsBasingOnEditionMode();
        initSavedOkDialog();
        initDeleteDialog();
        initDeletionOkDialog();
    }

    @Override
	protected Dialog onCreateDialog(int id) {
        switch(id) {
	        case DIALOG_SAVED_OK: return this.savedOkDialog;
	        case CONFIRM_ENTRY_DELETION: return this.deleteDialog;
	        case DIALOG_DELETION_OK: return this.deletionOkDialog;
	        default: return null;
        }
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
