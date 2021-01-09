package iKi.com.profileData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
class Profile(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val Name: String,
    val Age: Int,
    val Nation: String,
    val Gender: String,
    val PhoneNumber: Int,
    val Email: String,
    val Image_profile: Int
)