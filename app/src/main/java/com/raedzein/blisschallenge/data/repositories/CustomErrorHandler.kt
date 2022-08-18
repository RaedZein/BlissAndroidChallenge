package com.raedzein.blisschallenge.data.repositories

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raedzein.blisschallenge.domain.model.GithubErrorResponse
import okhttp3.ResponseBody
import java.lang.reflect.Type

/**
 * This class is designed to return an Exception based on a custom Error response from the backend
 * @author Raed Zein
 * created on Thursday, 24 March, 2022
 */
class CustomErrorHandler(private val errorBody: ResponseBody?){

    private val gson = Gson()
    private val errorAdaptersTypes: ArrayList<Type> =
        arrayListOf(
            //Add every custom Error adapter
            object : TypeToken<GithubErrorResponse>() {}.type
        )
    private val exception: Exception


    init { exception = Exception(getCustomErrorType()) }

    private fun getCustomErrorType(): String? {
        val errorJson = errorBody?.string()?:""
        var error : CustomErrorResponse? = null
        //will search the list until we find the custom error adapter that correctly serializes the error body
        for (adapter in errorAdaptersTypes){
            error = serializeError(adapter,errorJson)
            if(error!=null)
                break
        }

        return error?.getError()
    }
    private fun <T: CustomErrorResponse> serializeError(type: Type, errorJson: String): T? {
        return try {
            gson.fromJson(errorJson,type)
        }catch (e: Exception){ null }
    }

    fun getError() = exception
}
abstract class CustomErrorResponse{
    abstract fun getError() : String?
}

