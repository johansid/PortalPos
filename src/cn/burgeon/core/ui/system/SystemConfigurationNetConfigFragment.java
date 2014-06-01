package cn.burgeon.core.ui.system;

import cn.burgeon.core.App;
import cn.burgeon.core.R;
import cn.burgeon.core.utils.PreferenceUtils;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SystemConfigurationNetConfigFragment extends Fragment {
    private static final String TAG = "SystemConfigurationNetConfigFragment";
    
    private EditText URLAddressEditText;
    private String URLAddress;
    private String URLAddressLastInput;
    private Button mServerTest;
    private Button mSystemUpdate;
    private Button mDataDownload;
    private Button mChangePassword;
    private Button mSaveButton;

    static SystemConfigurationNetConfigFragment newInstance() {
        SystemConfigurationNetConfigFragment newFragment = new SystemConfigurationNetConfigFragment();
        return newFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.system_configuration_net_config_fragment, container, false);
        
        URLAddressEditText = (EditText) view.findViewById(R.id.URLAddressEditText);
        mServerTest = (Button) view.findViewById(R.id.serverTestButton);
        mSystemUpdate = (Button) view.findViewById(R.id.systemUpdateConfigButton);
        mDataDownload = (Button) view.findViewById(R.id.dataDownloadButton);
        mChangePassword = (Button) view.findViewById(R.id.changePasswordButton);
        mSaveButton = (Button) view.findViewById(R.id.saveButton);
        
        mServerTest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(SystemConfigurationNetConfigFragment.this.getActivity(), 
						SystemNetTestActivity.class);
				startActivity(intent);	
			}
		});
        
        mSystemUpdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
	
			}
		});
        
        mDataDownload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(SystemConfigurationNetConfigFragment.this.getActivity(), 
						SystemDataDownloadActivity.class);
				startActivity(intent);
			}
		});
        
        mChangePassword.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
	
			}
		});
        
        mSaveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkToSave();
			}
		});
        return view;
    }

    private void checkToSave(){

    	getInputData();

    	if(noInput()){
			showTips(R.string.tipsNoInputData);
    		return;
    	}
    	if(sameData()){
    		showTips(R.string.tipsInputDataNotChanged);
    		return;
    	}

    	saveToMemory();

    	saveLastInput();
    }
  

    private boolean sameData(){    	
    	if(saveOnce() && URLAddress.equals(URLAddressLastInput)){
    		return true;
    	}
    	return false;
    }


    private void saveToMemory(){
    	if( !TextUtils.isEmpty(URLAddress)){
    		App.getPreferenceUtils().savePreferenceStr(PreferenceUtils.URLAddressKey, URLAddress);
    	}
    	showTips(R.string.tipsSaveSucess);
    }
    

    private void getInputData(){
    	URLAddress = URLAddressEditText.getText().toString();
    	URLAddress = URLAddress != null ? URLAddress.trim() : "";
    }


    private boolean noInput(){
    	if(TextUtils.isEmpty(URLAddress)){
    		return true;
    	}
    	return false;
    }


    private boolean saveOnce(){
    	return !TextUtils.isEmpty(URLAddress);
    }
    

    private void saveLastInput(){
    	URLAddressLastInput = URLAddress;
    }
    

    private void showTips(int whichTips){
    	LayoutInflater inflater = this.getActivity().getLayoutInflater();

    	View tipsLayout = inflater.inflate(R.layout.inventory_refresh_tips, 
    			(ViewGroup)this.getActivity().findViewById(R.id.inventoryRefreshTipsLayout));
    	TextView tipsText = (TextView) tipsLayout.findViewById(R.id.inventoryRefreshingTipsText);
    	tipsText.setText(whichTips);
    	
    	new AlertDialog.Builder(this.getActivity())
    		.setTitle(getString(R.string.tipsDataDownload))
    		.setView(tipsLayout)
    		.setPositiveButton(getString(R.string.confirm),null)
    		.show();
    	
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
