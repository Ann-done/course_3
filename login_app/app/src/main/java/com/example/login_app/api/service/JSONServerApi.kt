package com.example.login_app.api.service


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface JSONServerApi {
  @GET("/api/values")
  fun getStudents() : Call<List<Student>>

  @POST("/api/values/login")
  fun postLogIn(@Body data: String): Call<Student?>? //вместе с id предмета

  @POST("/api/values/getsubject")
  fun postGetSubject(@Body data: String): Call<MySubject?>?

  @POST("/api/values/gettopics")
  fun getTopics(@Body data:String): Call<List<Topic>?>?

  @POST("/api/values/getQus")
  fun postGetTest(@Body data: String): Call<List<Task>?>?

   @POST("/api/values/postResult")
   fun postResult(@Body data: String) : Call<Int>?

   @POST("/api/values/getNewRes")
   fun postGetNewRes(@Body data: String) : Call<List<Result>>?

    @POST("/api/values/logOut")
    fun postLogOut(@Body data: String): Call<Logout>? //вместе с id предмета
}