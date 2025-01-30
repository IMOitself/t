package imo.t;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
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
		textEditor.setText("this is a text from the custom view with a custom font");
		
		seekbar.setMax(100);
		seekbar.setProgress((int) textEditor.textSize);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			public void onStartTrackingTouch(SeekBar v){}
			public void onStopTrackingTouch(SeekBar v){}
			
			@Override
			public void onProgressChanged(SeekBar v, int progress, boolean b){
				textEditor.setTextSize(progress);
				textEditor.setText(progress + " <- This is the text size of this custom view");
			}
		});
    }
}
