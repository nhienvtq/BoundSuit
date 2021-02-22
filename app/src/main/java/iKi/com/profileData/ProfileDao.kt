package iKi.com.profileData

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profiles_table ORDER BY id ASC")
    fun readAllData():LiveData<List<Profile>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProfile(profile: Profile)

    @Delete
    suspend fun delProfile(profile: Profile)

    @Query("DELETE FROM profiles_Table")
    suspend fun deleteAllProfile()

    @Update
    suspend fun updateProfile(profile: Profile)
}