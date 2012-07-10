package ag.greekcards.model;

import ag.greekcards.model.base.IdElement;
import android.os.Parcel;
import android.os.Parcelable;

public class VocabularyEntry extends IdElement implements Parcelable {
	private String spanishText;
	private String greekText;
	private int categoryId;

	public static final Parcelable.Creator<VocabularyEntry> CREATOR = new Parcelable.Creator<VocabularyEntry>() {
		public VocabularyEntry createFromParcel(Parcel in) {
			final VocabularyEntry s = new VocabularyEntry();
			s.setId(in.readInt());
			s.setSpanishText(in.readString());
			s.setGreekText(in.readString());
			s.setCategoryId(in.readInt());
			return s;
		}

		public VocabularyEntry[] newArray(int size) {
			return new VocabularyEntry[size];
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
		parcel.writeInt(getCategoryId());
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
}
