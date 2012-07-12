package ag.greekcards.utils;

public final class ResultCodes {
	private ResultCodes() {}
	private static final int BASE = 8000;
	public static final int EDIT_ENTRY_OK = BASE + 1;
	public static final int FINISH_PREVIOUS = BASE + 2;
	public static final int DELETE_ENTRY_OK = BASE + 3;
}