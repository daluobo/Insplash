package daluobo.insplash.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import daluobo.insplash.db.dao.DownloadDao;
import daluobo.insplash.db.model.DownloadItem;

/**
 * Created by daluobo on 2018/1/2.
 */

@Database(entities = {DownloadItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static AppDatabase sInstance;

    public static AppDatabase init(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context, AppDatabase.class, "insplash.db").build();
        }
        return sInstance;
    }

    public abstract DownloadDao downloadDao();
}
