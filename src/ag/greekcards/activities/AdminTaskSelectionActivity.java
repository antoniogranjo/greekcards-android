package ag.greekcards.activities;

import ag.greekcards.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class AdminTaskSelectionActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_task_selection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_admin_task_selection, menu);
        return true;
    }

    
}
