package iKi.com.profileData

import android.app.AlertDialog
import android.app.Application
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
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

    fun delProfile(profile: Profile){
        viewModelScope.launch(Dispatchers.IO){
            repository.delProfile(profile)
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