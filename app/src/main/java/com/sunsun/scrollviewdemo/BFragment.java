package com.sunsun.scrollviewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun on 2016/12/18.
 */
public class BFragment extends Fragment implements AdapterView.OnItemClickListener , ScrollViewListener {

    private View rootView;
    private CustomListView mCustomListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_layout_b, null, false);
        mCustomListView = (CustomListView) rootView.findViewById(R.id.listView_b);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCustomListView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_expandable_list_item_1, getData()));
        mCustomListView.setOnItemClickListener(this);
    }

    private List<String> getData() {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            data.add("测试b" + i);
        }
        return data;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String data = (String) parent.getItemAtPosition(position);
        Toast.makeText(getActivity(), "" + data.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isSlidingTop() {
        if (mCustomListView != null) {
            return mCustomListView.getFirstVisiblePosition() == 0 ? true : false;
        }
        return false;
    }

}
