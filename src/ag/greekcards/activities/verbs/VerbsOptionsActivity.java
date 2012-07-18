package ag.greekcards.activities.verbs;

import ag.greekcards.R;
import ag.greekcards.activities.base.GreekCardsActivity;
import android.os.Bundle;
import android.view.Menu;

public class VerbsOptionsActivity extends GreekCardsActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbs_options);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_verbs_options, menu);
        return true;
    }
}
