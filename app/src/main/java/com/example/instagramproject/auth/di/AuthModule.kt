package com.example.instagramproject.auth.di

import com.example.instagramproject.auth.data.AuthRepositoryImpl
import com.example.instagramproject.auth.domain.AuthRepository
import com.example.instagramproject.auth.domain.Authenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AuthModule {

    @Singleton
    @Provides
    fun provideAuthenticator(): Authenticator {
        return FirebaseAuthenticator()
    }

    @Singleton
    @Provides
    fun provideAuthRepository(authenticator: Authenticator): AuthRepository {
        return AuthRepositoryImpl(authenticator)
    }
}