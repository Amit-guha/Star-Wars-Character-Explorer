package com.example.starwarscharacterexplorer.planet.data.database

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
class PlanetDataBaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PlanetDatabase {
        return Room.databaseBuilder(context, PlanetDatabase::class.java, "PlanetDb").build()
    }
}