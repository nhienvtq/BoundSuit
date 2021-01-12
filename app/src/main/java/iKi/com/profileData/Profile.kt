package iKi.com.profileData

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "profile_Table")
class Profile(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val Name: String,
    val Age: Int,
    val Birthday: String,
    val Nation: String,
    val Gender: String,
    val PhoneNumber: String,
    val Email: String,
    val Image_profile: String
): Parcelable