package com.suifeng.web.italker.push.bean.db

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.*
import java.time.LocalDateTime

/**
 * Message模型，对应数据库表
 */
@Entity
@Table(name = "TB_MESSAGE")
class Message {

    @Id
    @PrimaryKeyJoinColumn
    //这里不自动生成UUID，Id由代码写入，由客户端负责生成
    //避免复杂的服务器和客户端的映射关系
    //@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(updatable = false, nullable = false)
    var id: String? = null

    //内容不允许为空，类型为text
    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String? = null

    //附件
    @Column
    var attach: String? = null

    //消息类型
    @Column(nullable = false)
    var type: Int = 0

    //发送者，不可为空
    //多个消息对应一个发送者
    @JoinColumn(name = "senderId")
    @ManyToOne(optional = false)
    var sender: User? = null
    @Column(nullable = false, updatable = false, insertable = false)
    var senderId: String? = null

    //接收者，可为空
    @ManyToOne
    @JoinColumn(name = "receiverId")
    var receiver: User? = null
    @Column(updatable = false, insertable = false)
    var receiverId: String? = null

    //一个群可以接收多个消息
    @ManyToOne
    @JoinColumn(name = "groupId")
    var group: Group? = null
    @Column(updatable = false, insertable = false)
    var groupId: String? = null


    //定义为创建时间戳,在创建时就已经写入
    @CreationTimestamp
    @Column(nullable = false)
    var createAt = LocalDateTime.now()

    //定义为更新时间戳
    @UpdateTimestamp
    @Column(nullable = false)
    var updateAt = LocalDateTime.now()

    companion object {

        val TYPE_STR = 1   //字符串类型
        val TYPE_PIC = 2   //图片类型
        val TYPE_FILE = 3  //文件类型
        val TYPE_AUDIO = 4 //语音类型
    }
}
