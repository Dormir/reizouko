package jp.dormir.test;

/*ヘルパーでのテーブル定義
 id ユニークID
 name 名前
 class 種類
 DATE 日付*/
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.example.test.R;

import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnClickListener {
	ListView mListView;
	ArrayList<String> mData = new ArrayList<String>();
	ArrayAdapter<String> mAdapter;
	int mSelected = -1;
	boolean home_SEL = true; // 画面の違いで挙動を変えるためのフラグ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// requestWindowFeature(Window.FEATURE_NO_TITLE); //なぜか動かない
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
				ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.action_bar);
		LinearLayout l1 = (LinearLayout) findViewById(R.id.linearLayout1);
		LinearLayout l2 = (LinearLayout) findViewById(R.id.linearLayout2);
		LinearLayout l3 = (LinearLayout) findViewById(R.id.linearLayout3);

		setContentView(R.layout.activity_home);
		// Button b1 = (Button) findViewById(R.id.button1); //テストで使用したボタン
		l1.setOnClickListener(this);
		l2.setOnClickListener(this);
		l3.setOnClickListener(this);
		// b1.setOnClickListener(this);　//テストで使用したボタン

		setContentView(R.layout.activity_add);
		// widgets
		mListView = (ListView) findViewById(R.id.listView1);
		// mData.add("AA");
		// mData.add("BB");
		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_checked, mData);
		mListView.setAdapter(mAdapter);
		mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mSelected = position;
			}
		});
		clearResult();
		select();
	}

	// ゴミ//
	/*
	 * public void focusableViewAvailable(View v) { switch (v.getId()) { case
	 * R.id.linearLayout1: Toast.makeText(this, "フォーカスされました", 1).show(); break;
	 * }
	 * 
	 * }
	 */

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		switch (v.getId()) {
		case R.id.linearLayout1:
			Toast.makeText(this, "l1が押されました", 1).show();
			setContentView(R.layout.activity_home);
			home_SEL = true;
			// もう一度インスタンス化しリスナーを有効にする
			mListView = (ListView) findViewById(R.id.listView1);
			mAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_checked, mData);
			mListView.setAdapter(mAdapter);
			mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			mListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					mSelected = position;
				}
			});

			clearResult();
			select();
			break;
		case R.id.linearLayout2:
			Toast.makeText(this, "l2が押されました", 1).show();
			setContentView(R.layout.activity_nakami);
			home_SEL = false;
			mListView = (ListView) findViewById(R.id.listView1);
			mAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_checked, mData);
			mListView.setAdapter(mAdapter);
			mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			mListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					mSelected = position;
				}
			});

			clearResult();
			select();
			break;
		case R.id.linearLayout3:
			Toast.makeText(this, "l3が押されました", 1).show();

			setContentView(R.layout.activity_add);
			home_SEL = false;
			mListView = (ListView) findViewById(R.id.listView1);
			mAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_checked, mData);
			mListView.setAdapter(mAdapter);
			mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			mListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					mSelected = position;
				}
			});
			break;
		/*
		 * 　//テストで使用したボタン case R.id.button1: Toast.makeText(this,
		 * "button1が押されました", 1).show(); // LinearLayout l4 = (LinearLayout) //
		 * findViewById(R.id.LinearLayout4); View view = getLayoutInflater()
		 * .inflate(R.layout.activity_sub, null); // l4.addView(view);
		 * 
		 * TextView text = (TextView) findViewById(R.id.textView4);
		 * text.setId(R.id.textView4 + 1); text.setText("test");
		 * 
		 * break;
		 */
		default:
			Toast.makeText(this, "未確認のIDが押されています", 1).show();

		}
	}

	// DBインターフェース取得
	SQLiteDatabase getDb() {
		MyDbHelper helper = new MyDbHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		return db;
	}

	// 結果表示
	public void clearResult() {
		TextView textView = (TextView) findViewById(R.id.result);
		textView.setText("");
	}

	public void addResult(String result) {
		TextView textView = (TextView) findViewById(R.id.result);
		textView.setText(textView.getText().toString() + result + "\n");
	}

	// 選択中のレコードのIDを取得
	public int getSelectedRecordId() {
		int i = mSelected; // mListView.getSelectedItemPosition();
		if (i >= 0 && i < mData.size()) {
			return Integer.parseInt(mData.get(i).split(":")[0]);
		} else {
			return -1;
		}
	}

	// INSERT
	public void buttonMethodInsert(View v) {
		String month;
		String day;
		clearResult();
		SQLiteDatabase db = getDb();
		// insertメソッド版
		try {
			EditText editItemName = (EditText) findViewById(R.id.editText1);
			EditText editDATE = (EditText) findViewById(R.id.editText2);
			ToggleButton itemClass = (ToggleButton) findViewById(R.id.toggleButton1);
			// Stringに変換
			String itemname = editItemName.getText().toString();
			String itemDATE = editDATE.getText().toString();
			String Str_itemClass = itemClass.getText().toString();
			// 入力ボックスの中身の確認
			if (itemname.equals("") || itemDATE.equals("")
					|| itemDATE.length() != 4) {
				addResult("入力されていない項目、または日付の指定が不正です(insert)");
				return;

			}
			// 日付の分割、MM-DD型にする
			try {
				month = itemDATE.substring(0, 2);
				day = itemDATE.substring(2, 4);
				itemDATE = month + "-" + day;
			} catch (Exception ex) {
				addResult(ex.getMessage());
			}
			// DBに挿入
			ContentValues values = new ContentValues();
			values.put("name", itemname);
			values.put("DATE", itemDATE);
			values.put("class", Str_itemClass);
			db.insert("shokuhin_data", // テーブル名
					null, // データを挿入する際にnull値が許可されていないカラムに代わりに利用される値
					values // 値群
			);
			addResult("INSERT 成功");
		} catch (Exception ex) {
			addResult("INSERT 失敗: " + ex.getMessage());
		}
		select();
	}

	// SELECT
	public void buttonMethodSelect(View v) {
		clearResult();
		select();
	}

	public void select() {
		SQLiteDatabase db = getDb();

		// queryメソッド版
		try {
			//日付計算（未完成）ただし、月をまたいだ時の動作が不完全
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf1 = new SimpleDateFormat("MMDD");
			// 現在の月日を持つ
			int month1 = calendar.get(calendar.MONTH) + 1;
			int day1 = calendar.get(calendar.DATE);
			String test = sdf1.format(month1 + day1);
			// 二日後の月日を持つ
			calendar.add(calendar.DAY_OF_MONTH, 2);
			int month2 = calendar.get(calendar.MONTH) + 1;
			int day2 = calendar.get(calendar.DATE);
			// 文字列結合した後に数字にする。4桁または3桁の数字にしないと3/31と4/1などの繰越の計算が厄介なため
			String str_daymonth1 = Integer.toString(month1)
					+ Integer.toString(day1);
			String str_daymonth2 = Integer.toString(month2)
					+ Integer.toString(day2);

			mData.clear();
			Cursor c = db.query("shokuhin_data", // テーブル名
					new String[] { "_id", "name", "class", "DATE" }, // 選択するカラム群
					null, // selection
					null, // selectionArgs
					null, // group by
					null, // having
					null // order by
					);

			while (c.moveToNext()) {
				int id = c.getInt(c.getColumnIndex("_id")); // ※ c.getString(0)
				String month;
				String day;
				String name = c.getString(c.getColumnIndex("name")); // ※
																		// c.getString(1)
				String Class = c.getString(c.getColumnIndex("class"));// と同じ
				String date = c.getString(c.getColumnIndex("DATE"));
				// 日付の分割処理(MM-DDをStringで格納してるだけだから)
				try { // 入力文字数をチェックしているが安全ではない。
					month = date.substring(0, 2);
					day = date.substring(3, 5);
					
				} catch (Exception ex) {
					addResult(ex.getMessage());
					continue;
				}
				//String test = month + day;
				int testint1 = Integer.parseInt(str_daymonth1);
				int testint2 = Integer.parseInt(str_daymonth2);
				int testint3 = Integer.parseInt(test);
				/*if(testint3 <= testint2 && testint3 == testint1 )
				{
					mData.add(id + ":" + name + " 　　　" + "種類：" + Class + "  　　"
							+ "賞味期限：" + month + "月" + day + "日");
				}*/
				
				mData.add(id + ":" + name + " 　　　" + "種類：" + Class + "  　　"
						+ "賞味期限：" + month + "月" + day + "日");

				// addResult(id + ":" + body);
				

			}
			addResult("SELECT 成功");
		} catch (Exception ex) {
			addResult("SELECT 失敗: " + ex.getMessage());
		} finally {
			mAdapter.notifyDataSetChanged();
		}
	}

	public void buttonMethodUpdate(View v) {
		clearResult();
		SQLiteDatabase db = getDb();

		// 対象レコードID
		int id = getSelectedRecordId();
		if (id == -1) {
			addResult("項目が選択されていません");
			return;
		}

		// updateメソッド版
		try {
			EditText editItemName = (EditText) findViewById(R.id.editText1);
			EditText editDATE = (EditText) findViewById(R.id.editText2);
			ToggleButton itemClass = (ToggleButton) findViewById(R.id.toggleButton1);
			// Stringに変換
			String itemname = editItemName.getText().toString();
			String itemDATE = editDATE.getText().toString();
			String Str_itemClass = itemClass.getText().toString();
			if (itemname.equals("") || itemDATE.equals("")
					|| itemDATE.length() != 4) {
				addResult("入力されていない項目、または日付の指定が不正です(UPDATE)");
				return;

			}

			ContentValues values = new ContentValues();
			values.put("name", itemname);
			values.put("DATE", itemDATE);
			values.put("class", Str_itemClass);
			db.update("shokuhin_data", // テーブル名
					values, // 値群。
					"_id = ?", // 条件。
					new String[] { String.valueOf(id) } // where args
			);
			addResult("UPDATE 成功");
		} catch (Exception ex) {
			addResult("UPDATE 失敗: " + ex.getMessage());
		}
		select();
	}

	public void buttonMethodDelete(View v) {
		clearResult();
		SQLiteDatabase db = getDb();

		// 対象レコードID
		int id = getSelectedRecordId();
		if (id == -1) {
			addResult("項目が選択されていません");
			return;
		}

		// deleteメソッド版
		try {
			db.delete("shokuhin_data", // テーブル名
					"_id = ?", // 条件。
					new String[] { String.valueOf(id) } // where args (?の中の数字)
			);
			addResult("DELETE 成功");
		} catch (Exception ex) {
			addResult("DELETE 失敗: " + ex.getMessage());
		}
		select();
	}

}
