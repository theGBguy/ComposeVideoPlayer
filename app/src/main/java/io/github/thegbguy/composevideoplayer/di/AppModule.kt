package io.github.thegbguy.composevideoplayer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.thegbguy.composevideoplayer.data.repository.VideoRepositoryImpl
import io.github.thegbguy.composevideoplayer.domain.repository.VideoRepository
import io.github.thegbguy.composevideoplayer.domain.usecase.GetVideoFeedUseCase
import javax.inject.Singleton

/**
 * Hilt module for providing app-wide dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideVideoRepository(): VideoRepository = VideoRepositoryImpl()

    @Provides
    @Singleton
    fun provideGetVideoFeedUseCase(
        repository: VideoRepository
    ): GetVideoFeedUseCase = GetVideoFeedUseCase(repository)
}