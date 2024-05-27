package com.fabiolourenco.catbreedsapp.ui.feature.breeds

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed
import com.fabiolourenco.catbreedsapp.core.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.base.MockitoException
import org.mockito.kotlin.given

@ExperimentalCoroutinesApi
class BreedsViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository
    private lateinit var viewModel: BreedsViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = BreedsViewModel(repository = repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // fetchBreeds
    // repositoryReturnsListOfBreeds
    // successIsReturned
    @Test
    fun fetchBreeds1() = runTest {
        val catBreeds = listOf(
            CatBreed(
                id = "1",
                name = "Breed1",
                origin = "Origin1",
                temperament = "Temperament1",
                description = "Description1",
                imageUrl = "ImageUrl1",
                lifeSpan = 12,
                isFavorite = true
            ),
            CatBreed(
                id = "2",
                name = "Breed2",
                origin = "Origin2",
                temperament = "Temperament2",
                description = "Description2",
                imageUrl = "ImageUrl2",
                lifeSpan = 12,
                isFavorite = false
            )
        )
        given(repository.getBreeds()).willReturn(catBreeds)

        viewModel.fetchBreedsOld()

        Mockito.verify(repository).getBreeds()
        val result = viewModel.getBreedsResultObservable.first()
        Assert.assertTrue(result is GetBreedsResult.Success)
        Assert.assertEquals(catBreeds, (result as GetBreedsResult.Success).breeds)
    }

    // fetchBreeds
    // repositoryReturnsEmptyList
    // emptyIsReturned
    @Test
    fun fetchBreeds2() = runTest {
        val catBreeds = emptyList<CatBreed>()
        given(repository.getBreeds()).willReturn(catBreeds)

        viewModel.fetchBreedsOld()

        Mockito.verify(repository).getBreeds()
        val result = viewModel.getBreedsResultObservable.first()
        Assert.assertTrue(result is GetBreedsResult.Empty)
    }

    // fetchBreeds
    // repositoryThrowsException
    // errorIsReturned
    @Test
    fun fetchBreeds3() = runTest {
        given(repository.getBreeds()).willThrow(MockitoException(""))

        viewModel.fetchBreedsOld()

        Mockito.verify(repository).getBreeds()
        val result = viewModel.getBreedsResultObservable.first()
        Assert.assertTrue(result is GetBreedsResult.Error)
    }

    // searchBreeds
    // repositoryReturnsListOfBreeds
    // successIsReturned
    @Test
    fun searchBreeds1() = runTest {
        val query = "Breed"
        val catBreeds = listOf(
            CatBreed(
                id = "1",
                name = "Breed1",
                origin = "Origin1",
                temperament = "Temperament1",
                description = "Description1",
                imageUrl = "ImageUrl1",
                lifeSpan = 12,
                isFavorite = true
            ),
            CatBreed(
                id = "2",
                name = "Breed2",
                origin = "Origin2",
                temperament = "Temperament2",
                description = "Description2",
                imageUrl = "ImageUrl2",
                lifeSpan = 12,
                isFavorite = false
            )
        )
        given(repository.searchBreedsByName(breedName = query)).willReturn(catBreeds)

        viewModel.searchBreedsOld(query)

        Mockito.verify(repository).searchBreedsByName(breedName = query)
        val result = viewModel.getBreedsResultObservable.first()
        Assert.assertTrue(result is GetBreedsResult.Success)
        Assert.assertEquals(catBreeds, (result as GetBreedsResult.Success).breeds)
    }

    // searchBreeds
    // repositoryReturnsEmptyList
    // emptySearchResultIsReturned
    @Test
    fun searchBreeds2() = runTest {
        val query = "Breed"
        val catBreeds = emptyList<CatBreed>()
        given(repository.searchBreedsByName(breedName = query)).willReturn(catBreeds)

        viewModel.searchBreedsOld(query)

        Mockito.verify(repository).searchBreedsByName(breedName = query)
        val result = viewModel.getBreedsResultObservable.first()
        Assert.assertTrue(result is GetBreedsResult.EmptySearchResult)
        Assert.assertEquals(query, (result as GetBreedsResult.EmptySearchResult).breedName)
    }

    // searchBreeds
    // repositoryThrowsException
    // errorIsReturned
    @Test
    fun searchBreeds3() = runTest {
        val query = "Breed"
        given(repository.searchBreedsByName(breedName = query)).willThrow(MockitoException(""))

        viewModel.searchBreedsOld(query)

        Mockito.verify(repository).searchBreedsByName(breedName = query)
        val result = viewModel.getBreedsResultObservable.first()
        Assert.assertTrue(result is GetBreedsResult.Error)
    }

    // updateFavoriteBreed
    // breedIsNotFavorite
    // repositoryAddFavoriteBreedCalled
    @Test
    fun updateFavoriteBreed1() = runTest {
        val catBreed = CatBreed(
            id = "1",
            name = "Breed1",
            origin = "Origin1",
            temperament = "Temperament1",
            description = "Description1",
            imageUrl = "ImageUrl1",
            lifeSpan = 12,
            isFavorite = false
        )

        viewModel.updateFavoriteBreed(breed = catBreed)

        Mockito.verify(repository).addFavoriteBreed(breed = catBreed)
    }

    // updateFavoriteBreed
    // breedIsFavorite
    // repositoryRemoveFavoriteBreedCalled
    @Test
    fun updateFavoriteBreed2() = runTest {
        val catBreed = CatBreed(
            id = "1",
            name = "Breed1",
            origin = "Origin1",
            temperament = "Temperament1",
            description = "Description1",
            imageUrl = "ImageUrl1",
            lifeSpan = 12,
            isFavorite = true
        )

        viewModel.updateFavoriteBreed(breed = catBreed)

        Mockito.verify(repository).removeFavoriteBreed(breed = catBreed)
    }
}