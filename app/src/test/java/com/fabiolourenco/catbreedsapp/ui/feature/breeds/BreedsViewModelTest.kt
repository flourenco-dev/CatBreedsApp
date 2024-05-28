package com.fabiolourenco.catbreedsapp.ui.feature.breeds

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed
import com.fabiolourenco.catbreedsapp.core.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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
        given(repository.fetchBreeds()).willReturn(Unit)

        viewModel.fetchBreeds()

        Mockito.verify(repository).fetchBreeds()
        val result = viewModel.fetchBreedsResultObservable.first()
        Assert.assertTrue(result is FetchBreedsResult.Success)
    }

    // fetchBreeds
    // repositoryThrowsException
    // errorIsReturned
    @Test
    fun fetchBreeds2() = runTest {
        given(repository.fetchBreeds()).willThrow(MockitoException(""))

        viewModel.fetchBreeds()

        Mockito.verify(repository).fetchBreeds()
        val result = viewModel.fetchBreedsResultObservable.first()
        Assert.assertTrue(result is FetchBreedsResult.Error)
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
        given(repository.getBreedsByNameObservable(breedName = query))
            .willReturn(flowOf(catBreeds))
        given(repository.fetchBreedsByName(breedName = query)).willReturn(Unit)

        viewModel.searchBreeds(query)

        Mockito.verify(repository).fetchBreedsByName(breedName = query)
        val result = viewModel.fetchBreedsByNameResultObservable.first()
        Assert.assertTrue(result is FetchBreedsByNameResult.Success)
        val observableValue = viewModel.breedsByNameObservable.first()
        Assert.assertEquals(catBreeds, observableValue)
    }

    // searchBreeds
    // repositoryThrowsException
    // errorIsReturned
    @Test
    fun searchBreeds2() = runTest {
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
        given(repository.getBreedsByNameObservable(breedName = query))
            .willReturn(flowOf(catBreeds))
        given(repository.fetchBreedsByName(breedName = query)).willThrow(MockitoException(""))

        viewModel.searchBreeds(query)

        Mockito.verify(repository).fetchBreedsByName(breedName = query)
        val result = viewModel.fetchBreedsByNameResultObservable.first()
        Assert.assertTrue(result is FetchBreedsByNameResult.Error)
        Assert.assertEquals(query, (result as FetchBreedsByNameResult.Error).breedName)
        val observableValue = viewModel.breedsByNameObservable.first()
        Assert.assertEquals(catBreeds, observableValue)
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