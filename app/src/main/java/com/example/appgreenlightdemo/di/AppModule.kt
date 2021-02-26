package com.example.appgreenlightdemo.di

import android.content.Context
import androidx.room.Room
import com.example.appgreenlightdemo.database.AppDao
import com.example.appgreenlightdemo.network.ApiService
import com.example.appgreenlightdemo.database.AppDatabase
import com.example.appgreenlightdemo.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent :: class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://demo1929804.mockable.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService :: class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(context, AppDatabase :: class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideAppDao(appDatabase: AppDatabase) : AppDao {
        return appDatabase.getDao()
    }

    @Provides
    @Singleton
    fun provideMainRepository(
       apiService: ApiService,
       appDao: AppDao
    ) : MainRepository {
        return MainRepository(apiService, appDao)
    }
}