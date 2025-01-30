package imo.t;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class TextEditor extends View {

	static Paint mPaint;
	static String content = "";
	static float textSize = 30;

	public TextEditor(Context context) {
		super(context);
		init();
	}
	public TextEditor(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public TextEditor(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	void setText(String string) {
		content = string;
		invalidate();
	}
	
	void setTextSize(float size) {
		textSize = size;
		mPaint.setTextSize(textSize);
		invalidate();
	}

	void setTypeface(Typeface typeface) {
		mPaint.setTypeface(typeface);
		invalidate();
	}



	void init() {
		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		mPaint.setTextSize(textSize);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawText(content, 0, getHeight() / 2, mPaint);
	}
}
