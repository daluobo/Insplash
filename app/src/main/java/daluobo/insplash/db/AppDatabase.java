package daluobo.insplash.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import daluobo.insplash.db.dao.DownloadInfoDao;
import daluobo.insplash.db.model.DownloadInfo;

/**
 * Created by daluobo on 2018/1/2.
 */

@Database(entities = {DownloadInfo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase get(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "insplash.db").build();
    }

    public abstract DownloadInfoDao downloadDao();
}
