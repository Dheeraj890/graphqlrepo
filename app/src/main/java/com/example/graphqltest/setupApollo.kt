package com.example.graphqltest

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient

class setupApollo {


    val BASE_URL="https://api.github.com/graphql"

     fun getClient(): ApolloClient {
        val okHttp = OkHttpClient
            .Builder()
            .addInterceptor({ chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(original.method(),
                    original.body())
                builder.addHeader("Authorization"
                    , "Bearer " + "bac8dc6fd2119fcdbe94332c3e1aa2c43f897b24")
                chain.proceed(builder.build())
            })
            .build()
        return ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttp)
            .build()
    }

}