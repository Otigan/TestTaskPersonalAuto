package com.example.personalauto.di

import com.example.personalauto.data.repository.AutoRepositoryImpl
import com.example.personalauto.data.repository.ManufacturerRepositoryImpl
import com.example.personalauto.data.repository.YearRepositoryImpl
import com.example.personalauto.domain.repository.AutoRepository
import com.example.personalauto.domain.repository.ManufacturerRepository
import com.example.personalauto.domain.repository.YearRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindManufacturerRepository(impl: ManufacturerRepositoryImpl): ManufacturerRepository

    @Binds
    @Singleton
    abstract fun bindAutoRepository(impl: AutoRepositoryImpl): AutoRepository

    @Binds
    @Singleton
    abstract fun bindYearRepository(impl: YearRepositoryImpl): YearRepository

}