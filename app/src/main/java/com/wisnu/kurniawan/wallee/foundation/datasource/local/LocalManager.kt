package com.wisnu.kurniawan.wallee.foundation.datasource.local

import com.wisnu.kurniawan.wallee.foundation.di.DiName
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.CoroutineDispatcher

class LocalManager @Inject constructor(
    @Named(DiName.DISPATCHER_IO) private val dispatcher: CoroutineDispatcher,
    private val walleeReadDao: WalleeReadDao,
    private val walleeWriteDao: WalleeWriteDao
) {

}
