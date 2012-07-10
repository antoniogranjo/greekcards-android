package ag.greekcards.utils;

import ag.greekcards.activities.vocabulary.EditVocabularyEntryActivity;
import ag.greekcards.activities.vocabulary.VocabularyTranslationActivity;
import ag.greekcards.activities.vocabulary.VocabularyTranslationOptionsActivity;
import ag.greekcards.model.VocabularyCategory;
import ag.greekcards.model.VocabularyEntry;
import ag.greekcards.model.enums.TranslationMode;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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
