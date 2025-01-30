package imo.t;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class TextEditor extends View {

	private static Paint mPaint;
	private static List<String> rowTexts = new ArrayList<>();
	private static float textSize = 30;
	private static float rowHeight;

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
		rowTexts.clear();
		for (String rowText : string.split("\n")) {
			rowTexts.add(rowText);
		}
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

	float getTextSize() {
		return textSize;
	}



	void init() {
		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		mPaint.setTextSize(textSize);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		rowHeight = mPaint.getFontSpacing();

		float drawTextPoint = 0;
		for (String rowText : rowTexts) {
			drawTextPoint += rowHeight;
			canvas.drawText(rowText, 0, drawTextPoint, mPaint);
		}
	}
}
