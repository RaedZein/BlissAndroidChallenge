package com.raedzein.blisschallenge.data.api

import com.raedzein.blisschallenge.utils.Constants
import com.raedzein.blisschallenge.utils.Constants.DEFAULT_REPO_USER

class GoogleReposQueryBuilder : GitReposQueryBuilder() {

    override fun putQueryParams(queryParams: MutableMap<String, String>) {
        queryParams["per_page"] = Constants.DEFAULT_PER_PAGE.toString()
    }

    private var currentUser = DEFAULT_REPO_USER
    override fun getCurrentUser() = currentUser

    fun setCurrentUser(name: String) {
        currentUser = name
    }
}
