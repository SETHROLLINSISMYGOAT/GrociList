package com.siddhant.grocilist.di

import android.content.Context
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate
import com.siddhant.grocilist.data.local.AppDatabase
import com.siddhant.grocilist.data.local.CartDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase{
        return AppDatabase.getDatabase(context)
    }
    @Provides
    fun provideCartDao(database : AppDatabase) : CartDao{
        return database.cartDao()
    }

}
