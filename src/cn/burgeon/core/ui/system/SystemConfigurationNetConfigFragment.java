package cn.burgeon.core.ui.system;

import cn.burgeon.core.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SystemConfigurationNetConfigFragment extends Fragment {
    private static final String TAG = "NetSettingsFragment";
    
    private Button mServerConnectTest;
    private Button mSystemUpdate;
    private Button mDownload;
    private Button mModifyPassword;
    private Button mSave;

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
        View view = inflater.inflate(R.layout.net_settings_fragment, container, false);
        mServerConnectTest = (Button) view.findViewById(R.id.server_connect_test);
        mSystemUpdate = (Button) view.findViewById(R.id.system_update);
        mDownload = (Button) view.findViewById(R.id.download);
        mModifyPassword = (Button) view.findViewById(R.id.modify_password);
        mSave = (Button) view.findViewById(R.id.save);
        
        mServerConnectTest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
	
			}
		});
        
        mSystemUpdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
	
			}
		});
        
        mDownload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(SystemConfigurationNetConfigFragment.this.getActivity(), SystemConfigurationNetConfigDataDownloadActivity.class);
				startActivity(intent);
			}
		});
        
        mModifyPassword.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
	
			}
		});
        
        mSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
	
			}
		});

        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
