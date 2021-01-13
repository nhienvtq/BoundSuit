package iKi.com.profileData

import androidx.lifecycle.LiveData

class ProfileRepository(private val profileDao: ProfileDao) {
    val readAllData: LiveData<List<Profile>> = profileDao.readAllData()

    suspend fun addProfile(newProfile: Profile){
        profileDao.addProfile(newProfile)
    }

    suspend fun delProfile(selectedProfile: Profile){
        profileDao.delProfile(selectedProfile)
    }

    suspend fun delAllProfile(){
        profileDao.deleteAllProfile()
    }

    suspend fun updateProfile(selectedProfile: Profile){
        profileDao.updateProfile(selectedProfile)
    }
}