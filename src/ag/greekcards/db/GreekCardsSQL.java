package ag.greekcards.db;

import ag.greekcards.model.Verb;
import ag.greekcards.model.VerbEntry;
import ag.greekcards.model.VocabularyEntry;
import ag.greekcards.model.enums.VerbForm;
import ag.greekcards.model.enums.VerbPerson;
import android.content.ContentValues;
import android.provider.BaseColumns;

public final class GreekCardsSQL {
	public static final String DATABASE_NAME = "greekcards.sqlite";
	public static final int DATABASE_VERSION = 2;
	public static final String FILTER_BY_ID = BaseColumns._ID + " = ?";
	
	public static final class VocabularyEntries implements BaseColumns {
		public static final String _GREEK_TEXT = "txt_gr";
		public static final String _SPANISH_TEXT = "txt_es";
		public static final String _CATEGORY_ID = "category_id";
		public static final String TABLE_NAME = "vocabulary_entries";
		public static final String INDEX_NAME = "vocabulary_entries_categ_idx";
		
		public static final String[] QUERY_COLS = new String[] {_ID, _GREEK_TEXT, _SPANISH_TEXT, _CATEGORY_ID};
		public static final String SELECTION_BY_CATEGORY_ID = _CATEGORY_ID + " = ?";
		public static final String SELECTION_BY_ID = _ID + " = ?";
		
		public static ContentValues getInsertContentValues(VocabularyEntry s) {
			final ContentValues values = new ContentValues();
			values.put(_GREEK_TEXT, s.getGreekText());
			values.put(_SPANISH_TEXT, s.getSpanishText());
			values.put(_CATEGORY_ID, s.getCategoryId());
			return values;
		}

		public static ContentValues getUpdateContentValues(VocabularyEntry s) {
			final ContentValues values = new ContentValues();
			values.put(_GREEK_TEXT, s.getGreekText());
			values.put(_SPANISH_TEXT, s.getSpanishText());
			values.put(_CATEGORY_ID, s.getCategoryId());
			return values;
		}
		
		private VocabularyEntries() {}
	}
	
	public static final class VocabularyCategories implements BaseColumns {
		public static final String _DESCRIPTION = "desc";
		public static final String TABLE_NAME = "vocabulary_categories";
		public static final String[] QUERY_COLS = new String[]{_ID, _DESCRIPTION};
		
		private VocabularyCategories() {}
	}
	
	public static final class Verbs implements BaseColumns {
		public static final String _SPANISH_TEXT = "txt_es";
		public static final String TABLE_NAME = "verbs";
		
		public static ContentValues getInsertContentValues(Verb v) {
			final ContentValues values = new ContentValues();
			values.put(_SPANISH_TEXT, v.getSpanishText());
			return values;
		}
		
		private Verbs() {}
	}
	
	public static final class VerbInfinitives implements BaseColumns {
		public static final String _SPANISH_TEXT = "txt_es";
		public static final String _GREEK_TEXT = "txt_gr";
		public static final String VIEW_NAME = "verb_infinitives";
		public static final String[] QUERY_COLS = new String[]{_ID, _GREEK_TEXT, _SPANISH_TEXT};
		
		private VerbInfinitives() {}
	}
	
	public static final class VerbEntries implements BaseColumns {
		public static final String _VERB_ID = "verb_id";
		public static final String _GREEK_TEXT = "txt_gr";
		public static final String _FORM = "form";
		public static final String _PERSON = "person";
		public static final String TABLE_NAME = "verb_entries";
		public static final String[] QUERY_COLS = new String[]{_ID, _GREEK_TEXT, _FORM, _PERSON};
		public static final String SELECTION_BY_VERB_ID = _VERB_ID + " = ?";
		public static final String ORDER_BY_COLUMNS = _FORM + ", " + _PERSON;
		
		public static ContentValues getInsertInfinitiveContentValues(Verb v) {
			final VerbEntry ve = new VerbEntry();
			ve.setVerbId(v.getId());
			ve.setGreekText(v.getGreekText());
			ve.setForm(VerbForm.PRESENT);
			ve.setPerson(VerbPerson.S1);
			return getInsertContentValues(ve);
		}
		
		public static ContentValues getInsertContentValues(VerbEntry v) {
			final ContentValues values = new ContentValues();
			values.put(_VERB_ID, v.getVerbId());
			values.put(_GREEK_TEXT, v.getGreekText());
			values.put(_FORM, v.getForm().getCode());
			values.put(_PERSON, v.getPerson().getCode());
			return values;
		}
		
		private VerbEntries() {}
	}
	
	private GreekCardsSQL() {}
}
