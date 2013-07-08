package edu.jxsd.x3510.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NameEdit extends Activity{
     private EditText editName;
     private Button button;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_edit);
		
		editName = (EditText) this.findViewById(R.id.personal_name);
		button = (Button) this.findViewById(R.id.personal_name_return);
		button.setOnClickListener(new ButtonOnClickListener());
	}
    private final class ButtonOnClickListener implements View.OnClickListener{

		public void onClick(View v) {
			personalNameReturn(v);
		}

		
    	
    }
    public void personalNameReturn(View v) {
    	Intent intent = new Intent(this, PersonalInformationActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
		
	}
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
	}
     
     
}
