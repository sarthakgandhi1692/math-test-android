package com.example.mathTest.api

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * A [Converter.Factory] for converting [String] to and from [RequestBody] and [ResponseBody].
 * This is used by Retrofit to handle plain text request and response bodies.
 */
class StringConverter : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return if (String::class.java == type) {
            Converter<ResponseBody, String> { value -> value.string() }
        } else null
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {

        return if (String::class.java == type) {
            Converter<String, RequestBody> { value -> value.toRequestBody(MEDIA_TYPE) }
        } else null
    }

    companion object {

        private val MEDIA_TYPE = "text/plain".toMediaTypeOrNull()
    }
}