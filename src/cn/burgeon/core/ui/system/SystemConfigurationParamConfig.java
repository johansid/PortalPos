package cn.burgeon.core.ui.system;

import cn.burgeon.core.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SystemConfigurationParamConfig extends Fragment{
    static SystemConfigurationParamConfig newInstance() {
    	SystemConfigurationParamConfig newFragment = new SystemConfigurationParamConfig();
        return newFragment;
    }
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.system_configuration_param_config, null);
	}
}
