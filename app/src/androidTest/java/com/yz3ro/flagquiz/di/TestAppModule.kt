package com.yz3ro.flagquiz.di

import android.content.Context
import androidx.room.Room
import com.yz3ro.flagquiz.data.datasource.FlagDataSource
import com.yz3ro.flagquiz.data.retrofit.FlagAPI
import com.yz3ro.flagquiz.room.CountryDao
import com.yz3ro.flagquiz.room.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {


    @Provides
    @Named("testDatabase")
    fun injectMockCountryDao(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context,DataBase::class.java)
            .allowMainThreadQueries()
            .build()
}