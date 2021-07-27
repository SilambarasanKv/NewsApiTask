package com.betamonks.newsapitask.api

import com.betamonks.newsapitask.model.ArticleResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ArticleInterface{

    @Headers("X-Api-Key: 816bec77c3e14c0fbd10081bcf3e411b")
    @GET("top-headlines")
    fun getArticle(
        @Query("country") country: String
    ): Call<ArticleResult>
}