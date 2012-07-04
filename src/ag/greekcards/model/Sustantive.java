package ag.greekcards.model;

import ag.greekcards.model.base.IdElement;
import android.os.Parcel;
import android.os.Parcelable;

public class Sustantive extends IdElement implements Parcelable {
	private String spanishText;
	private String greekText;

	public static final Parcelable.Creator<Sustantive> CREATOR = new Parcelable.Creator<Sustantive>() {
		public Sustantive createFromParcel(Parcel in) {
			final Sustantive s = new Sustantive();
			s.setId(in.readInt());
			s.setSpanishText(in.readString());
			s.setGreekText(in.readString());
			return s;
		}

		public Sustantive[] newArray(int size) {
			return new Sustantive[size];
		}
	};

	public String getSpanishText() {
		return spanishText;
	}

	public void setSpanishText(String spanishText) {
		this.spanishText = spanishText;
	}

	public String getGreekText() {
		return greekText;
	}

	public void setGreekText(String greekText) {
		this.greekText = greekText;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(getId());
		parcel.writeString(getSpanishText());
		parcel.writeString(getGreekText());
	}
}
