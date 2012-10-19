/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband3 for more book information.
***/
package edu.neu.madcourse.adamgressen.persistentboggle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import edu.neu.madcourse.adamgressen.R;
import edu.neu.mobileclass.apis.KeyValueAPI;

public class PersistentBoggle extends Activity implements OnClickListener {
	private static final String BOARD_PREFS = "persistent_board-prefs";
	private static final String STORED_BOARD = "board";
	private static final String SCORE_KEY = "score";
	private static final String USED_WORDS_KEY = "used-words";
	private static final String TIME_KEY = "time";
	
	private static final String USER_PREFS = "persistent_user_prefs";
	private static final String USER_NAME = "name";
	
	private static final String TEAM = "persistence";
	private static final String PASSWORD = "p3rs1st3nc3";
	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.persistent_boggle_main);

      // Set up click listeners for all the buttons
      View continueButton = findViewById(R.id.continue_button);
      continueButton.setOnClickListener(this);
      View newButton = findViewById(R.id.new_button);
      newButton.setOnClickListener(this);
      View rulesButton = findViewById(R.id.rules_button);
      rulesButton.setOnClickListener(this);
      View aboutButton = findViewById(R.id.about_button);
      aboutButton.setOnClickListener(this);
      View acknowledgementsButton = findViewById(R.id.acknowledgements_button);
      acknowledgementsButton.setOnClickListener(this);
      View exitButton = findViewById(R.id.exit_button);
      exitButton.setOnClickListener(this);
   }

   @Override
   protected void onResume() {
      super.onResume();
      PersistentBoggleMusic.playMusic(this, R.raw.main);
   }

   @Override
   protected void onPause() {
      super.onPause();
      PersistentBoggleMusic.stop(this);
   }

   public void onClick(View v) {
      switch (v.getId()) {
      case R.id.continue_button:
         startGame();
         break;
      case R.id.about_button:
    	  new AlertDialog.Builder(this)
			.setMessage(R.string.persistent_about_text)
			.setTitle(R.string.persistent_about_title)
			.setCancelable(true)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {  
				public void onClick(DialogInterface dialog, int id) {  
					dialog.dismiss();
				}
			}).show();
         break;
      case R.id.acknowledgements_button:
    	  new AlertDialog.Builder(this)
			.setMessage(R.string.persistent_acknowledgements)
			.setTitle(R.string.acknowledgements_label)
			.setCancelable(true)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {  
				public void onClick(DialogInterface dialog, int id) {  
					dialog.dismiss();
				}
			}).show();
    	  break;
      case R.id.rules_button:
    	  new AlertDialog.Builder(this)
			.setMessage(R.string.persistent_rules)
			.setTitle(R.string.rules_label)
			.setCancelable(true)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {  
				public void onClick(DialogInterface dialog, int id) {  
					dialog.dismiss();
				}
			}).show();
    	  break;
      case R.id.new_button:
         openNewGameDialog();
         break;
      case R.id.exit_button:
         finish();
         break;
      }
   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      super.onCreateOptionsMenu(menu);
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.persistent_boggle_menu, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.settings:
         startActivity(new Intent(this, PersistentBogglePrefs.class));
         return true;
      }
      return false;
   }

   /** Ask the user what difficulty level they want */
   private void openNewGameDialog() {
	   new AlertDialog.Builder(this)
	   		.setTitle(R.string.new_game_title)
	   		.setMessage(R.string.new_game_message)
	   		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	   			public void onClick(DialogInterface arg0, int arg1) {
	   				clearGamePrefs();
	   				startGame();
	   			}
	   		})
	   		.setNegativeButton("No", new DialogInterface.OnClickListener() {
	   			public void onClick(DialogInterface arg0, int arg1) {
	   			}
	   		})
	   .show();
   }

   /** Start a new game */
   private void startGame() {
      Intent intent = new Intent(this, PersistentBoggleGame.class);
      startActivity(intent);
   }
   
   /** Methods for modifying preferences locally and on the server */
   
   // Clear game preferences for new game creation
   public void clearGamePrefs() {
	   getSharedPreferences(BOARD_PREFS, MODE_PRIVATE).edit().clear().commit();
	   // Clear all stored board prefs
	   /*KeyValueAPI.clearKey(TEAM, PASSWORD, STORED_BOARD);
	   KeyValueAPI.clearKey(TEAM, PASSWORD, SCORE_KEY);
	   KeyValueAPI.clearKey(TEAM, PASSWORD, USED_WORDS_KEY);
	   KeyValueAPI.clearKey(TEAM, PASSWORD, TIME_KEY);*/
   }
   
   public void setPref(String key, String val) {
	   getSharedPreferences(BOARD_PREFS, MODE_PRIVATE).edit().putString(key, val);
   }
   public void setPref(String key, int val) {
	   getSharedPreferences(BOARD_PREFS, MODE_PRIVATE).edit().putInt(key, val);
   }
   public void setPref(String key, boolean val) {
	   getSharedPreferences(BOARD_PREFS, MODE_PRIVATE).edit().putBoolean(key, val);
   }
   
   public String getPref(String key, String defVal) {
	   return getSharedPreferences(BOARD_PREFS, MODE_PRIVATE).getString(key, defVal);
   }
   public int getPref(String key, int defVal) {
	   return getSharedPreferences(BOARD_PREFS, MODE_PRIVATE).getInt(key, defVal);
   }
   public boolean getPref(String key, boolean defVal) {
	   return getSharedPreferences(BOARD_PREFS, MODE_PRIVATE).getBoolean(key, defVal);
   }
}