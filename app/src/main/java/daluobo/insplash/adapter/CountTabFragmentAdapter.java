package daluobo.insplash.adapter;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import daluobo.insplash.R;
import daluobo.insplash.base.view.TabFragmentAdapter;

/**
 * Created by daluobo on 2017/12/29.
 */

public class CountTabFragmentAdapter extends TabFragmentAdapter {
    private Context mContext;
    private List<String> mCounts;

    public CountTabFragmentAdapter(Context context, FragmentManager fm, List<Fragment> fragments, List<String> titles, List<String> counts) {
        super(fm, fragments, titles);
        this.mContext = context;
        this.mCounts = counts;
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_count_tab, null);
        TextView count = view.findViewById(R.id.count);
        TextView title = view.findViewById(R.id.title);

        if (position == 0) {
            count.setGravity(Gravity.LEFT);
            title.setGravity(Gravity.LEFT);
        } else if (position == mTitles.size() - 1) {
            count.setGravity(Gravity.RIGHT);
            title.setGravity(Gravity.RIGHT);
        } else {
            count.setGravity(Gravity.CENTER);
            title.setGravity(Gravity.CENTER);
        }
        count.setText(mCounts.get(position));
        title.setText(mTitles.get(position));
        return view;
    }

    public void setTabSelected(View view, int color){
        TextView count = view.findViewById(R.id.count);
        TextView title = view.findViewById(R.id.title);
        title.setTextColor(color);
        count.setTextColor(color);
    }
}
