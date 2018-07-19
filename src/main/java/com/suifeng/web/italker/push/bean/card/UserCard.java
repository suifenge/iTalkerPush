package com.suifeng.web.italker.push.bean.card;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;

public class UserCard {

    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String phone;
    @Expose
    private String portrait;
    @Expose
    private String desc;
    @Expose
    private int sex = 0;

    //用户关注人的数量
    @Expose
    private int follows;

    //关注用户的数量
    @Expose
    private int following;

    //自己与当前User的关系状态，是否已经关注了这个人
    @Expose
    private int isFollow;

    //用户信息最后的更新时间
    @Expose
    private LocalDateTime modifyAt;

}
