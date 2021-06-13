package ru.iskhakoff.domain.implementation

import ru.iskhakoff.data.remote.ApiProvider
import ru.iskhakoff.data.remote.implementation.ApiProviderImpl
import ru.iskhakoff.data.remote.models.request.*
import ru.iskhakoff.data.remote.models.request.Stock
import ru.iskhakoff.data.remote.models.response.*
import ru.iskhakoff.domain.ApiRepository
import ru.iskhakoff.domain.mapper.MapFromApiToDomain
import ru.iskhakoff.domain.models.ResponseObj

class ApiRepositoryImpl(private val apiProvider: ApiProvider, private val mapper: MapFromApiToDomain) : ApiRepository {
    override suspend fun signIn(name: String, password: String): ResponseObj<User> {
        val requestData = UserSignIn(name, password)
        return mapper.userMap(apiProvider.signIn(requestData))
    }

    override suspend fun signUp(name: String, email: String, password: String): ResponseObj<User> {
        val requestData = UserSignUp(name, email, password)
        return mapper.userMap(apiProvider.signUp(requestData))
    }

    override suspend fun checkLogin(login: String): ResponseObj<Boolean> {
        val requestData = ru.iskhakoff.data.remote.models.request.UserCheckLogin(login)
        return mapper.checkingMap(apiProvider.checkLogin(requestData))
    }

    override suspend fun checkToken(token: String): ResponseObj<Boolean> {
        return mapper.checkingTokenMap(apiProvider.checkToken(token))
    }

    override suspend fun getProjects(token: String): ResponseObj<List<ProjectItem>> {
        return mapper.projectsMap(apiProvider.getProjects(token))
    }

    override suspend fun getProjectById(token: String, id: Int): ResponseObj<ProjectItem> {
        return mapper.projectMap(apiProvider.getProjectById(token, id))
    }

    override suspend fun createProject(
        token: String,
        title: String,
        description: String,
        sum: Double
    ): ResponseObj<Void> {
        val project = Project(title, description, sum)
        val projectRequest = CreateProjectRequest(project)
        return mapper.createProjectMap(apiProvider.createProject(token, projectRequest))
    }

    override suspend fun buyAsset(
        token: String,
        sum: Double,
        projectId: Int
    ): ResponseObj<BuyStockResponse> {
        val stock = Stock(
            sum, ProjectStock(projectId)
        )
        val stockRequestData = BuyStockRequest(stock)
        return mapper.buyStockMap(apiProvider.buyAsset(token, stockRequestData))
    }

    override suspend fun getInvests(token: String): ResponseObj<List<InvestItem>> {
        return mapper.investMap(apiProvider.getInvests(token))
    }
}