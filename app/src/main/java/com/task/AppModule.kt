package com.task

import com.repositories.ListDataRepository
import com.services.ListDataService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 05/04/2023
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
  @Provides
  @Singleton
  fun provideListRepository(listDataService: ListDataService) = ListDataRepository(listDataService)
}