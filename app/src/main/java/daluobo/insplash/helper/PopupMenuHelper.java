package daluobo.insplash.helper;

import android.content.Context;
import android.content.res.Resources;
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
import daluobo.insplash.common.MyApplication;
import daluobo.insplash.model.OptionItem;
import daluobo.insplash.util.DimensionUtil;
import daluobo.insplash.util.ViewUtil;
import daluobo.insplash.viewmodel.BasePageViewModel;
import daluobo.insplash.viewmodel.CollectionsViewModel;
import daluobo.insplash.viewmodel.PhotoViewModel;

/**
 * Created by daluobo on 2017/12/17.
 */

public class PopupMenuHelper {
    private static final List<OptionItem> mPhotoType = new ArrayList<>();
    private static final List<OptionItem> mOrderBy = new ArrayList<>();
    private static final List<OptionItem> mCollectionType = new ArrayList<>();
    private static final List<OptionItem> mViewType = new ArrayList<>();
    private static final List<OptionItem> mLanguage = new ArrayList<>();

    private static String[] mPhotosOption;
    private static String[] mOrderByOption;
    private static String[] mCollectionOption;
    private static String[] mViewOption;
    private static String[] mLanguageOption;

    public static void initPhotoType(Context context) {
        Resources res = context.getResources();

        mPhotosOption = res.getStringArray(R.array.photos_option);

        mPhotoType.clear();
        mPhotoType.add(new OptionItem(mPhotosOption[0], PhotoViewModel.PhotoType.ALL));
        mPhotoType.add(new OptionItem(mPhotosOption[1], PhotoViewModel.PhotoType.CURATED));
    }

    public static void initOrderBy(Context context) {
        Resources res = context.getResources();
        mOrderByOption = res.getStringArray(R.array.order_by_option);

        mOrderBy.clear();
        mOrderBy.add(new OptionItem(mOrderByOption[0], BasePageViewModel.OrderBy.LATEST));
        mOrderBy.add(new OptionItem(mOrderByOption[1], BasePageViewModel.OrderBy.OLDEST));
        mOrderBy.add(new OptionItem(mOrderByOption[2], BasePageViewModel.OrderBy.POPULAR));
    }

    public static void initCollectionType(Context context) {
        Resources res = context.getResources();
        mCollectionOption = res.getStringArray(R.array.collection_option);

        mCollectionType.clear();
        mCollectionType.add(new OptionItem(mCollectionOption[0], CollectionsViewModel.CollectionType.ALL));
        mCollectionType.add(new OptionItem(mCollectionOption[1], CollectionsViewModel.CollectionType.FEATURED));
        mCollectionType.add(new OptionItem(mCollectionOption[2], CollectionsViewModel.CollectionType.CURATED));
    }

    public static void initViewType(Context context) {
        Resources res = context.getResources();
        mViewOption = res.getStringArray(R.array.view_option);

        mViewType.clear();
        mViewType.add(new OptionItem(mViewOption[0], ConfigHelper.ViewType.CARD));
        mViewType.add(new OptionItem(mViewOption[1], ConfigHelper.ViewType.COMPAT));
    }

    public static void initLanguage(Context context) {
        Resources res = context.getResources();
        mLanguageOption = res.getStringArray(R.array.language_option);

        mLanguage.clear();
        mLanguage.add(new OptionItem(mLanguageOption[0], ConfigHelper.Language.ENGLISH));
        mLanguage.add(new OptionItem(mLanguageOption[1], ConfigHelper.Language.CHINESE));
    }

    public static void showPopupMenu(Context context, View anchor,
                                     @LayoutRes int itemLayoutId,
                                     List<OptionItem> menuItems,
                                     OptionItem selectedItem,
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

        for (final OptionItem menuItem : menuItems) {
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

    public static void showOptionMenu(Context context, View anchor,
                                      @LayoutRes int itemLayoutId,
                                      List<OptionItem> menuItems,
                                      final OptionItem selectedItem,
                                      final OnMenuItemClickListener listener,
                                      int xOff,
                                      int yOff) {
        int windowWidth, windowHeight;
        final PopupWindow window = new PopupWindow(context);
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View contentView = inflater.inflate(R.layout.menu_option_container, null, false);
        final LinearLayout container = contentView.findViewById(R.id.container);

        View smi = inflater.inflate(itemLayoutId, null, false);
        TextView sTitle = smi.findViewById(R.id.title);
        sTitle.setText(selectedItem.title);

        windowWidth = ViewUtil.getViewSize(sTitle)[0];
        windowHeight = ViewUtil.getViewSize(sTitle)[1] * menuItems.size() + DimensionUtil.dpToPx(8) * 2;

        smi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        container.addView(smi);

        for (final OptionItem menuItem : menuItems) {
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
        window.setHeight(windowHeight);
        window.setOutsideTouchable(true);
        window.setFocusable(true);
        window.setTouchable(true);
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        window.setAnimationStyle(R.style.AlphaAnimation);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                listener.onDismiss();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setElevation(10);
        }

        window.showAsDropDown(anchor, anchor.getWidth() - windowWidth, -(anchor.getHeight() + yOff));
    }

    public static void showPhotoTypeMenu(Context context, View anchor, OptionItem selectedItem, OnMenuItemClickListener listener) {
        showPopupMenu(context, anchor, R.layout.menu_item_type, mPhotoType, selectedItem, listener, 0);
    }

    public static void showOrderByMenu(Context context, View anchor, OptionItem selectedItem, OnMenuItemClickListener listener) {
        showPopupMenu(context, anchor, R.layout.menu_item_order_by, mOrderBy, selectedItem, listener, -DimensionUtil.dpToPx(8));
    }

    public static void showCollectionTypeMenu(Context context, View anchor, OptionItem selectedItem, OnMenuItemClickListener listener) {
        showPopupMenu(context, anchor, R.layout.menu_item_type, mCollectionType, selectedItem, listener, 0);
    }

    public static void showViewTypeMenu(Context context, View anchor, OptionItem selectedItem, OnMenuItemClickListener listener) {
        showOptionMenu(context, anchor, R.layout.menu_item_view, mViewType, selectedItem, listener, 0, DimensionUtil.dpToPx(8));
    }

    public static void showLanguageMenu(Context context, View anchor, OptionItem selectedItem, OnMenuItemClickListener listener) {
        showOptionMenu(context, anchor, R.layout.menu_item_view, mLanguage, selectedItem, listener, 0, DimensionUtil.dpToPx(8));
    }

    public static List<OptionItem> getPhotoType() {
        initPhotoType(MyApplication.getInstance());
        return mPhotoType;
    }

    public static List<OptionItem> getOrderBy() {
        initOrderBy(MyApplication.getInstance());
        return mOrderBy;
    }

    public static List<OptionItem> getCollectionType() {
        initCollectionType(MyApplication.getInstance());
        return mCollectionType;
    }

    public static List<OptionItem> getViewType() {
        initViewType(MyApplication.getInstance());
        return mViewType;
    }

    public static List<OptionItem> getLanguage() {
        initLanguage(MyApplication.getInstance());
        return mLanguage;
    }

    public interface OnMenuItemClickListener {
        void onItemClick(OptionItem menuItem);

        void onDismiss();
    }
}
