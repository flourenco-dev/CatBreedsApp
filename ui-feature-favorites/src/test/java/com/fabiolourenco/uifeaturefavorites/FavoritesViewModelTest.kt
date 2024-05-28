package com.fabiolourenco.uifeaturefavorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fabiolourenco.common.uiModel.CatBreed
import com.fabiolourenco.core.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import org.mockito.kotlin.never

@ExperimentalCoroutinesApi
class FavoritesViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository
    private lateinit var viewModel: FavoritesViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = FavoritesViewModel(repository = repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // removeFavoriteBreed
    // breedIsFavorite
    // repositoryRemoveFavoriteBreedCalled
    @Test
    fun removeFavoriteBreed1() = runTest {
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

        viewModel.removeFavoriteBreed(breed = catBreed)

        Mockito.verify(repository).removeFavoriteBreed(breed = catBreed)
    }

    // removeFavoriteBreed
    // breedIsNotFavorite
    // repositoryAddFavoriteBreedNotCalled
    @Test
    fun removeFavoriteBreed2() = runTest {
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

        viewModel.removeFavoriteBreed(breed = catBreed)

        Mockito.verify(repository).removeFavoriteBreed(breed = catBreed)
        Mockito.verify(repository, never()).addFavoriteBreed(breed = catBreed)
    }

    // getAverageLifeSpan
    // twoBreedsWith12And15LifeSpan
    // 13.5IsReturned
    @Test
    fun getAverageLifeSpan1() = runTest {
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
                lifeSpan = 15,
                isFavorite = false
            )
        )

        val result = viewModel.getAverageLifeSpan(catBreeds)

        Assert.assertEquals(13.5f, result.toFloat())
    }
}