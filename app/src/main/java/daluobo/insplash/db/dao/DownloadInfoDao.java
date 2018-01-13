package daluobo.insplash.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import daluobo.insplash.db.model.DownloadInfo;

/**
 * Created by daluobo on 2018/1/2.
 */

@Dao
public interface DownloadInfoDao {

    @Query("SELECT * FROM download")
    LiveData<List<DownloadInfo>> getAll();

    @Query("SELECT * FROM download WHERE state = :state")
    LiveData<List<DownloadInfo>> getProcessing(String state);

    @Query("SELECT * FROM download WHERE id = :id")
    LiveData<DownloadInfo> getById(String id);

    @Query("SELECT * FROM download WHERE id = :id")
    DownloadInfo getDataById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(DownloadInfo downloadItem);

    @Update
    void update(DownloadInfo downloadItem);

    @Delete
    void deleteAll(List<DownloadInfo> downloadItems);

    @Delete
    void delete(DownloadInfo... downloadItems);

    @Query("SELECT * FROM download WHERE state != :state")
    List<DownloadInfo> getNotProcessing(String state);
}
