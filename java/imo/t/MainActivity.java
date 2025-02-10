package imo.t;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.SeekBar;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		final TextEditor textEditor = findViewById(R.id.text_editor);
		final LineNumbers lineNumbers = findViewById(R.id.line_numbers);
		final SeekBar seekbar = findViewById(R.id.seekbar);

		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/jetbrainsmonoregular.ttf");
		textEditor.setTypeface(typeface);
		textEditor.setText(getString(R.string.text));
		textEditor.scrollAxis = TextEditor.ScrollAxis.X;
		
		lineNumbers.linkWithTextEditor(textEditor);

		seekbar.setMax(1);
		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
				public void onStartTrackingTouch(SeekBar v) {}
				public void onStopTrackingTouch(SeekBar v) {}
				public void onProgressChanged(SeekBar v, int progress, boolean b) {
					if(0 == progress)
						textEditor.scrollAxis = TextEditor.ScrollAxis.X;
					if(1 == progress)
						textEditor.scrollAxis = TextEditor.ScrollAxis.Y;
					textEditor.invalidate();
				}
			});
    }
}
