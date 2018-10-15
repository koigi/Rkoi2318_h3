package comp5216.sydney.edu.au.rkoi2318_h3;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RunDao {

    @Insert
    void insertRun(RunEntry runEntry);

    @Query("SELECT * FROM RunEntry ORDER BY startTime desc")
    List<RunEntry> fetchAllRunEntries();

    @Query("SELECT * FROM RunEntry where runId = :uuid")
    List<RunEntry> getRunEntryByID(String uuid);

}
