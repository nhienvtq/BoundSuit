package iKi.com.profileData

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProfile(profile: Profile)

    @Query("SELECT * FROM profile_table ORDER BY id ASC")
    fun readAllData():LiveData<List<Profile>>

    @Query("DELETE FROM profile_Table")
    suspend fun deleteAllProfile()

    @Delete
    suspend fun delProfile(profile: Profile)
}