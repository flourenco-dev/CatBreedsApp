package com.fabiolourenco.catbreedsapp.ui.feature.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fabiolourenco.catbreedsapp.core.Repository
import com.fabiolourenco.common.uiModel.CatBreed
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
class DetailsViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository
    private lateinit var viewModel: DetailsViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailsViewModel(repository = repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // fetchBreedById
    // repositoryReturnsBreed
    // successIsReturned
    @Test
    fun fetchBreedById1() = runTest {
        val breedId = "1"
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
        given(repository.getBreedById(breedId)).willReturn(catBreed)

        viewModel.fetchBreedById(breedId)

        Mockito.verify(repository).getBreedById(breedId)
        val result = viewModel.getBreedByIdResultObservable.first()
        Assert.assertTrue(result is GetBreedByIdResult.Success)
        Assert.assertEquals(catBreed, (result as GetBreedByIdResult.Success).breed)
    }

    // fetchBreedById
    // repositoryThrowsException
    // errorIsReturned
    @Test
    fun searchBreeds2() = runTest {
        val breedId = "1"
        given(repository.getBreedById(breedId)).willThrow(MockitoException(""))

        viewModel.fetchBreedById(breedId)

        Mockito.verify(repository).getBreedById(breedId)
        val result = viewModel.getBreedByIdResultObservable.first()
        Assert.assertTrue(result is GetBreedByIdResult.Error)
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