package ru.iskhakoff.domain.mapper.implementation

import retrofit2.Response
import ru.iskhakoff.data.remote.models.response.*
import ru.iskhakoff.domain.mapper.MapFromApiToDomain
import ru.iskhakoff.domain.models.ResponseObj

class MapFromApiToDomainImpl : MapFromApiToDomain {
    override fun userMap(user: Response<User>): ResponseObj<User> {
        val code = user.code()
        val message = user.message()
        val obj = user.body()
        return ResponseObj(code, message, obj)
    }

    override fun checkingMap(response: Response<UserCheckLogin>): ResponseObj<Boolean> {
        val code = response.code()
        val message = response.message()
        val obj = response.body()?.data
        return ResponseObj(code, message, obj)
    }

    override fun checkingTokenMap(response: Response<Void>): ResponseObj<Boolean> {
        val code = response.code()
        val message = response.message()
        val obj = code == 200
        return ResponseObj(code, message, obj)
    }

    override fun projectsMap(response: Response<List<ProjectItem>>): ResponseObj<List<ProjectItem>> {
        val code = response.code()
        val message = response.message()
        val body = response.body()
        return ResponseObj(code, message, body)
    }

    override fun projectMap(response: Response<ProjectItem>): ResponseObj<ProjectItem> {
        val code = response.code()
        val message = response.message()
        val body = response.body()
        return ResponseObj(code, message, body)
    }

    override fun createProjectMap(response: Response<Void>): ResponseObj<Void> {
        val code = response.code()
        val message = response.message()
        return ResponseObj(code, message, null)
    }

    override fun buyStockMap(response: Response<BuyStockResponse>): ResponseObj<BuyStockResponse> {
        val code = response.code()
        val message = response.message()
        val body = response.body()
        return ResponseObj(code, message, body)
    }

    override fun investMap(response: Response<List<InvestItem>>): ResponseObj<List<InvestItem>> {
        val code = response.code()
        val message = response.message()
        val body = response.body()?.map { investItem ->
            var x = 0.0
            investItem.stocks.forEach { stock ->
                x += stock.sum
            }
            investItem.owned = x
            investItem
        }

        return ResponseObj(code, message, body)
    }

}