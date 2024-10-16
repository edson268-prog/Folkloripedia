package com.edsondev26.folkloripedia.di

import com.edsondev26.folkloripedia.data.CategoryRepositoryImpl
import com.edsondev26.folkloripedia.data.EventRepositoryImpl
import com.edsondev26.folkloripedia.data.GameRepositoryImpl
import com.edsondev26.folkloripedia.data.PopulateRepositoryImpl
import com.edsondev26.folkloripedia.data.SuggestionRepositoryImpl
import com.edsondev26.folkloripedia.domain.CategoryRepository
import com.edsondev26.folkloripedia.domain.EventRepository
import com.edsondev26.folkloripedia.domain.GameRepository
import com.edsondev26.folkloripedia.domain.PopulateRepository
import com.edsondev26.folkloripedia.domain.SuggestionRepository
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
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl,
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindGameRepository(
        gameRepositoryImpl: GameRepositoryImpl,
    ): GameRepository

    @Binds
    @Singleton
    abstract fun bindSuggestionRepository(
        suggestionRepositoryImpl: SuggestionRepositoryImpl,
    ): SuggestionRepository

    @Binds
    @Singleton
    abstract fun bindEventRepository(
        eventRepositoryImpl: EventRepositoryImpl,
    ): EventRepository

    @Binds
    @Singleton
    abstract fun bindPopulateRepository(
        populateRepositoryImpl: PopulateRepositoryImpl
    ): PopulateRepository
}