package ag.greekcards.model.enums;

import ag.greekcards.model.base.IntEncoded;
import ag.greekcards.utils.Enums;

public enum VerbNumber implements IntEncoded {
	SINGULAR(1), PLURAL(2);
	
	private int code;
	
	private VerbNumber(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static VerbNumber fromCode(int code) {
		return Enums.enumByCode(VerbNumber.class, code);
	}
}
