package com.twitter.simulation.android.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppHttpLoggingInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppConverter