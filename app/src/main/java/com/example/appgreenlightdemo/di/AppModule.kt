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

/***
 * createdBy : Amit
 * description :
 *  this is a module class that can is used to define the dependencies of the entire application.
 *  since we want maintain the dependency until the application scope exists.
 */
@Module
@InstallIn(ApplicationComponent :: class)
class AppModule {

    /**
     * creating single instance of retrofit2 for network calls
     */
    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://demo1929804.mockable.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * creating the instance of interface,
     * it will be responsible for making api calls.
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService :: class.java)
    }

    /**
     * creating single instance of room database for app_database
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(context, AppDatabase :: class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    /**
     * initiating the dao for handling queries.
     */
    @Provides
    fun provideAppDao(appDatabase: AppDatabase) : AppDao {
        return appDatabase.getDao()
    }

    /**
     * creating single instance of main repository,
     * it will responsible for handling network calls and database queries.
     */
    @Provides
    @Singleton
    fun provideMainRepository(
       apiService: ApiService,
       appDao: AppDao
    ) : MainRepository {
        return MainRepository(apiService, appDao)
    }
}