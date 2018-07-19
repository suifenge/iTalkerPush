package com.suifeng.web.italker.push.bean.db

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.*
import java.time.LocalDateTime

/**
 * 群信息
 */

@Entity
@Table(name = "TB_GROUP")
class Group {

    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(updatable = false, nullable = false)
    var id: String? = null

    //群名称
    @Column(nullable = false)
    var name: String? = null

    //群描述
    @Column(nullable = false)
    var description: String? = null

    @Column(nullable = false)
    var picture: String? = null

    //定义为创建时间戳,在创建时就已经写入
    @CreationTimestamp
    @Column(nullable = false)
    var createAt = LocalDateTime.now()

    //定义为更新时间戳
    @UpdateTimestamp
    @Column(nullable = false)
    var updateAt = LocalDateTime.now()

    //群的创建者
    //optional：可选为false， 必须有一个创建者
    //fetch：加载方式FetchType.EAGER,急加载，意味着加载群的信息的时候就必须加载owner的信息
    //cascade：联级级别为ALL，所有的更改(更新、删除等) 都将进行关系更新
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "ownerId")
    private val owner: User? = null
    @Column(updatable = false, insertable = false, nullable = false)
    private val ownerId: String? = null
}
