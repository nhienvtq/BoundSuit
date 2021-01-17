package iKi.com

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iKi.com.networkRESTful.PhotoApiFilter
import iKi.com.networkRESTful.PhotoModel
import iKi.com.networkRESTful.photoApi
import kotlinx.coroutines.launch

enum class PhotoApiStatus { LOADING, ERROR, DONE }

class PhotoViewModel(): ViewModel() {
    private val _status = MutableLiveData<PhotoApiStatus>()
    val status: LiveData<PhotoApiStatus> get() = _status

    private val _model = MutableLiveData<List<PhotoModel>>()
    val model: LiveData<List<PhotoModel>> get() = _model

    var indexArray: Int

    fun updateFilter(filter: PhotoApiFilter) {
        getPhotoModel(filter)
    }

    init{
        getPhotoModel(PhotoApiFilter.SHOW_ALL)
        indexArray = 0
    }

    private fun getPhotoModel(filter: PhotoApiFilter){
        viewModelScope.launch{
            _status.value = PhotoApiStatus.LOADING
            try {
                _model.value = photoApi.retrofitService.getProperties(filter.value)
                _status.value = PhotoApiStatus.DONE
            } catch (e: Exception) {
                _status.value = PhotoApiStatus.ERROR
                _model.value = ArrayList()
            }
        }
    }

//    fun increase(){
//        indexArray = indexArray + 1
//    }
}
