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
	private float textSize = 22;
	private float rowHeight;
	private float charWidth;
	private RectF cursorRect = new RectF();
	private int cursorCol = 0;
	private int cursorRow = 0;
	private int cursorSize = 3;
	int translateY = 0;
	int scrollDamp = 1;
	private float initialY;
	Runnable onTranslateY;
	
	
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

	float getCharWidth(){
		return charWidth;
	}

	List<String> getLines(){
		return rowTexts;
	}

	Paint getPaint(){
		return mPaint;
	}

	int getRowAmount(){
		return rowTexts.size();
	}

	float getRowHeight(){
		return rowHeight;
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

		canvas.translate(0, -translateY);

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
		
		mPaint.setColor(Color.WHITE);
		float drawTextPoint = 0;
		for (String rowText : rowTexts) {
			drawTextPoint += rowHeight;
			canvas.drawText(rowText, 0, drawTextPoint, mPaint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(MotionEvent.ACTION_DOWN == event.getAction()){
			initialY = event.getY(); 
		}
		if(MotionEvent.ACTION_MOVE == event.getAction()) {
			float deltaY = initialY - event.getY();
			translateY += (int) deltaY / scrollDamp;
			if (translateY < 0) translateY = 0;
			if (onTranslateY != null) onTranslateY.run();
			invalidate();
		}
		
		if (true) return true;
		
		// CLICK LOGIC
		cursorCol = (int) (event.getX() / charWidth + 0.5f) + 1;
		cursorRow = (int) Math.floor(event.getY() / rowHeight) + 1;
		invalidate();
		return true;
	}
}
