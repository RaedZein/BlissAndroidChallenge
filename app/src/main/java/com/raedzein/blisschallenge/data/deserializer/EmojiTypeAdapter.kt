package com.raedzein.blisschallenge.data.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.raedzein.blisschallenge.domain.model.Emoji
import com.raedzein.blisschallenge.domain.model.EmojiListResponse
import java.lang.Exception
import java.lang.reflect.Type

/**
 * @author Raed Zein
 * created on Thursday, 18 August, 2022
 */
class EmojiTypeAdapter : JsonDeserializer<EmojiListResponse>{

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): EmojiListResponse {
        val list = arrayListOf<Emoji>()
        if(json.isJsonObject){
            val entries: Set<Map.Entry<String, JsonElement>> = json.asJsonObject.entrySet()
            for ((key) in entries) {
                try {
                    list.add(Emoji(key, json.asJsonObject[key].asString))
                }catch (e: Exception){
                    //Ignore the ones that cant be deserialized
                }
            }
        }
        return EmojiListResponse(list)
    }


}
