package ag.greekcards.utils;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;

public final class ViewUtils {
	private ViewUtils() {}
	
	public static void configureAsClearableEditText(final EditText et) {
		final Drawable x = et.getContext().getResources().getDrawable(android.R.drawable.presence_offline);//your x image, this one from standard android images looks pretty good actually
		x.setBounds(0, 0, x.getIntrinsicWidth(), x.getIntrinsicHeight());
		et.setCompoundDrawables(null, null, et.getText().length() == 0 ? null : x, null);
		
		et.setOnTouchListener(new OnTouchListener() {
		    @Override
		    public boolean onTouch(View view, MotionEvent event) {
		    	EditText et = (EditText)view;
		        if (et.getCompoundDrawables()[2] == null) {
		            return false;
		        }
		        if (event.getAction() != MotionEvent.ACTION_UP) {
		            return false;
		        }
		        if (event.getX() > et.getWidth() - et.getPaddingRight() - x.getIntrinsicWidth()) {
		            et.setText("");
		            et.setCompoundDrawables(null, null, null, null);
		        }
		        return false;
		    }
		});
		
		et.addTextChangedListener(new TextWatcher() {
		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		        et.setCompoundDrawables(null, null, et.getText().toString().equals("") ? null : x, null);
		    }

		    @Override
		    public void afterTextChanged(Editable arg0) {}

		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		});
	}
}
