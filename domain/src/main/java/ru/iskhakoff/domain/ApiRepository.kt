package ru.iskhakoff.domain

import ru.iskhakoff.data.remote.models.response.*
import ru.iskhakoff.domain.models.ResponseObj

interface ApiRepository {
    suspend fun signIn(name: String, password: String): ResponseObj<User>
    suspend fun signUp(name: String, email: String, password: String): ResponseObj<User>
    suspend fun checkLogin(login: String): ResponseObj<Boolean>
    suspend fun checkToken(token: String): ResponseObj<Boolean>
    suspend fun getProjects(token: String): ResponseObj<List<ProjectItem>>
    suspend fun getProjectById(token: String, id: Int): ResponseObj<ProjectItem>
    suspend fun createProject(token: String, title: String, description: String, sum: Double): ResponseObj<Void>
    suspend fun buyAsset(token: String, sum: Double, projectId: Int): ResponseObj<BuyStockResponse>
    suspend fun getInvests(token: String): ResponseObj<List<InvestItem>>
}