package com.suifeng.web.italker.push.bean.db

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.*
import java.time.LocalDateTime

/**
 * 申请记录表
 */

@Entity
@Table(name = "TB_APPLY")
class Apply {

    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(updatable = false, nullable = false)
    var id: String? = null

    //对申请进行描述
    @Column(nullable = false)
    var description: String? = null

    //附件，可以附带图片地址或者其他
    @Column(columnDefinition = "TEXT")
    var attach: String? = null

    //当前申请的类型
    @Column(nullable = false)
    var type: Int = 0

    //目标id
    //type->TYPE_ADD_USER: User.id
    //type->TYPE_ADD_GROUP: Group.id
    @Column(nullable = false)
    var targetId: String? = null

    @CreationTimestamp
    @Column(nullable = false)
    var createAt = LocalDateTime.now()

    //定义为更新时间戳
    @UpdateTimestamp
    @Column(nullable = false)
    var updateAt = LocalDateTime.now()

    //申请人 可为空，是系统信息
    @ManyToOne
    @JoinColumn(name = "applicantId")
    var applicant: User? = null
    @Column(updatable = false, insertable = false)
    var applicantId: String? = null

    companion object {

        val TYPE_ADD_USER = 1  //添加用户
        val TYPE_ADD_GROUP = 2 //加入群
    }
}
