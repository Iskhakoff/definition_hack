package ru.iskhakoff.data.remote.implementation

import retrofit2.Response
import ru.iskhakoff.data.remote.ApiProvider
import ru.iskhakoff.data.remote.helper.RetrofitFactory
import ru.iskhakoff.data.remote.models.request.*
import ru.iskhakoff.data.remote.models.response.BuyStockResponse
import ru.iskhakoff.data.remote.models.response.InvestItem
import ru.iskhakoff.data.remote.models.response.ProjectItem
import ru.iskhakoff.data.remote.models.response.User

class ApiProviderImpl : ApiProvider {
    override suspend fun signIn(user: UserSignIn):
            Response<User> = RetrofitFactory.getService().signIn(user)
    override suspend fun signUp(user: UserSignUp):
            Response<User> = RetrofitFactory.getService().signUp(user)
    override suspend fun checkLogin(login: UserCheckLogin):
            Response<ru.iskhakoff.data.remote.models.response.UserCheckLogin> = RetrofitFactory.getService().checkLogin(login)
    override suspend fun checkToken(token: String):
            Response<Void> = RetrofitFactory.getService().checkToken(token)
    override suspend fun getProjects(token: String):
            Response<List<ProjectItem>> = RetrofitFactory.getService().getProjects(token)
    override suspend fun getProjectById(token: String, id: Int):
            Response<ProjectItem> = RetrofitFactory.getService().getProjectById(token, id)

    override suspend fun createProject(
        token: String,
        project: CreateProjectRequest
    ): Response<Void> {
        return RetrofitFactory.getService().createProject(token, project)
    }

    override suspend fun buyAsset(
        token: String,
        buyAssetData: BuyStockRequest
    ): Response<BuyStockResponse> {
        return RetrofitFactory.getService().buyAsset(token, buyAssetData)
    }

    override suspend fun getInvests(token: String): Response<List<InvestItem>> {
        return RetrofitFactory.getService().getInvests(token)
    }

}