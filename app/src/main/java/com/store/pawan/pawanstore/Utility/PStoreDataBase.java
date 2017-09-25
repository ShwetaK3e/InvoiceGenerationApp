package com.store.pawan.pawanstore.Utility;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.store.pawan.pawanstore.entities.Account;

/**
 * Created by shwetakumar on 9/26/17.
 */

@Database(entities = {
        Account.class
}, version = 1,exportSchema = false)
public abstract class PStoreDataBase  extends RoomDatabase{

    private static  PStoreDataBase INSTANCE;

    public  abstract Account AccountDao();

    public PStoreDataBase getPStoreDatabaseInstance(Context context){
        if(INSTANCE==null) {
            INSTANCE = Room.databaseBuilder(context, PStoreDataBase.class, "PStore")
                    //remove this
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build();
        }
        return INSTANCE;
    }

    public  void destroyInstance(){
        INSTANCE=null;
    }


}
