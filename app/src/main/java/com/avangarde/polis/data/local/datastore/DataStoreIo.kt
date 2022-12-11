package com.avangarde.polis.data.local.datastore

interface DataStoreIo<T> {
    suspend fun read(): T?
    suspend fun save(value: T)
}
