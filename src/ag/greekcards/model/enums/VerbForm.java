package ag.greekcards.model.enums;

import ag.greekcards.model.base.IntEncoded;
import ag.greekcards.utils.Enums;

public enum VerbForm implements IntEncoded {
	PRESENT(1), AORISTOS(2), PARATATIKOS(3);
	
	private int code;
	
	private VerbForm(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public static VerbForm fromCode(int code) {
		return Enums.enumByCode(VerbForm.class, code);
	}
}
