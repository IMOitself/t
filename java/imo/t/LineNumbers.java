package imo.t;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class LineNumbers extends View {

	private TextEditor mTextEditor;
	private Paint mPaint;
	private int rowAmount;
	private float textSize;
	private float rowHeight;
	private float charWidth;
	int translateY = 0;

    public LineNumbers(Context context) {
		super(context);
	}
	public LineNumbers(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public LineNumbers(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	void linkWithTextEditor(TextEditor textEditor) {
		mTextEditor = textEditor;
		mPaint = mTextEditor.getPaint();
		rowAmount = mTextEditor.getRowAmount();
		textSize = mTextEditor.getTextSize();
		charWidth = mTextEditor.getCharWidth();
		mTextEditor.onTranslateY = new Runnable(){
			@Override
			public void run(){
				translateY = mTextEditor.translateY;
				invalidate();
			}
		};
		
		int digits = String.valueOf(Math.abs(rowAmount)).length();
		resizeWidth((int) ((digits * charWidth) + (charWidth / 2)));
	}

	void resizeWidth(int width) {
		ViewGroup.LayoutParams params = getLayoutParams();
		params.width = width;
		setLayoutParams(params);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mTextEditor == null) return;
		rowHeight = mPaint.getFontSpacing();
		
		canvas.translate(0, -translateY);
		
		float drawTextPoint = 0;
		for (int i = 0; i < rowAmount; i++) {
			drawTextPoint += rowHeight;
			mPaint.setColor(Color.DKGRAY);
			canvas.drawText((i+1) + "", 0, drawTextPoint, mPaint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return super.onTouchEvent(event);
	}
}
