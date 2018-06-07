package com.cn.lottery.leeyybssc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 定义适配器
 * 
 * @author trinea.cn 2012-11-15
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String>   titleList;

    public ViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList, List<String> titleList){
        super(fragmentManager);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    /**
     * 得到每个页面
     */
    @Override
    public Fragment getItem(int arg0) {
        return (fragmentList == null || fragmentList.size() == 0) ? null : fragmentList.get(arg0);
    }

    /**
     * 每个页面的title
     */
    @Override
    public CharSequence getPageTitle(int position) {
    	if (titleList == null) {
			return "";
		}
        return (titleList.size() > position) ? titleList.get(position) : "";
    }

    /**
     * 页面的总个数
     */
    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }
    
    
    private int mChildCount = 0;

    

    @Override

    public void notifyDataSetChanged() {         

          mChildCount = getCount();

          super.notifyDataSetChanged();

    }



    @Override

    public int getItemPosition(Object object)   {          

          if ( mChildCount > 0) {

          mChildCount --;

          return POSITION_NONE;

          }

          return super.getItemPosition(object);

    }
    
    
}
