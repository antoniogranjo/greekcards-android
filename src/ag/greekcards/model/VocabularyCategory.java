package ag.greekcards.model;

import ag.greekcards.model.base.IdDescElement;
import android.os.Parcel;
import android.os.Parcelable;

public class VocabularyCategory extends IdDescElement implements Parcelable {
	public static final VocabularyCategory CATEGORY_ALL;
	public static final VocabularyCategory NO_CATEGORY;
	public static final Integer CATEGORY_ALL_ID = -1;
	public static final Integer NO_CATEGORY_ID = -2;

	static {
		CATEGORY_ALL = new VocabularyCategory();
		CATEGORY_ALL.setDescription("[Todas las categorías]");
		CATEGORY_ALL.setId(CATEGORY_ALL_ID);

		NO_CATEGORY = new VocabularyCategory();
		NO_CATEGORY.setDescription("[Sin categoría]");
		NO_CATEGORY.setId(NO_CATEGORY_ID);
	}

	public boolean isCategoryAll() {
		return CATEGORY_ALL_ID.equals(getId());
	}

	@Override
	public String toString() {
		return getDescription();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<VocabularyCategory> CREATOR = new Parcelable.Creator<VocabularyCategory>() {
		public VocabularyCategory createFromParcel(Parcel in) {
			VocabularyCategory vc = new VocabularyCategory();
			vc.setId(in.readInt());
			vc.setDescription(in.readString());
			return vc;
		}

		public VocabularyCategory[] newArray(int size) {
			return new VocabularyCategory[size];
		}
	};

	@Override
	public void writeToParcel(Parcel out, int i) {
		out.writeInt(this.getId());
		out.writeString(this.getDescription());
	}
}
