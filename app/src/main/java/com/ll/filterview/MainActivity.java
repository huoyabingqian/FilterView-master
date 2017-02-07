package com.ll.filterview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements FilterView.OnFilterItemClickListener{
    private FilterView mFilterView;
    private TextView mTextView;
    private FilterData mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFilterView = (FilterView) findViewById(R.id.filterView);
        mTextView = (TextView) findViewById(R.id.textView);
        this.initData();
    }

    private void initData() {
        mData = new FilterData();
        mData.setDateData(Arrays.asList("今天","前7天","后7天"));
        mData.setTypeData(Arrays.asList("全部订单","自由健","公开体验课"));
        mData.setPayData(Arrays.asList("未消费","已消费","已过期"));
        mFilterView.setFilterData(mData);
        mFilterView.setOnFilterItemClickListener(this);
    }

    @Override
    public void onFilterItemClick(String content) {
        mTextView.setText(content);
    }
}
