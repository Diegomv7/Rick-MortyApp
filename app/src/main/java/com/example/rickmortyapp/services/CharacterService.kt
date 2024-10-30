package com.example.rickmortyapp.services

import com.example.rickmortyapp.models.ApiResult
import com.example.rickmortyapp.models.Result
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {
    @GET("character")
    suspend fun getCharacters() : ApiResult

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id : Int) : Result
}
