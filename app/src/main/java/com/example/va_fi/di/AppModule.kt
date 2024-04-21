package com.example.va_fi.di


import android.app.Application
import androidx.room.Room
import com.example.va_fi.MainRepository
import com.example.va_fi.MainViewModel
import com.example.va_fi.data_source.MainDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    /** Creating Room Database Instance **/
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): MainDatabase {
        return Room.databaseBuilder(
            app,
            MainDatabase::class.java,
            MainDatabase.DATABASE_NAME
        ).build()
    }


    /** Creating Repository Instance **/
    @Provides
    @Singleton
    fun provideNoteRepository(db:MainDatabase):MainRepository {
        return MainRepository(db.mwifiUrlDao)
    }

}