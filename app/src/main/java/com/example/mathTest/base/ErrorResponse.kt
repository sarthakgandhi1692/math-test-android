package com.example.mathTest.base

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import se.ansman.kotshi.JsonSerializable

/**
 * Represents an error response from the API.
 *
 * This class is annotated with `@JsonSerializable` for Moshi JSON serialization/deserialization,
 * `@Parcelize` for Android Parcelable implementation, and extends `Exception` to be throwable.
 * It contains information about the error, such as an image URL, subtitle, title, and type.
 */
@JsonSerializable
@Parcelize
data class ErrorResponse(
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "subtitle")
    val subtitle: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "type")
    val type: String?
) : Parcelable, Exception()
