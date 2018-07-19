package com.suifeng.web.italker.push.bean.db

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.*
import java.time.LocalDateTime

/**
 * 用户关系的Model
 * 用于用户之间进行还有关系的实现
 */
@Entity
@Table(name = "TB_USER_FOLLOW")
class UserFollow {

    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(updatable = false, nullable = false)
    var id: String? = null

    //定义一个发起人
    //多对1 -> 你可以关注很多人，你的每一次关注都是一条记录
    //可以创建很多和关注的消息
    @ManyToOne(optional = false)    //不可选，必须存储，一条关注记录一定要有
    //定义关联的表字段名为originId，对应的是User.id
    @JoinColumn(name = "originId")
    var origin: User? = null
    @Column(nullable = false, updatable = false, insertable = false)
    var originId: String? = null

    //定义关注的目标
    @ManyToOne(optional = false)
    @JoinColumn(name = "targetId")
    var target: User? = null
    @Column(nullable = false, updatable = false, insertable = false)
    var targetId: String? = null

    //别名，也就是对target的备注名,别名可以为空
    @Column
    var alias: String? = null

    @CreationTimestamp
    @Column(nullable = false)
    var createAt = LocalDateTime.now()

    @UpdateTimestamp
    @Column(nullable = false)
    var updateAt = LocalDateTime.now()
}
