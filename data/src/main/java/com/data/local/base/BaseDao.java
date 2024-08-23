package com.data.local.base;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BaseDao<T> {

    /**
     * Insert an object in the database.
     *
     * @param obj the object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(T obj);

    /**
     * Insert an array of objects in the database.
     *
     * @param obj the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T... obj);

    /**
     * Insert an list of objects in the database
     *
     * @param list the objects of list to be insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<T> list);

    /**
     * Update an object from the database.
     *
     * @param obj the object to be updated
     */
    @Update
    void update(T obj);

    /**
     * Delete an object from the database
     *
     * @param obj the object to be deleted
     */
    @Delete
    void delete(T obj);

}