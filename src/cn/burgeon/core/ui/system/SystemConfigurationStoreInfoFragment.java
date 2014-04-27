package cn.burgeon.core.ui.system;

import cn.burgeon.core.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SystemConfigurationStoreInfoFragment extends Fragment {
    private static final String TAG = "SystemConfigurationStoreInfoFragment";
    
    private EditText mStoreNoEditor;
    private EditText mCustomerNameEditor;
    private EditText mTerminalNoEditor;
    private EditText mInventoryRowCountEditor;
    
    private Button mSave;

    static SystemConfigurationStoreInfoFragment newInstance() {
        SystemConfigurationStoreInfoFragment newFragment = new SystemConfigurationStoreInfoFragment();
        return newFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.system_configuration_store_info_fragment, container, false);
        
        mStoreNoEditor = (EditText) view.findViewById(R.id.storeNoEditText);
        mCustomerNameEditor = (EditText) view.findViewById(R.id.customerNameEditText);
        mTerminalNoEditor = (EditText) view.findViewById(R.id.terminalNoEditText);
        mInventoryRowCountEditor = (EditText) view.findViewById(R.id.inventoryRowCountEditText);
        
        mSave = (Button) view.findViewById(R.id.saveButton);
        
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
