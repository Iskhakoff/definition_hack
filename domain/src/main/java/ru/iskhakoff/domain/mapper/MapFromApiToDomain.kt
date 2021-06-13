package ru.iskhakoff.domain.mapper

import retrofit2.Response
import ru.iskhakoff.data.remote.models.response.*
import ru.iskhakoff.domain.models.ResponseObj

interface MapFromApiToDomain {
    fun userMap(user: Response<User>): ResponseObj<User>
    fun checkingMap(response: Response<UserCheckLogin>): ResponseObj<Boolean>
    fun checkingTokenMap(response: Response<Void>): ResponseObj<Boolean>
    fun projectsMap(response: Response<List<ProjectItem>>): ResponseObj<List<ProjectItem>>
    fun projectMap(response: Response<ProjectItem>): ResponseObj<ProjectItem>
    fun createProjectMap(response: Response<Void>): ResponseObj<Void>
    fun buyStockMap(response: Response<BuyStockResponse>): ResponseObj<BuyStockResponse>
    fun investMap(response: Response<List<InvestItem>>): ResponseObj<List<InvestItem>>
}