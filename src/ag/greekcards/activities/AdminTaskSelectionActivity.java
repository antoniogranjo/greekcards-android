package ag.greekcards.activities;

import java.util.ArrayList;
import java.util.List;

import ag.greekcards.R;
import ag.greekcards.activities.verbs.EditVerbActivity;
import ag.greekcards.activities.verbs.VerbSelectionActivity;
import ag.greekcards.activities.vocabulary.EditVocabularyEntryActivity;
import ag.greekcards.activities.vocabulary.VocabularyEntrySelectionActivity;
import ag.greekcards.utils.NavigationUtils;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class AdminTaskSelectionActivity extends ListActivity {
	private ListAdapter adminTaskSelectionListAdapter; 
	private List<Class<? extends Activity>> onClickActivities = new ArrayList<Class<? extends Activity>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeListAdapter();
    }

    private void initializeListAdapter() {
    	if (this.adminTaskSelectionListAdapter == null) {
    		final List<String> options = new ArrayList<String>();
    		
    		options.add(getString(R.string.add_vocabulary_entry));
    		onClickActivities.add(EditVocabularyEntryActivity.class);
    		
    		options.add(getString(R.string.edit_vocabulary_entry));
    		onClickActivities.add(VocabularyEntrySelectionActivity.class);
    		
    		options.add(getString(R.string.add_verb));
    		onClickActivities.add(EditVerbActivity.class);
    		
    		options.add(getString(R.string.edit_verb));
    		onClickActivities.add(VerbSelectionActivity.class);
    		
    		this.adminTaskSelectionListAdapter = new ArrayAdapter<String>(this,	android.R.layout.simple_list_item_1, options);
    	}
    	
		setListAdapter(this.adminTaskSelectionListAdapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		NavigationUtils.startActivity(this, onClickActivities.get(position));
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_admin_task_selection, menu);
        return true;
    }
}
