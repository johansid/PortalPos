package cn.burgeon.core.ui.system;

import cn.burgeon.core.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SystemConfigurationNetConfigFragment extends Fragment {
    private static final String TAG = "SystemConfigurationNetConfigFragment";
    
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
        
        mServerTest = (Button) view.findViewById(R.id.serverTestButton);
        mSystemUpdate = (Button) view.findViewById(R.id.systemUpdateConfigButton);
        mDataDownload = (Button) view.findViewById(R.id.dataDownloadButton);
        mChangePassword = (Button) view.findViewById(R.id.changePasswordButton);
        mSaveButton = (Button) view.findViewById(R.id.saveButton);
        
        mServerTest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
	
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
	
			}
		});

        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
