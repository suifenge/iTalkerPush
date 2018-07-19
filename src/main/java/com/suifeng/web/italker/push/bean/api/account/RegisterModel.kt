package com.suifeng.web.italker.push.bean.api.account

data class RegisterModel(var account: String, var password: String, var name: String) {
    constructor() : this("", "", "")
}

