package com.task

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class ListDataRepository {
  fun getShowItem(api_key: String): Flow<Response<DataModel>> = flow {
    //thực hiện gọi API bằng retrofit
    val request = CallApiRetrofit.loadApi.getItem(api_key)
    if (request.isSuccessful)
    //phát kết quả yêu cầu cho luồng flow
      emit(request)
  }.flowOn(IO)// chạy trong IO được ưu tiên
}