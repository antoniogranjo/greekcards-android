package ag.greekcards.utils;

import ag.greekcards.model.base.IntEncoded;
import android.util.AndroidRuntimeException;

public final class Enums {
	private Enums() {}
	
	public static <T extends Enum<?> & IntEncoded> T enumByCode(Class<T> enumClass, int code) {
		for (T t : enumClass.getEnumConstants()) {
			if (t.getCode() == code) {
				return t;
			}
		}
		
		throw new AndroidRuntimeException("No se encuentra enum en [" + enumClass.getSimpleName() + "] con el código [" + code + "]");
	}
}
