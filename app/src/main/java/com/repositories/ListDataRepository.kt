package com.repositories

import com.entities.DataModel
import com.services.ListDataService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class ListDataRepository @Inject constructor(
  private var listDataService: ListDataService,
) {
  fun getShowItem(api_key: String): Flow<Response<DataModel>> = flow {
    //thực hiện gọi API bằng retrofit
    val request = listDataService.getItem(api_key)
    if (request.isSuccessful)
    //phát kết quả yêu cầu cho luồng flow
      emit(request)
  }.flowOn(IO)// chạy trong IO được ưu tiên
}