package iKi.com.networkRESTful

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://mars.udacity.com/realestate
private const val BASE_URL = "https://mars.udacity.com"
enum class PhotoApiFilter(val value: String) { SHOW_RENT("rent"), SHOW_BUY("buy"), SHOW_ALL("all") }

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface photoApiService{
    @GET("realestate")
    suspend fun getProperties(@Query("filter") type: String): List<PhotoModel>
}

object photoApi{
    val retrofitService: photoApiService by lazy{retrofit.create(photoApiService::class.java)}
}