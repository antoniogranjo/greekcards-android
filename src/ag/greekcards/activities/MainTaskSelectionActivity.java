package ag.greekcards.activities;

import ag.greekcards.R;
import ag.greekcards.activities.base.GreekCardsActivity;
import ag.greekcards.activities.verbs.VerbsOptionsActivity;
import ag.greekcards.activities.vocabulary.VocabularyTranslationOptionsActivity;
import ag.greekcards.utils.NavigationUtils;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainTaskSelectionActivity extends GreekCardsActivity {
	private static final int ABOUT_DIALOG_ID = 1;
	
	private Dialog aboutDialog;
	
	public void goToVocabulary(View view) {
		NavigationUtils.startActivity(this, VocabularyTranslationOptionsActivity.class);
	}
	
	public void goToVerbs(View view) {
		NavigationUtils.startActivity(this, VerbsOptionsActivity.class);
	}
	
	public void goToAdmin(View view) {
		NavigationUtils.startActivity(this, AdminTaskSelectionActivity.class);
	}
	
	public void showAbout(View view) {
		showDialog(ABOUT_DIALOG_ID);
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
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
}
