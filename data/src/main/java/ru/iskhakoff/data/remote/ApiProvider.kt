package ru.iskhakoff.data.remote

import retrofit2.Response
import ru.iskhakoff.data.remote.models.request.*
import ru.iskhakoff.data.remote.models.response.BuyStockResponse
import ru.iskhakoff.data.remote.models.response.InvestItem
import ru.iskhakoff.data.remote.models.response.ProjectItem
import ru.iskhakoff.data.remote.models.response.User

interface ApiProvider {
    suspend fun signIn(user: UserSignIn): Response<User>
    suspend fun signUp(user: UserSignUp): Response<User>
    suspend fun checkLogin(login: UserCheckLogin): Response<ru.iskhakoff.data.remote.models.response.UserCheckLogin>
    suspend fun checkToken(token: String): Response<Void>
    suspend fun getProjects(token: String): Response<List<ProjectItem>>
    suspend fun getProjectById(token: String, id: Int): Response<ProjectItem>
    suspend fun createProject(token: String, project: CreateProjectRequest): Response<Void>
    suspend fun buyAsset(token: String, buyAssetData: BuyStockRequest): Response<BuyStockResponse>
    suspend fun getInvests(token: String): Response<List<InvestItem>>
}