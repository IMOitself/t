package imo.t;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		final TextEditor textEditor = findViewById(R.id.text_editor);
		final SeekBar seekbar = findViewById(R.id.seekbar);

		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/jetbrainsmonoregular.ttf");
		textEditor.setTypeface(typeface);
		textEditor.setText("this is a text \nfrom the custom view \nwith a custom font");

		seekbar.setMax(1);
		seekbar.setProgress(0);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
				public void onStartTrackingTouch(SeekBar v) {}
				public void onStopTrackingTouch(SeekBar v) {}
				public void onProgressChanged(SeekBar v, int progress, boolean b) {}
			});
    }
}
