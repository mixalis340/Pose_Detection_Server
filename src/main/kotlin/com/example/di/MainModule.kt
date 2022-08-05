package com.example.di

import com.example.data.repository.user.UserRepository
import com.example.data.repository.user.UserRepositoryImpl
import com.example.service.UserService
import com.example.util.Constants
import com.google.gson.Gson
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


val mainModule = module {
    single{
        val client = KMongo.createClient().coroutine
        client.getDatabase(Constants.DATABASE_NAME)
    }
    single<UserRepository>{
        UserRepositoryImpl(get())
    }
    single { UserService(get()) }

    single { Gson() }

}