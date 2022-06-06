package com.markolucic.cubes.events24.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.markolucic.cubes.events24.data.model.Event
import com.markolucic.cubes.events24.data.room.dao.EventDAO

@Database(entities = [Event::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDAO(): EventDAO?

    companion object {
        private var instance: AppDatabase? = null
        fun getInstance(context: Context?): AppDatabase? {

            if (instance == null) {

                instance = Room.databaseBuilder(
                    context!!,
                    AppDatabase::class.java,
                    "database-events24"
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return instance
        }
    }
}