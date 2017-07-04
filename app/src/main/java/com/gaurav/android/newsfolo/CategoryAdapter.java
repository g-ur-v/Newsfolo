package com.gaurav.android.newsfolo;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by rawal's on 04-Jul-17.
 */

public class CategoryAdapter extends FragmentPagerAdapter {
    private Context mContext;
    public CategoryAdapter(Context context, FragmentManager fm){
        super(fm);
        mContext = context;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new HomeFragment();
        }else if(position == 1){
            return new EntertainmentFragment();
        }else if (position == 2){
            return new SportsFragment();
        }else if (position ==3){
            return new EducationFragment();
        }else{
            return new PoliticsFragment();
        }
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.category_home);
        } else if (position == 1) {
            return mContext.getString(R.string.category_entertainment);
        } else if (position == 2) {
            return mContext.getString(R.string.category_sports);
        }else if (position == 3){
            return mContext.getString(R.string.category_education);
        } else {
            return mContext.getString(R.string.category_politics);
        }
    }
}
