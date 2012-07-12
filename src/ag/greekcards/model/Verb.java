package ag.greekcards.model;

import ag.greekcards.model.base.IdDescElement;
import android.os.Parcel;
import android.os.Parcelable;

public class Verb extends IdDescElement implements Parcelable {
	private String spanishText;
	private String greekText;
	
	public static final Parcelable.Creator<Verb> CREATOR = new Parcelable.Creator<Verb>() {
		public Verb createFromParcel(Parcel in) {
			Verb verb = new Verb();
			verb.setId(in.readInt());
			verb.setSpanishText(in.readString());
			verb.setGreekText(in.readString());
			return verb;
		}

		public Verb[] newArray(int size) {
			return new Verb[size];
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
	public String toString() {
		return this.spanishText + " / " + this.greekText;
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
