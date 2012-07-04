package ag.greekcards;

import java.util.List;

import ag.greekcards.model.Sustantive;
import ag.greekcards.model.enums.SustantiveTranslationMode;
import ag.greekcards.utils.SustantiveUtils;
import ag.greekcards.utils.gui.Animations;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SustantiveTranslationActivity extends Activity {
	private static final String TAG = SustantiveTranslationActivity.class.getSimpleName();
	
	private TextView questionText;
	private TextView answerText;
	private Button showTranslation;
	private List<Sustantive> sustantives;
	private int sustantiveIndex = 0;
	private Sustantive sustantive;
	private SustantiveTranslationMode translationMode;
	
	public static final class IntentCodes {
		private IntentCodes() {}
		public static final String SUSTANTIVES = "sustantives";
		public static final String TRANSLATION_MODE = "translation_mode";
	}
	
	private OnClickListener onClickShowTranslation = new OnClickListener() {
		@Override
		public void onClick(View view) {
			showTranslation();
			if (thereAreMoreSustantivesToShow()) {
				showTranslation.setText(getString(R.string.next));
				showTranslation.setOnClickListener(onClickGoToNextSustantive);
			} else {
				showTranslation.setText(getString(R.string.exit));
				showTranslation.setOnClickListener(onClickGoBackToMainMenu);
			}
		}
	};
	
	private OnClickListener onClickGoToNextSustantive = new OnClickListener() {
		@Override
		public void onClick(View view) {
			loadNextSustantive();
			showTranslation.setText(getString(R.string.show_translation));
			showTranslation.setOnClickListener(onClickShowTranslation);
		}
	};
	
	private OnClickListener onClickGoBackToMainMenu = new OnClickListener() {
		@Override
		public void onClick(View view) {
			SustantiveTranslationActivity.this.finish();
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        initLayoutComponents();
        initBundleData();
        loadNextSustantive();
    }

	private void initBundleData() {
		final Bundle extra = getIntent().getExtras();
		this.sustantives = (List<Sustantive>)extra.get(IntentCodes.SUSTANTIVES);
		this.translationMode = SustantiveTranslationMode.valueOf((String)extra.get(IntentCodes.TRANSLATION_MODE));
	}

	private void initLayoutComponents() {
		setContentView(R.layout.activity_sustantive_translation);
        questionText = (TextView)findViewById(R.id.questionText);
        answerText = (TextView)findViewById(R.id.answerText);
        showTranslation = (Button)findViewById(R.id.showTranslation);
	}
	
	private void showTranslation() {
		answerText.startAnimation(Animations.FADE_IN);
	}
	
	private boolean thereAreMoreSustantivesToShow() {
		return this.sustantiveIndex < sustantives.size();
	}
	
	private void loadNextSustantive() {
		this.sustantive = sustantives.get(sustantiveIndex++);
		Log.d(TAG, "Configurando sustantivo [" + sustantive + "]");
		questionText.setText(SustantiveUtils.getQuestionText(sustantive, translationMode));
		answerText.startAnimation(Animations.FADE_OUT_NOW);
		answerText.setText(SustantiveUtils.getAnswerText(sustantive, translationMode));
		showTranslation.setText(getString(R.string.show_translation));
		showTranslation.setOnClickListener(onClickShowTranslation);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sustantive_translation, menu);
        return true;
    }
}
