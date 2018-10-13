package comp5216.sydney.edu.au.rkoi2318_h3;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {RunEntry.class, }, version = 1)
public abstract class AppDB extends RoomDatabase {

    private static AppDB DB;

    public abstract RunDao runDao() ;
}
