package com.example.rickmortyapp.models

data class ApiResult(
    val info: Info,
    val results: List<Result>
)