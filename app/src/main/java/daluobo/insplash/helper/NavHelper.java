package daluobo.insplash.helper;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import daluobo.insplash.activity.PhotoActivity;
import daluobo.insplash.activity.ProfileActivity;
import daluobo.insplash.activity.SearchActivity;
import daluobo.insplash.activity.SettingActivity;
import daluobo.insplash.activity.UserActivity;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.model.net.User;

/**
 * Created by daluobo on 2017/12/2.
 */

public class NavHelper {

    public static void toSetting(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }

    public static void toPhoto(Context context, Photo photo, View photoView) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra(PhotoActivity.ARG_PHOTO, photo);
        context.startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                        photoView,
                        "photo_view").toBundle());

    }

    public static void toUser(Context context, User user, View avatarView) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(UserActivity.ARG_USER, user);
        context.startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                        avatarView,
                        "avatar").toBundle());

    }

    public static void toUser(Context context, User user, View avatarView, int showIndex) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(UserActivity.ARG_USER, user);
        intent.putExtra(UserActivity.ARG_SHOW_INDEX, showIndex);
        context.startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                        avatarView,
                        "avatar").toBundle());

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
}
