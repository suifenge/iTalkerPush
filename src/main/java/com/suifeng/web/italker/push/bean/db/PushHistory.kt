package com.suifeng.web.italker.push.bean.db

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.*
import java.time.LocalDateTime

/**
 * 消息推送
 */

@Entity
@Table(name = "TB_PUSH_HISTORY")
class PushHistory {

    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(updatable = false, nullable = false)
    var id: String? = null

    //推送的具体实体，存储的都是JSON字符串
    //BLOB是比TEXT更多的一个大字段类型
    @Column(nullable = false, columnDefinition = "BLOB")
    var entity: String? = null

    //推送的实体类型
    @Column(nullable = false)
    var entityType: Int = 0

    //接收者，不允许为空
    //一个接受者可以接收很多推送的消息
    //加载一条推送消息的时候 直接加载用户信息
    @JoinColumn(name = "receiverId")
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = [(CascadeType.ALL)])
    var receiver: User? = null
    @Column(nullable = false, updatable = false, insertable = false)
    var receiverId: String? = null

    @JoinColumn(name = "senderId")
    @ManyToOne(fetch = FetchType.EAGER, cascade = [(CascadeType.ALL)])
    var sender: User? = null
    @Column(updatable = false, insertable = false)
    var senderId: String? = null

    //接收者当前状态下的设备推送Id
    @Column
    var receiverPushId: String? = null

    @CreationTimestamp
    @Column(nullable = false)
    var createAt = LocalDateTime.now()

    //定义为更新时间戳
    @UpdateTimestamp
    @Column(nullable = false)
    var updateAt = LocalDateTime.now()

    //消息到达的时间,可为空
    @Column(nullable = false)
    var arriveAt: LocalDateTime? = null
}
