package com.tsekhmeistruk.notary.widgets.util

import com.tsekhmeistruk.notary.widgets.Status

/**
 * Created by Roman Tsekhmeistruk on 10.03.2018.
 */
class DataResource<T>(val status: Status, val data: T?, val message: Throwable?) {

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }

        val resource = o as DataResource<*>?
        if (status !== resource!!.status) {
            return false
        }
        if (if (message != null) message != resource!!.message else resource!!.message != null) {
            return false
        }
        return if (data != null) data == resource.data else resource.data == null
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + (data?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}'
    }

    companion object {

        fun <T> success(data: T?): DataResource<T> {
            return DataResource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: Throwable, data: T?): DataResource<T> {
            return DataResource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): DataResource<T> {
            return DataResource(Status.LOADING, data, null)
        }
    }
}
