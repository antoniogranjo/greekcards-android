package ag.greekcards.activities.vocabulary;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ag.greekcards.R;
import ag.greekcards.activities.base.GreekCardsActivity;
import ag.greekcards.model.VocabularyEntry;
import ag.greekcards.utils.NavigationUtils;
import ag.greekcards.utils.RequestCodes;
import ag.greekcards.utils.ResultCodes;
import ag.greekcards.utils.ViewUtils;
import ag.greekcards.utils.collections.CollectionUtils;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class VocabularyEntrySelectionActivity extends GreekCardsActivity {
	private EditText filter;
	private ListView vocabularyList;
	private List<VocabularyEntry> allEntries;
	
	private OnItemClickListener vocabularyEntryClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			final VocabularyEntry ve = (VocabularyEntry) parent.getItemAtPosition(position);
			NavigationUtils.startVocabularyEntryEdit(VocabularyEntrySelectionActivity.this, ve);
		}
	};
	
	private TextWatcher vocabularyListFilter = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence charsequence, int i, int j, int k) {
			updateListFromFilter();
		}
		
		@Override
		public void beforeTextChanged(CharSequence charsequence, int i, int j, int k) {}
		
		@Override
		public void afterTextChanged(Editable editable) {}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadVocabularyEntries();
        setContentView(R.layout.activity_vocabulary_entry_selection);
        bindViewComponents();
        setupEventHandlers();
        updateListFromFilter();
    }

	private void loadVocabularyEntries() {
		allEntries = getGreekCardsDataSource().findVocabularyEntries();
        Collections.sort(allEntries, CollectionUtils.COMPARATOR_BY_TOSTRING);
	}
    
    private void setupEventHandlers() {
    	this.filter.addTextChangedListener(vocabularyListFilter);
    	vocabularyList.setOnItemClickListener(vocabularyEntryClickListener);
	}

	private void bindViewComponents() {
		filter = (EditText)findViewById(R.id.search_box);
		ViewUtils.configureAsClearableEditText(filter);
		
		vocabularyList = (ListView)findViewById(R.id.allVocabularyEntries);
	}

	public void updateListFromFilter() {
    	String filterValue = StringUtils.trim(this.filter.getText().toString());
    	List<VocabularyEntry> filteredEntries;
    	if (StringUtils.isEmpty(filterValue)) {
    		filteredEntries = allEntries;
    	} else {
    		filteredEntries = CollectionUtils.filterByToString(allEntries, filterValue);
    	}
    	
        vocabularyList.setAdapter(new ArrayAdapter<VocabularyEntry>(this, android.R.layout.simple_list_item_1, filteredEntries));
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
	
	private void processEditEntryResult(int resultCode, Intent data) {
		if (resultCode == ResultCodes.EDIT_ENTRY_OK || resultCode == ResultCodes.DELETE_ENTRY_OK) {
			loadVocabularyEntries();
			updateListFromFilter();
		}
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_vocabulary_entry_selection, menu);
        return true;
    }
}
