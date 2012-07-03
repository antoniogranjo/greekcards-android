package ag.greekcards.utils.io;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.util.AndroidRuntimeException;

public final class IoUtils {
	private IoUtils() {
	}

	public static <T> List<T> map(String file, LineMapper<T> mapper) {
		final List<T> results = new ArrayList<T>();
		try {
			final FileInputStream fstream = new FileInputStream(file);
			final DataInputStream in = new DataInputStream(fstream);
			final BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String strLine;
			while ((strLine = br.readLine()) != null) {
				results.add(mapper.map(strLine));
			}
			in.close();
		} catch (IOException e) {
			throw new AndroidRuntimeException(e);
		}
		return results;
	}
}
