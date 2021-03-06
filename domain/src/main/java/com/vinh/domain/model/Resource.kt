package com.vinh.domain.model

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */

data class Resource<out T>(val status: Status, val data: T?, val message: String?, val codeStatus: Int?= null) {
    val state: State =
        State(status, message)
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val resource: Resource<*>? = other as Resource<*>?

        return if (resource?.state == this.state && data != null) data == resource.data else resource?.data == null
    }

    override fun hashCode(): Int {
        var result = state.hashCode()
        result = 31 * result + (data?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Resource{" +
                "status=" + state.status +
                ", message='" + state.message + '\''.toString() +
                ", data=" + data +
                '}'.toString()
    }

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(message: String?): Resource<T> {
            return Resource(
                Status.ERROR,
                null,
                message
            )
        }

        fun <T> error(message: String?, codeStatus: Int?): Resource<T> {
            return Resource(
                Status.ERROR,
                null,
                message,
                codeStatus
            )
        }

        fun <T> loading(): Resource<T> {
            return Resource(
                Status.LOADING,
                null,
                null
            )
        }
    }
}