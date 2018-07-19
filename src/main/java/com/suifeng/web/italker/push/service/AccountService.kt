package com.suifeng.web.italker.push.service

import com.suifeng.web.italker.push.bean.api.account.RegisterModel
import com.suifeng.web.italker.push.bean.card.UserCard
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/account")
class AccountService {

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun register(model: RegisterModel) : UserCard{
        return UserCard()
    }
}