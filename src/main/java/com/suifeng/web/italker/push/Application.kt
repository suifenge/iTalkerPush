package com.suifeng.web.italker.push

import com.suifeng.web.italker.push.provider.GsonProvider
import com.suifeng.web.italker.push.service.AccountService
import org.glassfish.jersey.server.ResourceConfig
import java.util.logging.Logger

class Application : ResourceConfig() {
    init {
        //注册逻辑处理的包名
        packages(AccountService::class.java.`package`.name)
        //注册json解析器
        //register(JacksonJsonProvider::class)
        register(GsonProvider::class)
        //注册日志打印
        register(Logger::class)
    }
}