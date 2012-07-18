package ag.greekcards.activities.verbs;

import ag.greekcards.R;
import ag.greekcards.activities.base.GreekCardsActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class EditVerbActivity extends GreekCardsActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_verb);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_verb, menu);
        return true;
    }
}
