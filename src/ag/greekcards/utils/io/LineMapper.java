package ag.greekcards.utils.io;

public interface LineMapper<T> {
	T map(String line);
}
