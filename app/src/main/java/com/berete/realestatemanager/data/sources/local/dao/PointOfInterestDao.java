package com.berete.realestatemanager.data.sources.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.berete.realestatemanager.data.sources.local.entities.PointOfInterestEntity;

import java.util.List;

@Dao
public interface PointOfInterestDao {

  @Query(
      "SELECT point_of_interest.* FROM point_of_interest "
          + "INNER JOIN property_point_of_interest_association "
          + "ON property_point_of_interest_association.point_of_interest_id = point_of_interest.point_of_interest_id "
          + "WHERE property_point_of_interest_association.property_id = :property_id")
  LiveData<List<PointOfInterestEntity>> getPointOfInterestByPropertyId(int property_id);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  long[] create(PointOfInterestEntity... pointOfInterest);

  @Query("SELECT * FROM point_of_interest")
  LiveData<List<PointOfInterestEntity>> getAll();

  @Delete
  void delete(PointOfInterestEntity pointOfInterest);

  @Update
  void update(PointOfInterestEntity pointOfInterest);
}
