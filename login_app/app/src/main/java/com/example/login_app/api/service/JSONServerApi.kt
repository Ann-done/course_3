package com.example.login_app.api.service


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

 interface JSONServerApi {
  @GET("/api/values")
  fun getStudents() : Call<List<Student>>

  @POST("/api/values/login")
  fun postLogIn(@Body data: Student): Call<Student?>? //вместе с id предмета
}