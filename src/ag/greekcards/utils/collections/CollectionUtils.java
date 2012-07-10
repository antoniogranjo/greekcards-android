package ag.greekcards.utils.collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public final class CollectionUtils {
	public static final Comparator<? super Object> COMPARATOR_BY_TOSTRING = new Comparator<Object>() {
		@Override
		public int compare(Object lhs, Object rhs) {
			return lhs.toString().compareTo(rhs.toString());
		}
	};

	private CollectionUtils() {}
	
	public static <T> List<T> filterByToString(List<T> source, String filter) {
		final List<T> filteredList = new ArrayList<T>();
		String itemToString;
		String lcaseFilter = StringUtils.lowerCase(filter);
		for (T item : source) {
			itemToString = StringUtils.lowerCase(item.toString());
			if (StringUtils.contains(itemToString, lcaseFilter)) {
				filteredList.add(item);
			}
		}
		
		return filteredList;
	}
}
