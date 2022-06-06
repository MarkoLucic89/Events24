package com.markolucic.cubes.events24.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.markolucic.cubes.events24.data.model.Event

@Dao
interface EventDAO {

    @Insert
     fun insert(event: Event?)

    @Delete
     fun delete(event: Event?)

    @Query("SELECT * FROM event")
     fun getAll(): List<Event?>?

    @Query("SELECT * FROM event WHERE type LIKE :type")
     fun getAllByType(type: String?): List<Event?>?

    @Query("SELECT * FROM event WHERE id LIKE :id")
     fun getEvent(id: String?): Event?


}