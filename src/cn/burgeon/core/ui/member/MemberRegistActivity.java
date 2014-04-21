package cn.burgeon.core.ui.member;

import cn.burgeon.core.R;
import cn.burgeon.core.bean.Member;
import cn.burgeon.core.ui.BaseActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MemberRegistActivity extends BaseActivity {
	
	Button saveBtn,veryfiyBtn;
	EditText cardNOET,nameET,identityET,employeeET;
	EditText createDateET,mobilePhoneET,birthdayET;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupFullscreen();
		setContentView(R.layout.activity_member_regist);
		
		init();
	}
	
	private void init() {
		saveBtn = (Button) findViewById(R.id.memberRegistSaveBtn);
		veryfiyBtn = (Button) findViewById(R.id.memberRegistVerifyBtn);
		cardNOET = (EditText) findViewById(R.id.memberRegistCardNumET);
		nameET = (EditText) findViewById(R.id.memberRegistCardNameET);
		identityET = (EditText) findViewById(R.id.memberRegistIdentityNumET);
		createDateET = (EditText) findViewById(R.id.memberRegistCreateDateET);
		mobilePhoneET = (EditText) findViewById(R.id.memberRegistPhoneNumET);
		birthdayET = (EditText) findViewById(R.id.memberRegistBirthdayET);
		
		saveBtn.setOnClickListener(mOnclickListener);
		veryfiyBtn.setOnClickListener(mOnclickListener);
	}
	
	OnClickListener mOnclickListener = new OnClickListener() {
		
		/* "(_id INTEGER PRIMARY KEY AUTOINCREMENT, cardno VARCHAR,"+
					"name VARCHAR, sex VARCHAR,idno VARCHAR,mobile VARCHAR,birthday VARCHAR"+
	                "employee VARCHAR,email VARCHAR,createTime VARCHAR)");*/
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.memberRegistSaveBtn:
				/*Member m = new Member(); 
				m.setCardNum(cardNOET.getText().toString().trim());
				m.setName(nameET.getText().toString().trim());
				m.setiDentityCardNum(identityET.getText().toString().trim());
				m.setPhoneNum(mobilePhoneET.getText().toString().trim());
				db.beginTransaction();
				db.execSQL("insert into c_vip('cardno','name','idno','mobile') values(?,?,?,?)",
						new Object[]{cardNOET.getText().toString().trim(),
									nameET.getText().toString().trim(),
									identityET.getText().toString().trim(),
									mobilePhoneET.getText().toString().trim()});
				db.endTransaction();*/
				save();
				break;
			case R.id.memberRegistVerifyBtn:
				query();
				break;
			default:
				break;
			}
		}
	};
	
	public void save(){
		db.beginTransaction();
        try {  
        	db.execSQL("insert into c_vip('cardno','name','idno','mobile') values(?,?,?,?)",
					new Object[]{cardNOET.getText().toString().trim(),
								nameET.getText().toString().trim(),
								identityET.getText().toString().trim(),
								mobilePhoneET.getText().toString().trim()});
            db.setTransactionSuccessful();
        } finally {  
            db.endTransaction();
        } 
	}
	
	public void query(){
		Cursor c = db.rawQuery("select * from c_vip", null);
		while(c.moveToNext()){
			Log.d("zhang.h", c.getString(c.getColumnIndex("cardno")));
			Log.d("zhang.h", c.getString(c.getColumnIndex("name")));
			Log.d("zhang.h", c.getString(c.getColumnIndex("idno")));
			Log.d("zhang.h", c.getString(c.getColumnIndex("mobile")));
		}
	}
}
