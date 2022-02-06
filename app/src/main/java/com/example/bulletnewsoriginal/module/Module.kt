package com.example.bulletnewsoriginal.module

import android.content.Context
import com.example.bulletnewsoriginal.util.SharedPreferenceService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Singleton
    @Provides
    fun provideSharedPreference(
        @ActivityContext context: Context
    ) : SharedPreferenceService = SharedPreferenceService(context)

}