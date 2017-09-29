package com.gaurav.android.newsfolo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by rawal's on 09-Jul-17.
 */

public class CategoryAdapter extends FragmentPagerAdapter {
    private Context mContext;
    public CategoryAdapter(Context context, FragmentManager fm){
        super(fm);
        mContext = context;
    }
    public Fragment getItem(int position){
        switch (position){
            case 0: return new HomeFragment();
            case 1: return new EntertainmentFragment();
            case 2: return new SportsFragment();
            case 3: return new EducationFragment();
            case 4: return new PoliticsFragment();
            default: return new HomeFragment();
        }
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 5;
    }

    /**
     * This method may be called by the ViewPager to obtain a title string
     * to describe the specified page. This method may return null
     * indicating no title for this page. The default implementation returns
     * null.
     *
     * @param position The position of the title requested
     * @return A title for the requested page
     */
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return mContext.getString(R.string.category_home);
            case 1: return mContext.getString(R.string.category_entertainment);
            case 2: return mContext.getString(R.string.category_sports);
            case 3: return mContext.getString(R.string.category_education);
            case 4: return mContext.getString(R.string.category_politics);
            default: return mContext.getString(R.string.category_home);
        }
    }
}
