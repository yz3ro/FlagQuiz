package com.yz3ro.flagquiz.di

import android.content.Context
import androidx.room.Room
import com.yz3ro.flagquiz.data.datasource.FlagDataSource
import com.yz3ro.flagquiz.data.repo.FlagRepository
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideFlagApiService(): FlagAPI {
        return Retrofit.Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlagAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideFlagDataSource(apiService: FlagAPI, countryDao: CountryDao): FlagDataSource {
        return FlagDataSource(apiService, countryDao)
    }

    @Singleton
    @Provides
    fun provideFlagRepository(dataSource: FlagDataSource): FlagRepository {
        return FlagRepository(dataSource)
    }

    @Singleton
    @Provides
    fun provideCountryDao(@ApplicationContext context: Context): CountryDao {
        val db = Room.databaseBuilder(context, DataBase::class.java, "flagquiz.sqlite")
            .createFromAsset("flagquiz.sqlite").build()
        return db.getCountryDao()
    }
}
