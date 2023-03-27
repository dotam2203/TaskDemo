package com.task

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  15/12/2022
 */
interface ListDataService {
  @GET("550")
  //hàm sẽ tạm ngưng tới khi có kết quả trả về khi được gọi
  suspend fun getItem(@Query("api_key") api_key: String,
  ): Response<DataModel>
}