package com.suifeng.web.italker.push.bean.db

import org.hibernate.annotations.*

import javax.persistence.*
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Table
import java.time.LocalDateTime
import java.util.HashSet

/**
 * 用户模型，对应数据库表
 */

@Entity
@Table(name = "TB_USER")
class User {

    //这是一个主键
    @Id
    @PrimaryKeyJoinColumn
    //主键生成存储的类型为UUID
    @GeneratedValue(generator = "uuid")
    //把uuid的生成器定义为uuid2，uuid2是常规的UUID
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    //不允许更改，不允许为null
    @Column(updatable = false, nullable = false)
    var id: String? = null

    //用户名必须唯一
    @Column(nullable = false, length = 128, unique = true)
    var name: String? = null

    //电话必须唯一
    @Column(nullable = false, length = 62, unique = true)
    var phone: String? = null

    @Column(nullable = false)
    var password: String? = null

    //头像允许为空
    @Column
    var portrait: String? = null

    @Column
    var description: String? = null

    //性别有初始值，所以不能为空
    @Column(nullable = false)
    var sex = 0

    //可以拉取用户信息，所以必须唯一
    @Column(unique = true)
    var token: String? = null

    //用于推送的设备ID
    @Column
    var pushId: String? = null

    //定义为创建时间戳,在创建时就已经写入
    @CreationTimestamp
    @Column(nullable = false)
    var createAt = LocalDateTime.now()

    //定义为更新时间戳
    @UpdateTimestamp
    @Column(nullable = false)
    var updateAt = LocalDateTime.now()

    //最后一次收到消息的时间
    @Column
    var lastReceivedAt = LocalDateTime.now()

    //我关注的人的列表
    //对应数据库表字段为TB_USER_FOLLOW.originId
    @JoinColumn(name = "originId")
    //定义为懒加载，默认加载User信息的时候，并不查询这个集合
    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(fetch = FetchType.LAZY, cascade = [(CascadeType.ALL)])
    var following: Set<UserFollow> = HashSet()

    //关注我的人
    //对应数据库表字段为TB_USER_FOLLOW.targetId
    @JoinColumn(name = "targetId")
    //定义为懒加载，默认加载User信息的时候，并不查询这个集合
    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(fetch = FetchType.LAZY, cascade = [(CascadeType.ALL)])
    var followers: Set<UserFollow> = HashSet()

    //我所有创建的群
    @JoinColumn(name = "ownerId")
    //懒加载集合方式尽可能不加载具体数据
    @LazyCollection(LazyCollectionOption.EXTRA)
    //FetchType.LAZY 懒加载，加载用户信息时不加载这个集合
    @OneToMany(fetch = FetchType.LAZY, cascade = [(CascadeType.ALL)])
    var groups: Set<Group> = HashSet()
}
