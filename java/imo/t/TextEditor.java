package imo.t;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;
import java.util.ArrayList;
import java.util.List;

public class TextEditor extends View {

	private Paint mPaint;
	private List<String> rowTexts = new ArrayList<>();
	private float textSize = 22;
	private float rowHeight;
	private float charWidth;
	private RectF cursorRect = new RectF();
	private int cursorCol = 0;
	private int cursorRow = 0;
	private int cursorWidth = 3;
	int translateY = 0;
	private Scroller scroller;
	private float lastY;
	private float initialY;
	private boolean isSwiping = false;
	Runnable onTranslateY;


	public TextEditor(Context context) {
		super(context);
		init(context);
	}
	public TextEditor(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public TextEditor(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
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

	float getCharWidth() {
		return charWidth;
	}

	List<String> getLines() {
		return rowTexts;
	}

	Paint getPaint() {
		return mPaint;
	}

	int getRowAmount() {
		return rowTexts.size();
	}

	float getRowHeight() {
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





	void init(Context context) {
		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		this.setTextSize(textSize);
		scroller = new Scroller(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		rowHeight = mPaint.getFontSpacing();

		canvas.translate(0, -translateY);

		cursorRect.left = (cursorCol * charWidth) - charWidth;
		cursorRect.top = (cursorRow * rowHeight) - rowHeight;
		if (cursorRect.left < 0) cursorRect.left = 0;
		if (cursorRect.top < 0) cursorRect.top = 0;
		cursorRect.right = cursorRect.left + cursorWidth;
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
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			if (! scroller.isFinished()) scroller.abortAnimation();
			initialY = event.getY();
			lastY = event.getY();
			isSwiping = false;
			return true;
		}
		if (action == MotionEvent.ACTION_MOVE) {
			if (!isSwiping && Math.abs(event.getY() - initialY) < 10) 
				return true;
				
			isSwiping = true;
			float maxScroll = rowHeight * rowTexts.size();
			float distance = event.getY() - lastY;

			translateY -= distance;
			translateY = (int) Math.max(0, Math.min(translateY, maxScroll));
			invalidate();
			lastY = event.getY();

			if (onTranslateY != null) 
				onTranslateY.run();
			return true;
		}
		if (action == MotionEvent.ACTION_UP) {
			if (isSwiping) return true;
			// CLICK LOGIC
			cursorCol = (int) (event.getX() / charWidth + 0.5f) + 1;
			cursorRow = (int) ((event.getY() + translateY) / rowHeight) + 1;
			invalidate();
			return true;
		}
		return true;
	}

	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			translateY = scroller.getCurrY();
			postInvalidate();
		}
	}
}
