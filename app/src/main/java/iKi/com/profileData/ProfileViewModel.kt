package iKi.com.profileData

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Profile>>
    private val repository: ProfileRepository

    init{
        val profileDao = ProfileDatabase.getDatabase(application).profileDao()
        repository = ProfileRepository(profileDao)
        readAllData = repository.readAllData
    }

    fun addProfile(profile: Profile){
        viewModelScope.launch(Dispatchers.IO){
            repository.addProfile(profile)
        }
    }

    fun delAllProfile(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delAllProfile()
        }
    }

    fun updateProfile(profile: Profile){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateProfile(profile)
        }
    }
}