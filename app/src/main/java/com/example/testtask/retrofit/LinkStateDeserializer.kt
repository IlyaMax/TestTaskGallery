package com.example.testtask.retrofit

import com.example.testtask.models.LinkState
import com.example.testtask.models.Status
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class LinkStateDeserializer : JsonDeserializer<LinkState> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LinkState {
        val success = json?.asJsonObject?.get("success")?.asBoolean
        val status = json?.asJsonObject?.get("status")?.asInt
        return if (success == true) LinkState(
            Status.SUCCESS,
            json.asJsonObject?.get("data")?.asJsonObject?.get("link")?.asString, null
        ) else LinkState(
            Status.ERROR,
            null,
            Throwable("ERR: $status")
        )
    }
}