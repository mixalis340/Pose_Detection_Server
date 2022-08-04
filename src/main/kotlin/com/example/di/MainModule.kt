package com.example.di

import com.example.controller.UserController
import com.example.controller.UserControllerImpl
import com.example.util.Constants
import com.mongodb.reactivestreams.client.MongoDatabase
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


val mainModule = module {
    single{
        val client = KMongo.createClient().coroutine
        client.getDatabase(Constants.DATABASE_NAME)
    }
    single<UserController >{
        UserControllerImpl(get())
    }
}