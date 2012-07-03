package ag.greekcards.utils.io;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ag.greekcards.model.SustantiveCategory;
import android.util.AndroidRuntimeException;

public final class IoUtils {
	private IoUtils() {
	}

	public static <T> List<T> map(InputStream inputStream, LineMapper<T> lineMapper) {
		final List<T> results = new ArrayList<T>();
		try {
			final DataInputStream in = new DataInputStream(inputStream);
			final BufferedReader br = new BufferedReader(new InputStreamReader(
					in));

			String strLine;
			while ((strLine = br.readLine()) != null) {
				if (!StringUtils.isEmpty(strLine.trim())) {
					results.add(lineMapper.map(strLine));
				}
			}
			in.close();
		} catch (IOException e) {
			throw new AndroidRuntimeException(e);
		}
		return results;
	}
}
