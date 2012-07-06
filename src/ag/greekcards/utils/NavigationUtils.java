package ag.greekcards.utils;

import android.app.Activity;
import android.content.Intent;

public final class NavigationUtils {
	private NavigationUtils() {}
	
	public static void startSimpleNavigaton(Activity currentActivity, Class<? extends Activity> nextActivityClass) {
		final Intent vocabularyTranslation = new Intent(currentActivity.getBaseContext(), nextActivityClass);
		currentActivity.startActivity(vocabularyTranslation);
	}
}
