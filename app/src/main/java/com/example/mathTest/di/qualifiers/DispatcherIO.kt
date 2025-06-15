package com.example.mathTest.di.qualifiers

import javax.inject.Qualifier
/**
 * Qualifier for I/O dispatcher.
 * This annotation is used to inject an I/O dispatcher into a class.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DispatcherIO
