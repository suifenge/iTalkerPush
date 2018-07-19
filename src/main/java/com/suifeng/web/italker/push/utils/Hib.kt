package com.suifeng.web.italker.push.utils

import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistry
import org.hibernate.boot.registry.StandardServiceRegistryBuilder

object Hib {
    // 全局SessionFactory
    private var sessionFactory: SessionFactory? = null

    init {
        // 静态初始化sessionFactory
        init()
    }

    private fun init() {
        // 从hibernate.cfg.xml文件初始化
        val registry = StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build()
        try {
            // build 一个sessionFactory
            sessionFactory = MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory()
        } catch (e: Exception) {
            e.printStackTrace()
            // 错误则打印输出，并销毁
            StandardServiceRegistryBuilder.destroy(registry)
        }

    }

    /**
     * 获取全局的SessionFactory
     *
     * @return SessionFactory
     */
    fun sessionFactory(): SessionFactory? {
        return sessionFactory
    }

    /**
     * 从SessionFactory中得到一个Session会话
     *
     * @return Session
     */
    fun session(): Session {
        return sessionFactory!!.currentSession
    }

    /**
     * 关闭sessionFactory
     */
    fun closeFactory() {
        if (sessionFactory != null) {
            sessionFactory!!.close()
        }
    }


    // 用户的实际的操作的一个接口
    interface QueryOnly {
        fun query(session: Session)
    }

    // 简化Session事物操的一个工具方法
    fun queryOnly(query: QueryOnly) {
        // 重开一个Session
        val session = sessionFactory!!.openSession()
        // 开启事物
        val transaction = session.beginTransaction()

        try {
            // 调用传递进来的接口，
            // 并调用接口的方法把Session传递进去
            query.query(session)
            // 提交
            transaction.commit()
        } catch (e: Exception) {
            e.printStackTrace()
            // 回滚
            try {
                transaction.rollback()
            } catch (e1: RuntimeException) {
                e1.printStackTrace()
            }

        } finally {
            // 无论成功失败，都需要关闭Session
            session.close()
        }
    }


    // 用户的实际的操作的一个接口
    // 具有返回值T
    interface Query<T> {
        fun query(session: Session): T
    }

    // 简化Session操作的工具方法，
    // 具有一个返回值
    fun <T> query(query: Query<T>): T? {
        // 重开一个Session
        val session = sessionFactory!!.openSession()
        // 开启事物
        val transaction = session.beginTransaction()

        var t: T? = null
        try {
            // 调用传递进来的接口，
            // 并调用接口的方法把Session传递进去
            t = query.query(session)
            // 提交
            transaction.commit()
        } catch (e: Exception) {
            e.printStackTrace()
            // 回滚
            try {
                transaction.rollback()
            } catch (e1: RuntimeException) {
                e1.printStackTrace()
            }

        } finally {
            // 无论成功失败，都需要关闭Session
            session.close()
        }

        return t
    }


}
