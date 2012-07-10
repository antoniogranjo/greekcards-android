package ag.greekcards.activities;

import ag.greekcards.R;
import ag.greekcards.activities.verbs.VerbsOptionsActivity;
import ag.greekcards.activities.vocabulary.VocabularyTranslationOptionsActivity;
import ag.greekcards.db.GreekCardsDataSource;
import ag.greekcards.utils.NavigationUtils;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainTaskSelectionActivity extends Activity {
	private static final int ABOUT_DIALOG_ID = 1;
	
	private GreekCardsDataSource greekCardsDataSource;
	private Dialog aboutDialog;
	
	public void goToVocabulary(View view) {
		NavigationUtils.startActivity(MainTaskSelectionActivity.this, VocabularyTranslationOptionsActivity.class);
	}
	
	public void goToVerbs(View view) {
		NavigationUtils.startActivity(MainTaskSelectionActivity.this, VerbsOptionsActivity.class);
	}
	
	public void goToAdmin(View view) {
		NavigationUtils.startActivity(MainTaskSelectionActivity.this, AdminTaskSelectionActivity.class);
	}
	
	public void showAbout(View view) {
		showDialog(ABOUT_DIALOG_ID);
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        greekCardsDataSource = new GreekCardsDataSource(this);
        
        setContentView(R.layout.activity_task_selection);
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
		aboutDialog = new Dialog(this);
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
