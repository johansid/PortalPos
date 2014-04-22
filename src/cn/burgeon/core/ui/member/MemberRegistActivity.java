package cn.burgeon.core.ui.member;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;

import cn.burgeon.core.R;
import cn.burgeon.core.bean.Member;
import cn.burgeon.core.ui.BaseActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MemberRegistActivity extends BaseActivity {
	
	Button saveBtn,veryfiyBtn;
	EditText cardNOET,nameET,identityET,employeeET,emailET;
	EditText createDateET,mobilePhoneET,birthdayET,typeET;
	RadioGroup radioGroup;
	
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
		radioGroup = (RadioGroup) findViewById(R.id.memberRegistRG);
		emailET = (EditText) findViewById(R.id.memberRegistEmailET);
		employeeET = (EditText) findViewById(R.id.memberRegistSalesAssistantET);
		typeET = (EditText) findViewById(R.id.memberRegistVipTypeET);
		saveBtn.setOnClickListener(mOnclickListener);
		veryfiyBtn.setOnClickListener(mOnclickListener);
	}
	
	OnClickListener mOnclickListener = new OnClickListener() {
	
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
				verify();
				break;
			default:
				break;
			}
		}

	};
	
	public void save(){
		db.beginTransaction();
        try {
        	db.execSQL("insert into c_vip('cardno','name','idno','mobile','sex','email','birthday','createTime','employee','type')"+
        				" values(?,?,?,?,?,?,?,?,?,?)",
					new Object[]{cardNOET.getText().toString().trim(),
								nameET.getText().toString().trim(),
								identityET.getText().toString().trim(),
								mobilePhoneET.getText().toString().trim(),
								radioGroup.getCheckedRadioButtonId()==R.id.radioMale?1:0,
								emailET.getText().toString().trim(),
								birthdayET.getText().toString().trim(),
								createDateET.getText().toString().trim(),
								employeeET.getText().toString().trim(),
								typeET.getText().toString().trim(),
								});
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
			Log.d("zhang.h", c.getString(c.getColumnIndex("birthday")));
			Log.d("zhang.h", c.getString(c.getColumnIndex("employee")));
			Log.d("zhang.h", c.getString(c.getColumnIndex("email")));
			Log.d("zhang.h", c.getString(c.getColumnIndex("createTime")));
			Log.d("zhang.h", c.getString(c.getColumnIndex("type")));
			Log.d("zhang.h", Integer.toString(c.getInt(c.getColumnIndex("sex"))));
		}
	}
	
	private void verify() {
		Map<String,String> params = new HashMap<String, String>();
		JSONArray array;
		JSONObject transactions;
		try {
			array = new JSONArray();
			transactions = new JSONObject();
			transactions.put("id", 112);
			transactions.put("command", "Query");
			JSONObject paramsInTransactions = new JSONObject();
			paramsInTransactions.put("table", 12899);
			
			//查询条件的params
			JSONObject queryParams = new JSONObject();
			queryParams.put("column", "cardno");
			queryParams.put("condition", cardNOET.getText().toString().trim());
			paramsInTransactions.put("params", queryParams);
			
			transactions.put("params", paramsInTransactions);
			array.put(transactions);
			params.put("transactions", array.toString());
			sendRequest(params,new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					//Log.d("zhang.h", response);
					
				}
			});
		} catch (JSONException e) {}
	}
}
