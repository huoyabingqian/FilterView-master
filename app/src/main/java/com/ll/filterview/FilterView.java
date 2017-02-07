package com.ll.filterview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Zlb on 2016/10/14.
 */
public class FilterView extends LinearLayout implements View.OnClickListener,AdapterView.OnItemClickListener{
    // 今天、前一周、后一周
    private LinearLayout llFilterDate;
    // 全部订单、自由健、公开体验课
    private LinearLayout llFilterType;
    // 未消费、已消费、已过期
    private LinearLayout llFilterPay;
    //订单日期、类型、状态文字
    private TextView tvFilterDate;
    private TextView tvFilterType;
    private TextView tvFilterPay;
    //订单日期、类型、状态图标
    private ImageView ivFilterDate;
    private ImageView ivFilterType;
    private ImageView ivFilterPay;
    //菜单滑出时底部背景图
    private View maskView;
    private ListView lvMenu;
    //日期列表 item所在的位置
    // 当前选中的tab
    private int currentTabIndex = -1;
    private int currentDateItemIndex;
    private int currentTypeItemIndex;
    private int currentPayItemIndex;
    private Context mContext;
    private FilterData filterData;
    private OrderFilterAdapter mAdapter;
    private boolean isMenuShow;//下拉列表是否显示
    private int menuHeight;//下拉列表的高度
    private OnFilterItemClickListener onFilterItemClickListener;
    public FilterView(Context context) {
        this(context,null);
    }

    public FilterView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }
    private void init(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.order_tab_item, this);
        this.initView(view);
    }
    private void initView(View view) {
        llFilterDate = (LinearLayout) view.findViewById(R.id.ll_tab_today);
        llFilterType = (LinearLayout) view.findViewById(R.id.ll_tab_whole);
        llFilterPay = (LinearLayout) view.findViewById(R.id.ll_tab_no_pend);

        tvFilterDate = (TextView) view.findViewById(R.id.tv_tab_order);
        tvFilterType = (TextView) view.findViewById(R.id.tv_tab_whole);
        tvFilterPay = (TextView) view.findViewById(R.id.tv_tab_pend);
        ivFilterDate = (ImageView) view.findViewById(R.id.iv_tab_order);
        ivFilterType = (ImageView) view.findViewById(R.id.iv_tab_whole);
        ivFilterPay = (ImageView) view.findViewById(R.id.iv_tab_pend);

        maskView = view.findViewById(R.id.view_mask_bg);
        lvMenu = (ListView) view.findViewById(R.id.lv_menu);

        llFilterDate.setOnClickListener(this);
        llFilterType.setOnClickListener(this);
        llFilterPay.setOnClickListener(this);
        maskView.setOnClickListener(this);
        lvMenu.setOnItemClickListener(this);
    }
    // 设置筛选数据
    public void setFilterData(FilterData filterData) {
        this.filterData = filterData;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_tab_today:
                currentTabIndex = 0;
                setFilterDateAdapter();
                //显示下拉列表
                showMenu();
                break;
            case R.id.ll_tab_whole:
                currentTabIndex = 1;
                setFilterTypeAdapter();
                showMenu();
                break;
            case R.id.ll_tab_no_pend:
                currentTabIndex = 2;
                setFilterPayAdapter();
                showMenu();
                break;
            case R.id.view_mask_bg://点击灰色部分
                hideMenu();
                break;
        }

    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String content = null;
        switch (currentTabIndex){
            case 0:
                currentDateItemIndex = position;
                content = filterData.getDateData().get(position);
                setTabTitle(tvFilterDate, content);
                break;//第一个tab
            case 1:
                currentTypeItemIndex = position;
                content = filterData.getTypeData().get(position);
                setTabTitle(tvFilterType,filterData.getTypeData().get(position));
                break;//第二个tab
            case 2:
                currentPayItemIndex = position;
                content = filterData.getPayData().get(position);
                setTabTitle(tvFilterPay,filterData.getPayData().get(position));
                break;//第三份tab
        }
        if (onFilterItemClickListener != null) {
            onFilterItemClickListener.onFilterItemClick(content);//回调，将item值传出
        }
        hideMenu();
    }
    public void setTabTitle(TextView tvTabTitle,String title){
        tvTabTitle.setText(title);
    }

    /**
     * 显示下拉列表项
     */
    public void showMenu() {
        if(isMenuShow) return;
        isMenuShow = true;
        maskView.setVisibility(VISIBLE);
        lvMenu.setVisibility(View.VISIBLE);
        lvMenu.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                lvMenu.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                menuHeight = lvMenu.getHeight();
                ObjectAnimator.ofFloat(lvMenu, "translationY", -menuHeight, 0).setDuration(200).start();
            }
        });
    }

    /**
     * 隐藏下拉列表项
     */
    public void hideMenu(){
        if(!isMenuShow) return;
        isMenuShow = false;
        maskView.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(lvMenu, "translationY", 0, -menuHeight).setDuration(200).start();
        if(currentTabIndex == 0){
            ivFilterDate.setImageResource(R.mipmap.ic_order_down_select);
        }else if(currentTabIndex == 1){
            ivFilterType.setImageResource(R.mipmap.ic_order_down_select);
        }else if(currentTabIndex == 2){
            ivFilterPay.setImageResource(R.mipmap.ic_order_down_select);
        }
    }
    private void setFilterPayAdapter() {

        tvFilterPay.setTextColor(mContext.getResources().getColor(R.color.tab_text_select));
        ivFilterPay.setImageResource(R.mipmap.ic_order_up_select);
        tvFilterDate.setTextColor(mContext.getResources().getColor(R.color.tab_mine_name));
        ivFilterDate.setImageResource(R.mipmap.ic_order_down_normal);
        tvFilterType.setTextColor(mContext.getResources().getColor(R.color.tab_mine_name));
        ivFilterType.setImageResource(R.mipmap.ic_order_down_normal);
        mAdapter = new OrderFilterAdapter(mContext, filterData.getPayData(),currentPayItemIndex);
        lvMenu.setAdapter(mAdapter);
    }

    private void setFilterTypeAdapter() {
        tvFilterType.setTextColor(mContext.getResources().getColor(R.color.tab_text_select));
        ivFilterType.setImageResource(R.mipmap.ic_order_up_select);
        tvFilterDate.setTextColor(mContext.getResources().getColor(R.color.tab_mine_name));
        ivFilterDate.setImageResource(R.mipmap.ic_order_down_normal);
        tvFilterPay.setTextColor(mContext.getResources().getColor(R.color.tab_mine_name));
        ivFilterPay.setImageResource(R.mipmap.ic_order_down_normal);
        mAdapter = new OrderFilterAdapter(mContext, filterData.getTypeData(),currentTypeItemIndex);
        lvMenu.setAdapter(mAdapter);
    }

    private void setFilterDateAdapter() {
        tvFilterDate.setTextColor(mContext.getResources().getColor(R.color.tab_text_select));
        ivFilterDate.setImageResource(R.mipmap.ic_order_up_select);
        tvFilterType.setTextColor(mContext.getResources().getColor(R.color.tab_mine_name));
        ivFilterType.setImageResource(R.mipmap.ic_order_down_normal);
        tvFilterPay.setTextColor(mContext.getResources().getColor(R.color.tab_mine_name));
        ivFilterPay.setImageResource(R.mipmap.ic_order_down_normal);
        mAdapter = new OrderFilterAdapter(mContext, filterData.getDateData(),currentDateItemIndex);
        lvMenu.setAdapter(mAdapter);
    }

    public interface OnFilterItemClickListener {
        void onFilterItemClick(String content);
    }
    public void setOnFilterItemClickListener(OnFilterItemClickListener onFilterClickListener) {
        this.onFilterItemClickListener = onFilterClickListener;
    }
}
