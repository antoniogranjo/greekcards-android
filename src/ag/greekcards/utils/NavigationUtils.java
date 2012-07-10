package ag.greekcards.utils;

import ag.greekcards.activities.vocabulary.EditVocabularyEntryActivity;
import ag.greekcards.model.VocabularyEntry;
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
		extras.putParcelable(EditVocabularyEntryActivity.BundleData.VOCABULARY_ENTRY, ve);
		startActivity(currentActivity, EditVocabularyEntryActivity.class, extras);
	}
}
