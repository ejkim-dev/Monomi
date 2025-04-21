package com.example.monomi.core.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val monomiAppDispatcher: MonomiAppDispatchers)

enum class MonomiAppDispatchers {
    IO
}
