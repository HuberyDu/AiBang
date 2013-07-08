package edu.jxsd.x3510.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends Activity {
	private EditText searchText;
    private Button searchButton;
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		searchText = (EditText) this.findViewById(R.id.activity_search_et_search);
		searchButton = (Button) this.findViewById(R.id.activity_search_bt_search);
		searchButton.setOnClickListener(new sButtonClickListener());
	}
	class sButtonClickListener implements OnClickListener{

		public void onClick(View v) {
			Intent intent = new Intent(SearchActivity.this, SearchListViewActivity.class);
			startActivity(intent);
		}
	}
	/*public void startSearch(View v) {
		
		
	}*/
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
	}


}
