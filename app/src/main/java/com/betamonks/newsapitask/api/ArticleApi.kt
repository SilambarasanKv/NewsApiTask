package com.betamonks.newsapitask.api

import com.betamonks.newsapitask.model.ArticleResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticleApi{
    private val articleInterface : ArticleInterface


    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        articleInterface = retrofit.create(ArticleInterface::class.java)
    }

    fun getResults(): Call<ArticleResult> {
        return articleInterface.getArticle("ind")
    }
}