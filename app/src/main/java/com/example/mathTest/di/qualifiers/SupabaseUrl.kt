package com.example.mathTest.di.qualifiers

import javax.inject.Qualifier

/**
 * Qualifier annotation for providing the Supabase URL.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SupabaseUrl
