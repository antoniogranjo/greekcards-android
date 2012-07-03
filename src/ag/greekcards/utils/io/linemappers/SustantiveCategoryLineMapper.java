package ag.greekcards.utils.io.linemappers;

import org.apache.commons.lang.StringUtils;

import ag.greekcards.model.SustantiveCategory;
import ag.greekcards.utils.io.LineMapper;

public class SustantiveCategoryLineMapper implements LineMapper<SustantiveCategory> {
	@Override
	public SustantiveCategory map(String line) {
		final SustantiveCategory sustantiveCategory = new SustantiveCategory();
		sustantiveCategory.setDescription(StringUtils.capitalize(StringUtils.lowerCase(line)));
		
		return sustantiveCategory;
	}
}
