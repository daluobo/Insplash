package daluobo.insplash.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import daluobo.insplash.R;
import daluobo.insplash.model.MenuItem;
import daluobo.insplash.util.DimensionUtil;
import daluobo.insplash.util.ViewUtil;
import daluobo.insplash.viewmodel.BasePageViewModel;
import daluobo.insplash.viewmodel.CollectionsViewModel;
import daluobo.insplash.viewmodel.PhotoViewModel;

/**
 * Created by daluobo on 2017/12/17.
 */

public class PopupMenuHelper {
    public static final List<MenuItem> mPhotoType = new ArrayList<>();
    public static final List<MenuItem> mOrderBy = new ArrayList<>();
    public static final List<MenuItem> mCollectionType = new ArrayList<>();

    static {
        mPhotoType.add(new MenuItem("Photos", PhotoViewModel.PhotoType.ALL));
        mPhotoType.add(new MenuItem("Trending", PhotoViewModel.PhotoType.CURATED));

        mOrderBy.add(new MenuItem("Latest", BasePageViewModel.OrderBy.LATEST));
        mOrderBy.add(new MenuItem("Oldest", BasePageViewModel.OrderBy.OLDEST));
        mOrderBy.add(new MenuItem("Popular", BasePageViewModel.OrderBy.POPULAR));

        mCollectionType.add(new MenuItem("Collections", CollectionsViewModel.CollectionType.ALL));
        mCollectionType.add(new MenuItem("Featured", CollectionsViewModel.CollectionType.FEATURED));
        mCollectionType.add(new MenuItem("Curated", CollectionsViewModel.CollectionType.CURATED));
    }

    public static void showPopupMenu(Context context, View anchor,
                                     @LayoutRes int itemLayoutId,
                                     List<MenuItem> menuItems,
                                     MenuItem selectedItem,
                                     final OnMenuItemClickListener listener,
                                     int xOff) {
        int windowWidth;
        final PopupWindow window = new PopupWindow(context);
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View contentView = inflater.inflate(R.layout.menu_container, null, false);
        final LinearLayout container = contentView.findViewById(R.id.container);

        View smi = inflater.inflate(itemLayoutId, null, false);
        TextView sTitle = smi.findViewById(R.id.title);
        sTitle.setText(selectedItem.title);
        sTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        windowWidth = ViewUtil.getViewSize(sTitle)[0];
        smi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        container.addView(smi);

        for (final MenuItem menuItem : menuItems) {
            if (menuItem.value.equals(selectedItem.value)) {
                continue;
            }
            View mi = inflater.inflate(itemLayoutId, null, false);
            final TextView title = mi.findViewById(R.id.title);
            title.setText(menuItem.title);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(menuItem);
                    window.dismiss();
                }
            });
            container.addView(mi);

            int tWidth = ViewUtil.getViewSize(title)[0];
            windowWidth = windowWidth > tWidth ? windowWidth : tWidth;
        }

        window.setContentView(contentView);
        window.setWidth(windowWidth);
        window.setHeight(anchor.getHeight() * menuItems.size());
        window.setOutsideTouchable(true);
        window.setFocusable(true);
        window.setTouchable(true);
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        window.setAnimationStyle(R.style.AlphaAnimation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setElevation(10);
        }

        window.showAsDropDown(anchor, xOff, -anchor.getHeight());
    }

    public static void showPhotoTypeMenu(Context context, View anchor, MenuItem selectedItem, OnMenuItemClickListener listener) {
        showPopupMenu(context, anchor, R.layout.menu_item_type, mPhotoType, selectedItem, listener, 0);
    }

    public static void showOrderByMenu(Context context, View anchor, MenuItem selectedItem, OnMenuItemClickListener listener) {
        showPopupMenu(context, anchor, R.layout.menu_item_order_by, mOrderBy, selectedItem, listener, -DimensionUtil.dpToPx(8));
    }

    public static void showCollectionTypeMenu(Context context, View anchor, MenuItem selectedItem, OnMenuItemClickListener listener) {
        showPopupMenu(context, anchor, R.layout.menu_item_type, mCollectionType, selectedItem, listener, 0);
    }

    public interface OnMenuItemClickListener {
        void onItemClick(MenuItem menuItem);
    }

}
