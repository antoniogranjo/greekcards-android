package ag.greekcards.activities.verbs;

import ag.greekcards.R;
import ag.greekcards.activities.base.GreekCardsActivity;
import android.os.Bundle;
import android.view.Menu;

public class VerbSelectionActivity extends GreekCardsActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verb_selection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_verb_selection, menu);
        return true;
    }
}
