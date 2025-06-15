package com.example.mathTest.di.qualifiers

import javax.inject.Qualifier

/**
 * Qualifier for the main dispatcher.
 * This dispatcher is used for UI-related operations.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DispatcherMain
