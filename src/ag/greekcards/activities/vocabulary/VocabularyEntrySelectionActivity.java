package ag.greekcards.activities.vocabulary;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import ag.greekcards.R;
import ag.greekcards.db.GreekCardsDataSource;
import ag.greekcards.model.VocabularyCategory;
import ag.greekcards.model.VocabularyEntry;
import ag.greekcards.utils.collections.CollectionUtils;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class VocabularyEntrySelectionActivity extends Activity {
	private EditText filter;
	private ListView vocabularyList;
	private List<VocabularyEntry> allEntries;
	private GreekCardsDataSource greekCardsDataSource;
	
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
        greekCardsDataSource = new GreekCardsDataSource(this);
        allEntries = greekCardsDataSource.findVocabularyEntries();
        setContentView(R.layout.activity_vocabulary_entry_selection);
        bindViewComponents();
        setupEventHandlers();
        updateListFromFilter();
    }
    
    private void setupEventHandlers() {
    	this.filter.addTextChangedListener(vocabularyListFilter);
	}

	private void bindViewComponents() {
		filter = (EditText)findViewById(R.id.search_box);
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
    	
        // TODO
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_vocabulary_entry_selection, menu);
        return true;
    }
}
