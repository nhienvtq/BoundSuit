package iKi.com.networkRESTful

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class PhotoModel (val id: String,@Json(name = "img_src") val imgSrcUrl: String,val type: String,val price: Double) {
    fun getid(): String{return id}
    fun getUrl(): String{return imgSrcUrl}
    fun gettype(): String{return type}
    fun getprice(): String{return price.toString()}
}
