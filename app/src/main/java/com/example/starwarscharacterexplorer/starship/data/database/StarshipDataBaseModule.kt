package com.example.starwarscharacterexplorer.starship.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class StarshipDataBaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): StarshipDatabase {
        return Room.databaseBuilder(context, StarshipDatabase::class.java, "starshipDb").build()
    }
}