package ag.greekcards.model.enums;

import ag.greekcards.R;
import ag.greekcards.utils.RequestCodes;

public enum Language {
	GREEK("el-GR", R.string.greek_text, RequestCodes.GREEK_SPEECH_RECOGNIZING), SPANISH("es-ES", R.string.spanish_text, RequestCodes.SPANISH_SPEECH_RECOGNIZING);
	
	private String code;
	private int requestCode;
	private int promptStringId;
	
	private Language(String code, int promptStringId, int requestCode) {
		this.code = code;
		this.promptStringId = promptStringId;
		this.requestCode = requestCode;
	}
	
	public int getRequestCode() {
		return requestCode;
	}

	public int getPromptStringId() {
		return promptStringId;
	}

	public String getCode() {
		return this.code;
	}
}
