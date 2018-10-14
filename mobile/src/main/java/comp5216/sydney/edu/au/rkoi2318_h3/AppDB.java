package comp5216.sydney.edu.au.rkoi2318_h3;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {RunEntry.class, }, version = 1)
@TypeConverters({DateConverter.class})
public abstract class AppDB extends RoomDatabase {

    private static AppDB DB;

    public static AppDB getDatabase(final Context context){
        if(null == DB){
            DB = Room.databaseBuilder(context.getApplicationContext(),AppDB.class, "rkoi2318_runs")
                    .allowMainThreadQueries().build();
        }
        return DB;
    }

    public abstract RunDao runDao() ;
}
