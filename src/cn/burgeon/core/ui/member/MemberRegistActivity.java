package cn.burgeon.core.ui.member;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import cn.burgeon.core.App;
import cn.burgeon.core.R;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.ui.sales.SalesNewOrderActivity;
import cn.burgeon.core.utils.PreferenceUtils;
import cn.burgeon.core.widget.UndoBarController;
import cn.burgeon.core.widget.UndoBarStyle;

import com.android.volley.Response;

public class MemberRegistActivity extends BaseActivity {
	
	Button saveBtn,veryfiyBtn;
	EditText cardNOET,nameET,identityET,emailET;
	EditText createDateET,mobilePhoneET,birthdayET;
	Spinner typeSp,employeeSP;
	RadioGroup radioGroup;
	int _id = -1;
	String from = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupFullscreen();
		setContentView(R.layout.activity_member_regist);
		
		init();
		
		Intent intent = getIntent();
		if(intent != null){
			_id = intent.getIntExtra("_id", -1);
			Log.d("MemberRegistActivity", "_id=" +_id);
			if(-1 != _id)
				getMemberInfo();
			from = intent.getStringExtra("from");
			if("search".equals(from)){
				saveBtn.setText(getString(R.string.useNewMember));
			}
		}
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		_id = intent.getIntExtra("_id", -1);
		Log.d("MemberRegistActivity", "_id=" +_id);
		getMemberInfo();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
	
	private void getMemberInfo() {
		query();
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
		birthdayET.setOnClickListener(mOnclickListener);
		radioGroup = (RadioGroup) findViewById(R.id.memberRegistRG);
		emailET = (EditText) findViewById(R.id.memberRegistEmailET);
		employeeSP = (Spinner) findViewById(R.id.memberRegistSalesAssistantSP);
		typeSp = (Spinner) findViewById(R.id.memberRegistVipTypeSP);
		saveBtn.setOnClickListener(mOnclickListener);
		veryfiyBtn.setOnClickListener(mOnclickListener);
	}
	
	OnClickListener mOnclickListener = new OnClickListener() {
	
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.memberRegistSaveBtn:
				
				if(-1 != _id){
					update();
					UndoBarStyle MESSAGESTYLE = new UndoBarStyle(-1, -1, 3000);
		        	UndoBarController.show(MemberRegistActivity.this, "更新会员成功", null, MESSAGESTYLE);
				}else{
					save();
					UndoBarStyle MESSAGESTYLE = new UndoBarStyle(-1, -1, 3000);
		        	UndoBarController.show(MemberRegistActivity.this, "注册会员成功", null, MESSAGESTYLE);
		        	if("search".equals(from)){
		        		forwardActivity(SalesNewOrderActivity.class, "searchedMember",cardNOET.getText()+"\\"+100);
		        	}
				}
				
	        	break;
			case R.id.memberRegistVerifyBtn:
				verify();
				break;
			case R.id.memberRegistBirthdayET:
				Calendar c = Calendar.getInstance();
				 int startmYear = c.get(Calendar.YEAR);
			     int startmMonth = c.get(Calendar.MONTH);
			     int startmDay = c.get(Calendar.DAY_OF_MONTH);
			     DatePickerDialog startdialog = new DatePickerDialog(MemberRegistActivity.this, new startmDateSetListener(), startmYear, startmMonth, startmDay);
			     startdialog.show();
				break;
			default:
				break;
			}
		}

	};
	
    class startmDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            // Month is 0 based so add 1

            String month = String.valueOf(mMonth + 1).length() == 2 ? String.valueOf(mMonth + 1) : "0" + String.valueOf(mMonth + 1);
            String day = String.valueOf(mDay).length() == 2 ? String.valueOf(mDay) : "0" + String.valueOf(mDay);
            birthdayET.setText(new StringBuilder().append(mYear).append(month).append(day));
        }
    }
	

	
	private boolean isRequired(Editable src){
		return "".equals(src) || null == src;
	}
	
	private void validate(){
		if(isRequired(cardNOET.getText())){   
			UndoBarStyle MESSAGESTYLE = new UndoBarStyle(-1, -1, 3000);
        	UndoBarController.show(MemberRegistActivity.this, "卡号不能为空", null, MESSAGESTYLE);
        	return;
		}else if(isRequired(nameET.getText())){
			UndoBarStyle MESSAGESTYLE = new UndoBarStyle(-1, -1, 3000);
        	UndoBarController.show(MemberRegistActivity.this, "姓名不能为空", null, MESSAGESTYLE);
        	return;
		}else if(isRequired(mobilePhoneET.getText())){
			UndoBarStyle MESSAGESTYLE = new UndoBarStyle(-1, -1, 3000);
        	UndoBarController.show(MemberRegistActivity.this, "手机号码不能为空", null, MESSAGESTYLE);
        	return;
		}else if(isRequired(identityET.getText())){
			UndoBarStyle MESSAGESTYLE = new UndoBarStyle(-1, -1, 3000);
        	UndoBarController.show(MemberRegistActivity.this, "身份证号码不能为空", null, MESSAGESTYLE);
        	return;
		}
	}
	
	public void save(){
		validate();
		db.beginTransaction();
        try {
        	db.execSQL("insert into c_vip('cardno','name','idno','mobile','sex','email','birthday','createTime','employee','type')"+
        				" values(?,?,?,?,?,?,?,?,?,?)",
					new Object[]{cardNOET.getText().toString().trim(),
								nameET.getText().toString().trim(),
								identityET.getText().toString().trim(),
								mobilePhoneET.getText().toString().trim(),
								radioGroup.getCheckedRadioButtonId()==R.id.radioMale?getResources().getString(R.string.male):getResources().getString(R.string.female),
								emailET.getText().toString().trim(),
								birthdayET.getText().toString().trim(),
								new SimpleDateFormat("yyyyMMdd").format(new Date()),
								"",
								typeSp.getSelectedItem().toString(),
								});
            db.setTransactionSuccessful();
        } finally {  
            db.endTransaction();
        }
	}
	
	private void update() {
		validate();
		db.beginTransaction();
        try {
        	db.execSQL("update c_vip set 'cardno'=?,'name'=?,'idno'=?,'mobile'=?,'sex'=?,"
        			+ "'email'=?,'birthday'=?,'createTime'=?,'employee'=?,'type'=?"+
        				" where _id = ?",
					new Object[]{cardNOET.getText().toString().trim(),
								nameET.getText().toString().trim(),
								identityET.getText().toString().trim(),
								mobilePhoneET.getText().toString().trim(),
								radioGroup.getCheckedRadioButtonId()==R.id.radioMale?getResources().getString(R.string.male):getResources().getString(R.string.female),
								emailET.getText().toString().trim(),
								birthdayET.getText().toString().trim(),
								new SimpleDateFormat("yyyyMMdd").format(new Date()),
								App.getPreferenceUtils().getPreferenceStr(PreferenceUtils.user_key),
								typeSp.getSelectedItem().toString(),
								_id
								});
            db.setTransactionSuccessful();
        } finally {  
            db.endTransaction();
        }
	}
	
	public void query(){
		Cursor c = db.rawQuery("select * from c_vip where _id = ?", new String[]{String.valueOf(_id)});
		Log.d("zhang.h", "record number::::::::;" + c.getCount());
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
			Log.d("zhang.h", c.getString(c.getColumnIndex("sex")));
			cardNOET.setText(c.getString(c.getColumnIndex("cardno")));
			nameET.setText(c.getString(c.getColumnIndex("name")));
			identityET.setText(c.getString(c.getColumnIndex("idno")));
			mobilePhoneET.setText(c.getString(c.getColumnIndex("mobile")));
			radioGroup.check(getResources().getString(R.string.male).equals(c.getString(c.getColumnIndex("sex")))?R.id.radioMale:R.id.radioFemale);
			emailET.setText(c.getString(c.getColumnIndex("email")));
			birthdayET.setText(c.getString(c.getColumnIndex("birthday")));
			createDateET.setText(c.getString(c.getColumnIndex("createTime")));
		}
	}
	
	private void verify() {
		if(isRequired(cardNOET.getText())){
			UndoBarStyle MESSAGESTYLE = new UndoBarStyle(-1, -1, 3000);
        	UndoBarController.show(MemberRegistActivity.this, "卡号不能为空", null, MESSAGESTYLE);
        	return;
		}
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
