package ru.iskhakoff.data.remote.service

import retrofit2.Response
import retrofit2.http.*
import ru.iskhakoff.data.remote.models.request.*
import ru.iskhakoff.data.remote.models.response.BuyStockResponse
import ru.iskhakoff.data.remote.models.response.InvestItem
import ru.iskhakoff.data.remote.models.response.ProjectItem
import ru.iskhakoff.data.remote.models.response.User

private const val SIGN_IN = "user/login"
private const val SIGN_UP = "user/registration"
private const val CHECK_LOGIN = "user/check"
private const val CHECK_TOKEN = "user/check-login"
private const val GET_PROJECTS = "project"
private const val GET_PROJECT_BY_ID = "project/"
private const val CREATE_PROJECT = "project/"
private const val BUY_ASSET = "stock/"
private const val GET_INVESTS = "stock"
interface ApiService {
    @POST(SIGN_IN)
    suspend fun signIn(@Body user: UserSignIn): Response<User>
    @POST(SIGN_UP)
    suspend fun signUp(@Body user: UserSignUp): Response<User>
    @POST(CHECK_LOGIN)
    suspend fun checkLogin(@Body login: UserCheckLogin): Response<ru.iskhakoff.data.remote.models.response.UserCheckLogin>
    @POST(CHECK_TOKEN)
    suspend fun checkToken(@Header("Authorization") token: String): Response<Void>
    @GET(GET_PROJECTS)
    suspend fun getProjects(@Header("Authorization") token: String): Response<List<ProjectItem>>
    @GET("$GET_PROJECT_BY_ID{id}")
    suspend fun getProjectById(@Header("Authorization") token: String, @Path("id") id: Int): Response<ProjectItem>
    @POST(CREATE_PROJECT)
    suspend fun createProject(@Header("Authorization") token: String, @Body project: CreateProjectRequest): Response<Void>
    @POST(BUY_ASSET)
    suspend fun buyAsset(@Header("Authorization") token: String, @Body asset: BuyStockRequest): Response<BuyStockResponse>
    @GET(GET_INVESTS)
    suspend fun getInvests(@Header("Authorization") token: String): Response<List<InvestItem>>
}