package com.suifeng.web.italker.push.bean.card

import com.google.gson.annotations.Expose

import java.time.LocalDateTime

class UserCard {

    @Expose
    private val id: String? = null
    @Expose
    private val name: String? = null
    @Expose
    private val phone: String? = null
    @Expose
    private val portrait: String? = null
    @Expose
    private val desc: String? = null
    @Expose
    private val sex = 0

    //用户关注人的数量
    @Expose
    private val follows: Int = 0

    //关注用户的数量
    @Expose
    private val following: Int = 0

    //自己与当前User的关系状态，是否已经关注了这个人
    @Expose
    private val isFollow: Int = 0

    //用户信息最后的更新时间
    @Expose
    private val modifyAt: LocalDateTime? = null

}
