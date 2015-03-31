package jp.dormir.test;

/*�w���p�[�ł̃e�[�u����`
 id ���j�[�NID
 name ���O
 class ���
 DATE ���t*/
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
	boolean home_SEL = true; // ��ʂ̈Ⴂ�ŋ�����ς��邽�߂̃t���O

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// requestWindowFeature(Window.FEATURE_NO_TITLE); //�Ȃ��������Ȃ�
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
				ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.action_bar);
		LinearLayout l1 = (LinearLayout) findViewById(R.id.linearLayout1);
		LinearLayout l2 = (LinearLayout) findViewById(R.id.linearLayout2);
		LinearLayout l3 = (LinearLayout) findViewById(R.id.linearLayout3);

		setContentView(R.layout.activity_home);
		// Button b1 = (Button) findViewById(R.id.button1); //�e�X�g�Ŏg�p�����{�^��
		l1.setOnClickListener(this);
		l2.setOnClickListener(this);
		l3.setOnClickListener(this);
		// b1.setOnClickListener(this);�@//�e�X�g�Ŏg�p�����{�^��

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

	// �S�~//
	/*
	 * public void focusableViewAvailable(View v) { switch (v.getId()) { case
	 * R.id.linearLayout1: Toast.makeText(this, "�t�H�[�J�X����܂���", 1).show(); break;
	 * }
	 * 
	 * }
	 */

	@Override
	public void onClick(View v) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch (v.getId()) {
		case R.id.linearLayout1:
			Toast.makeText(this, "l1��������܂���", 1).show();
			setContentView(R.layout.activity_home);
			home_SEL = true;
			// ������x�C���X�^���X�������X�i�[��L���ɂ���
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
			Toast.makeText(this, "l2��������܂���", 1).show();
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
			Toast.makeText(this, "l3��������܂���", 1).show();

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
		 * �@//�e�X�g�Ŏg�p�����{�^�� case R.id.button1: Toast.makeText(this,
		 * "button1��������܂���", 1).show(); // LinearLayout l4 = (LinearLayout) //
		 * findViewById(R.id.LinearLayout4); View view = getLayoutInflater()
		 * .inflate(R.layout.activity_sub, null); // l4.addView(view);
		 * 
		 * TextView text = (TextView) findViewById(R.id.textView4);
		 * text.setId(R.id.textView4 + 1); text.setText("test");
		 * 
		 * break;
		 */
		default:
			Toast.makeText(this, "���m�F��ID��������Ă��܂�", 1).show();

		}
	}

	// DB�C���^�[�t�F�[�X�擾
	SQLiteDatabase getDb() {
		MyDbHelper helper = new MyDbHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		return db;
	}

	// ���ʕ\��
	public void clearResult() {
		TextView textView = (TextView) findViewById(R.id.result);
		textView.setText("");
	}

	public void addResult(String result) {
		TextView textView = (TextView) findViewById(R.id.result);
		textView.setText(textView.getText().toString() + result + "\n");
	}

	// �I�𒆂̃��R�[�h��ID���擾
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
		// insert���\�b�h��
		try {
			EditText editItemName = (EditText) findViewById(R.id.editText1);
			EditText editDATE = (EditText) findViewById(R.id.editText2);
			ToggleButton itemClass = (ToggleButton) findViewById(R.id.toggleButton1);
			// String�ɕϊ�
			String itemname = editItemName.getText().toString();
			String itemDATE = editDATE.getText().toString();
			String Str_itemClass = itemClass.getText().toString();
			// ���̓{�b�N�X�̒��g�̊m�F
			if (itemname.equals("") || itemDATE.equals("")
					|| itemDATE.length() != 4) {
				addResult("���͂���Ă��Ȃ����ځA�܂��͓��t�̎w�肪�s���ł�(insert)");
				return;

			}
			// ���t�̕����AMM-DD�^�ɂ���
			try {
				month = itemDATE.substring(0, 2);
				day = itemDATE.substring(2, 4);
				itemDATE = month + "-" + day;
			} catch (Exception ex) {
				addResult(ex.getMessage());
			}
			// DB�ɑ}��
			ContentValues values = new ContentValues();
			values.put("name", itemname);
			values.put("DATE", itemDATE);
			values.put("class", Str_itemClass);
			db.insert("shokuhin_data", // �e�[�u����
					null, // �f�[�^��}������ۂ�null�l��������Ă��Ȃ��J�����ɑ���ɗ��p�����l
					values // �l�Q
			);
			addResult("INSERT ����");
		} catch (Exception ex) {
			addResult("INSERT ���s: " + ex.getMessage());
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

		// query���\�b�h��
		try {
			//���t�v�Z�i�������j�������A�����܂��������̓��삪�s���S
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf1 = new SimpleDateFormat("MMDD");
			// ���݂̌���������
			int month1 = calendar.get(calendar.MONTH) + 1;
			int day1 = calendar.get(calendar.DATE);
			String test = sdf1.format(month1 + day1);
			// �����̌���������
			calendar.add(calendar.DAY_OF_MONTH, 2);
			int month2 = calendar.get(calendar.MONTH) + 1;
			int day2 = calendar.get(calendar.DATE);
			// �����񌋍�������ɐ����ɂ���B4���܂���3���̐����ɂ��Ȃ���3/31��4/1�Ȃǂ̌J�z�̌v�Z�����Ȃ���
			String str_daymonth1 = Integer.toString(month1)
					+ Integer.toString(day1);
			String str_daymonth2 = Integer.toString(month2)
					+ Integer.toString(day2);

			mData.clear();
			Cursor c = db.query("shokuhin_data", // �e�[�u����
					new String[] { "_id", "name", "class", "DATE" }, // �I������J�����Q
					null, // selection
					null, // selectionArgs
					null, // group by
					null, // having
					null // order by
					);

			while (c.moveToNext()) {
				int id = c.getInt(c.getColumnIndex("_id")); // �� c.getString(0)
				String month;
				String day;
				String name = c.getString(c.getColumnIndex("name")); // ��
																		// c.getString(1)
				String Class = c.getString(c.getColumnIndex("class"));// �Ɠ���
				String date = c.getString(c.getColumnIndex("DATE"));
				// ���t�̕�������(MM-DD��String�Ŋi�[���Ă邾��������)
				try { // ���͕��������`�F�b�N���Ă��邪���S�ł͂Ȃ��B
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
					mData.add(id + ":" + name + " �@�@�@" + "��ށF" + Class + "  �@�@"
							+ "�ܖ������F" + month + "��" + day + "��");
				}*/
				
				mData.add(id + ":" + name + " �@�@�@" + "��ށF" + Class + "  �@�@"
						+ "�ܖ������F" + month + "��" + day + "��");

				// addResult(id + ":" + body);
				

			}
			addResult("SELECT ����");
		} catch (Exception ex) {
			addResult("SELECT ���s: " + ex.getMessage());
		} finally {
			mAdapter.notifyDataSetChanged();
		}
	}

	public void buttonMethodUpdate(View v) {
		clearResult();
		SQLiteDatabase db = getDb();

		// �Ώۃ��R�[�hID
		int id = getSelectedRecordId();
		if (id == -1) {
			addResult("���ڂ��I������Ă��܂���");
			return;
		}

		// update���\�b�h��
		try {
			EditText editItemName = (EditText) findViewById(R.id.editText1);
			EditText editDATE = (EditText) findViewById(R.id.editText2);
			ToggleButton itemClass = (ToggleButton) findViewById(R.id.toggleButton1);
			// String�ɕϊ�
			String itemname = editItemName.getText().toString();
			String itemDATE = editDATE.getText().toString();
			String Str_itemClass = itemClass.getText().toString();
			if (itemname.equals("") || itemDATE.equals("")
					|| itemDATE.length() != 4) {
				addResult("���͂���Ă��Ȃ����ځA�܂��͓��t�̎w�肪�s���ł�(UPDATE)");
				return;

			}

			ContentValues values = new ContentValues();
			values.put("name", itemname);
			values.put("DATE", itemDATE);
			values.put("class", Str_itemClass);
			db.update("shokuhin_data", // �e�[�u����
					values, // �l�Q�B
					"_id = ?", // �����B
					new String[] { String.valueOf(id) } // where args
			);
			addResult("UPDATE ����");
		} catch (Exception ex) {
			addResult("UPDATE ���s: " + ex.getMessage());
		}
		select();
	}

	public void buttonMethodDelete(View v) {
		clearResult();
		SQLiteDatabase db = getDb();

		// �Ώۃ��R�[�hID
		int id = getSelectedRecordId();
		if (id == -1) {
			addResult("���ڂ��I������Ă��܂���");
			return;
		}

		// delete���\�b�h��
		try {
			db.delete("shokuhin_data", // �e�[�u����
					"_id = ?", // �����B
					new String[] { String.valueOf(id) } // where args (?�̒��̐���)
			);
			addResult("DELETE ����");
		} catch (Exception ex) {
			addResult("DELETE ���s: " + ex.getMessage());
		}
		select();
	}

}
