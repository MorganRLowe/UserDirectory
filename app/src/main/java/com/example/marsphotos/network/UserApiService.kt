package com.example.marsphotos.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

//the base url for the dummy data used to get the profile data
private const val BASE_URL = "https://dummyjson.com/"

//ignore any fields not in the data class
private val json = Json {
    ignoreUnknownKeys = true
}

//build the retrofit with json converter and base url
private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

//data class that represents one user from the dummy api data and
//maps the fields that we want
@Serializable
data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val image: String
)

@Serializable
data class UserResponse(
    val users: List<User>
)

//the GET request to fetch up to 20 users from the dummy data
interface UserApiService {
    @GET("users?limit=20")
    suspend fun getUsers(): UserResponse
}


object UserApi {
    val retrofitService: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
}