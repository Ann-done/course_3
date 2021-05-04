package com.example.login_app.api.service


import retrofit2.Call
import retrofit2.http.GET

 interface JSONServerApi {
  @GET("/api/values")
  fun getStudents() : Call<List<Student>>
}