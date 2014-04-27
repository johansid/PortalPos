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
    private static final String TAG = "StoreInfoFragment";
    
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
        View view = inflater.inflate(R.layout.store_info_fragment, container, false);
        mStoreNoEditor = (EditText) view.findViewById(R.id.store_No_editor);
        mCustomerNameEditor = (EditText) view.findViewById(R.id.customer_name_editor);
        mTerminalNoEditor = (EditText) view.findViewById(R.id.terminal_No_editor);
        mInventoryRowCountEditor = (EditText) view.findViewById(R.id.inventory_row_count_editor);
        
        mSave = (Button) view.findViewById(R.id.save);
        
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
