package com.task.notifications

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 11/05/2023
 */
@GlideModule
class GlideModule : AppGlideModule() {
  override fun applyOptions(context: Context, builder: GlideBuilder) {
    super.applyOptions(context, builder)
    val requestOption = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
      .signature(ObjectKey(System.currentTimeMillis()))
    builder.setDefaultRequestOptions(requestOption)
  }
}