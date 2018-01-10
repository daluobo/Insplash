package daluobo.insplash.helper;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import daluobo.insplash.activity.AboutActivity;
import daluobo.insplash.activity.CollectionActivity;
import daluobo.insplash.activity.CollectionRelatedActivity;
import daluobo.insplash.activity.CompatUserActivity;
import daluobo.insplash.activity.DownloadActivity;
import daluobo.insplash.activity.PhotoActivity;
import daluobo.insplash.activity.ProfileActivity;
import daluobo.insplash.activity.SearchActivity;
import daluobo.insplash.activity.SettingActivity;
import daluobo.insplash.activity.UserActivity;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.model.net.User;
import daluobo.insplash.fragment.dialog.AddToCollectionDialog;
import daluobo.insplash.fragment.dialog.EditCollectionDialog;

/**
 * Created by daluobo on 2017/12/2.
 */

public class NavHelper {

    public static void toSetting(Context context, int x, int y) {
        Intent intent = new Intent(context, SettingActivity.class);
        intent.putExtra(SettingActivity.ARG_REVEAL_X, x);
        intent.putExtra(SettingActivity.ARG_REVEAL_Y, y);
        context.startActivity(intent);
    }

    public static void toPhoto(Context context, Photo photo, View photoView) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra(PhotoActivity.ARG_PHOTO, photo);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                            photoView,
                            "photo_view").toBundle());
        } else {
            context.startActivity(intent);
        }
    }

    public static void toCollection(Context context, Collection collection, int x, int y) {
        Intent intent = new Intent(context, CollectionActivity.class);
        intent.putExtra(CollectionActivity.ARG_COLLECTION, collection);

        intent.putExtra(CollectionActivity.ARG_REVEAL_X, x);
        intent.putExtra(CollectionActivity.ARG_REVEAL_Y, y);

        context.startActivity(intent);
    }

    public static void toUser(Context context, User user, View avatarView) {
        Intent intent;

        if (ConfigHelper.isCompatView()) {
            intent = new Intent(context, CompatUserActivity.class);
            intent.putExtra(UserActivity.ARG_USER, user);
        } else {
            intent = new Intent(context, UserActivity.class);
            intent.putExtra(UserActivity.ARG_USER, user);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                            avatarView,
                            "avatar").toBundle());
        } else {
            context.startActivity(intent);
        }
    }

    public static void toUser(Context context, User user, View avatarView, int showIndex) {
        Intent intent;

        if (ConfigHelper.isCompatView()) {
            intent = new Intent(context, CompatUserActivity.class);
            intent.putExtra(UserActivity.ARG_USER, user);
        } else {
            intent = new Intent(context, UserActivity.class);
            intent.putExtra(UserActivity.ARG_USER, user);
            intent.putExtra(UserActivity.ARG_SHOW_INDEX, showIndex);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                            avatarView,
                            "avatar").toBundle());
        } else {
            context.startActivity(intent);
        }
    }

    public static void toSearch(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    public static void toProfile(Context context, User user) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(ProfileActivity.ARG_USER, user);

        context.startActivity(intent);
    }

    public static void collectPhoto(FragmentManager manager, Photo photo) {
        AddToCollectionDialog setCollectDialog = new AddToCollectionDialog();
        Bundle args = new Bundle();
        args.putParcelable(AddToCollectionDialog.ARG_PHOTO, photo);
        setCollectDialog.setArguments(args);
        setCollectDialog.show(manager, "AddToCollectionDialog");
    }

    public static void editCollection(FragmentManager manager, Collection collection) {
        EditCollectionDialog editCollectDialog = new EditCollectionDialog();
        Bundle args = new Bundle();
        args.putParcelable(EditCollectionDialog.ARG_COLLECTION, collection);
        editCollectDialog.setArguments(args);
        editCollectDialog.show(manager, "EditCollectionDialog");
    }

    public static void toAbout(Context context) {
        context.startActivity(new Intent(context, AboutActivity.class));
    }

    public static void toDownload(Context context) {
        context.startActivity(new Intent(context, DownloadActivity.class));
    }

    public static void toRelateCollection(Context context, Collection collection) {
        Intent intent = new Intent(context, CollectionRelatedActivity.class);
        intent.putExtra(CollectionRelatedActivity.ARG_COLLECTION, collection);
        context.startActivity(intent);
    }

}
