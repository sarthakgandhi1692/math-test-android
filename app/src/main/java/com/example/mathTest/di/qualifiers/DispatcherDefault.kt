package com.example.mathTest.di.qualifiers

import javax.inject.Qualifier

/**
 * Qualifier for the default dispatcher.
 * This dispatcher is used for CPU-intensive tasks.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DispatcherDefault
