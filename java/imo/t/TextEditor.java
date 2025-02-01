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
import android.graphics.RectF;
import android.view.MotionEvent;

public class TextEditor extends View {

	private Paint mPaint;
	private List<String> rowTexts = new ArrayList<>();
	private float textSize = 50;
	private float rowHeight;
	private float charWidth;
	private RectF cursorRect = new RectF();
	private int cursorCol = 0;
	private int cursorRow = 0;
	private int cursorSize = 3;
	
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

	List<String> getLines(){
		return rowTexts;
	}
	
	void setCursorRow(int cursorRow) {
		this.cursorRow = cursorRow;
	}

	int getCursorRow() {
		return cursorRow;
	}

	void setCursorCol(int cursorCol) {
		this.cursorCol = cursorCol;
	}

	int getCursorCol() {
		return cursorCol;
	}
	
	void setText(String string) {
		this.rowTexts.clear();
		for (String rowText : string.split("\n")) {
			this.rowTexts.add(rowText);
		}
		invalidate();
	}

	void setTextSize(float size) {
		this.textSize = size;
		this.mPaint.setTextSize(textSize);
		this.charWidth = mPaint.measureText(" ");
		invalidate();
	}

	float getTextSize() {
		return textSize;
	}

	void setTypeface(Typeface typeface) {
		this.mPaint.setTypeface(typeface);
		this.charWidth = mPaint.measureText(" ");
		invalidate();
	}
	
	



	void init() {
		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		this.setTextSize(textSize);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		rowHeight = mPaint.getFontSpacing();
		
		cursorRect.left = (cursorCol * charWidth) - charWidth;
		cursorRect.top = (cursorRow * rowHeight) - rowHeight;
		if(cursorRect.left < 0) cursorRect.left = 0;
		if(cursorRect.top < 0) cursorRect.top = 0;
		cursorRect.right = cursorRect.left + cursorSize;
		cursorRect.bottom = cursorRect.top + rowHeight;
		
		mPaint.setColor(Color.DKGRAY);
		canvas.drawRect(0, cursorRect.top, getWidth(), cursorRect.bottom, mPaint);
		mPaint.setColor(Color.GRAY);
		canvas.drawRect(cursorRect, mPaint);
		
		cursorRect.left -= charWidth / 2;
		cursorRect.right = cursorRect.left + charWidth;
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(cursorRect, mPaint);
		
		mPaint.setColor(Color.WHITE);
		mPaint.setStyle(Paint.Style.FILL);
		float drawTextPoint = 0;
		for (String rowText : rowTexts) {
			drawTextPoint += rowHeight;
			canvas.drawText(rowText, 0, drawTextPoint, mPaint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		cursorCol = (int) (event.getX() / charWidth + 0.5f) + 1;
		cursorRow = (int) Math.floor(event.getY() / rowHeight) + 1;
		invalidate();
		return super.onTouchEvent(event);
	}
}
