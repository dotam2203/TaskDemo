package com.repositories

import com.dto.DataApiDTO
import com.services.ListDataService
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class ListDataRepository @Inject constructor(
  private var listDataService: ListDataService,
) {
  fun getShowItem(api_key: String): Flow<Response<DataApiDTO>> = flow {
    //thực hiện gọi API bằng retrofit
    val request = listDataService.getItem(api_key)
    if (request.isSuccessful)
    //phát kết quả yêu cầu cho luồng flow
      emit(request)
  }.flowOn(IO)// chạy trong IO được ưu tiên
}