package ag.greekcards.utils;

import ag.greekcards.activities.vocabulary.EditVocabularyEntryActivity;
import ag.greekcards.activities.vocabulary.VocabularyTranslationActivity;
import ag.greekcards.activities.vocabulary.VocabularyTranslationOptionsActivity;
import ag.greekcards.model.VocabularyCategory;
import ag.greekcards.model.VocabularyEntry;
import ag.greekcards.model.enums.Language;
import ag.greekcards.model.enums.TranslationMode;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;

public final class NavigationUtils {
	private NavigationUtils() {}
	
	public static void startActivity(Activity currentActivity, Class<? extends Activity> nextActivityClass) {
		startActivity(currentActivity, nextActivityClass, null);
	}
	
	public static void startActivity(Activity currentActivity, Class<? extends Activity> nextActivityClass, Bundle extras) {
		final Intent navigationIntent = new Intent(currentActivity.getBaseContext(), nextActivityClass);
		if (extras != null && extras.size() > 0) {
			navigationIntent.putExtras(extras);
		}
		currentActivity.startActivity(navigationIntent);
	}
	
	public static void startActivityForResult(Activity currentActivity, Class<? extends Activity> nextActivityClass, Bundle extras, int requestCode) {
		final Intent navigationIntent = new Intent(currentActivity.getBaseContext(), nextActivityClass);
		if (extras != null && extras.size() > 0) {
			navigationIntent.putExtras(extras);
		}
		currentActivity.startActivityForResult(navigationIntent, requestCode);
	}
	
	public static void startSpeechRecognitionActivity(Activity currentActivity, Language language) {
		 final Intent speechRecognitionIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		 speechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		 speechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, currentActivity.getString(language.getPromptStringId()));
		 speechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language.getCode());
		 currentActivity.startActivityForResult(speechRecognitionIntent, language.getRequestCode());
	}
	
	public static void startVocabularyEntryEdit(Activity currentActivity, VocabularyEntry ve) {
		final Bundle extras = new Bundle();
		extras.putParcelable(BundleData.VOCABULARY_ENTRY, ve);
		startActivityForResult(currentActivity, EditVocabularyEntryActivity.class, extras, RequestCodes.EDIT_ENTRY);
	}
	
	public static void startVocabularyTranslation(VocabularyTranslationOptionsActivity currentActivity, VocabularyCategory category, TranslationMode translationMode) {
		final Bundle extras = new Bundle(2);
    	extras.putParcelable(BundleData.VOCABULARY_CATEGORY, category);
    	extras.putString(BundleData.TRANSLATION_MODE, translationMode.name());
		NavigationUtils.startActivityForResult(currentActivity, VocabularyTranslationActivity.class, extras, RequestCodes.START_VOCABULARY_TRANSLATION_ACTIVITY);
	}
}
