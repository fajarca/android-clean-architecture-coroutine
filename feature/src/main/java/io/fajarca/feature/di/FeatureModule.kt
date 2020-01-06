package io.fajarca.feature.di

import android.text.GetChars
import dagger.Module
import dagger.Provides
import io.fajarca.core.common.CoroutineDispatcherProvider
import io.fajarca.core.database.CharacterDao
import io.fajarca.core.database.MarvelDatabase
import io.fajarca.core.network.service.ApiService
import io.fajarca.feature.data.mapper.CharactersMapper
import io.fajarca.feature.data.repository.CharacterRepositoryImpl
import io.fajarca.feature.data.source.remote.CharacterRemoteDataSource
import io.fajarca.feature.domain.repository.CharacterRepository
import io.fajarca.feature.domain.usecase.GetCharactersUseCase
import io.fajarca.feature.ui.home.HomeViewModel


@Module
class FeatureModule {

    @Provides
    @FeatureScope
    fun provideCharacterDao(db: MarvelDatabase) : CharacterDao = db.characterDao()

    @Provides
    @FeatureScope
    fun provideMapper() : CharactersMapper = CharactersMapper()


    @Provides
    @FeatureScope
    fun provideUseCase(repository: CharacterRepository) : GetCharactersUseCase = GetCharactersUseCase(repository)

    @Provides
    @FeatureScope
    fun provideViewModel(useCase: GetCharactersUseCase) : HomeViewModel = HomeViewModel(useCase)

    /*@Provides
    @FeatureScope
    fun provideDispatcher() : CoroutineDispatcherProvider = CoroutineDispatcherProvider()

    @Provides
    @FeatureScope
    fun provideDataSource(apiService: ApiService) : CharacterRemoteDataSource = CharacterRemoteDataSource(apiService)

    @Provides
    @FeatureScope
    fun provideRepository(dispatcher : CoroutineDispatcherProvider, mapper : CharactersMapper, dao : CharacterDao, dataSource: CharacterRemoteDataSource) = CharacterRepositoryImpl(dispatcher, mapper, dao,dataSource )*/

}