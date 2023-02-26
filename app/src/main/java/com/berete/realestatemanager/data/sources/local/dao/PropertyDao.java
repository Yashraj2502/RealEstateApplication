package com.berete.realestatemanager.data.sources.local.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.berete.realestatemanager.data.sources.local.entities.PropertyEntity;
import com.berete.realestatemanager.utils.CurrencyUtils;

import java.util.List;

@Dao
public interface PropertyDao {

  @Query("SELECT * FROM property WHERE property_id=:id")
  PropertyEntity getById(int id);

  @Query("SELECT * FROM property WHERE property_id=:id")
  Cursor getAsCursor(int id);

  @Query("SELECT * FROM property")
  List<PropertyEntity> getAll();

  @Update
  void update(PropertyEntity property);

  @Delete
  void delete(PropertyEntity property);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  long[] create(PropertyEntity... property);

}
