package iKi.com.profileData

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Profile::class], version = 1, exportSchema = false)
abstract class ProfileDatabase:RoomDatabase() {
    abstract fun profileDao(): ProfileDao

    companion object{
        @Volatile
        private var INSTANCE: ProfileDatabase? = null

        fun getDatabase(context: Context): ProfileDatabase{
            val tempInstance = INSTANCE
            //verify pre-exist database - else create instance
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProfileDatabase::class.java,
                    "profiles_Table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}