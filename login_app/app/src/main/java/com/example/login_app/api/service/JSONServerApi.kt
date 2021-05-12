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
  fun postGetSubject(@Body data: String): Call<Subject?>?

  @GET("/api/values/gettopics/{idSubj}")
  fun getTopics(@Path("idSubj") idSubj: Int): Call<List<Topic>?>?

  @POST("/api/values/getQus")
  fun postGetTest(@Body data: String): Call<List<Task>?>?

}