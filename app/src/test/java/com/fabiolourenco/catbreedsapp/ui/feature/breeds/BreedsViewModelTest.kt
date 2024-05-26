package com.fabiolourenco.catbreedsapp.ui.feature.breeds

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed
import com.fabiolourenco.catbreedsapp.common.utils.UiState
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

    @Test
    fun fetchBreeds_repositoryReturnsListOfBreeds_successIsReturned() = runTest {
        val catBreeds = listOf(
            CatBreed(
                name = "Breed1",
                origin = "Origin1",
                imageUrl = "ImageUrl1"
            ),
            CatBreed(
                name = "Breed2",
                origin = "Origin2",
                imageUrl = "ImageUrl2"
            )
        )
        given(repository.getCatBreeds()).willReturn(catBreeds)

        viewModel.fetchBreeds()

        Mockito.verify(repository).getCatBreeds()
        val result = viewModel.breedsStateObservable.first()
        Assert.assertTrue(result is UiState.Success)
        Assert.assertEquals(catBreeds, (result as UiState.Success).breeds)
    }

    @Test
    fun fetchBreeds_repositoryThrowsException_errorIsReturned() = runTest {
        given(repository.getCatBreeds()).willThrow(MockitoException(""))

        viewModel.fetchBreeds()

        Mockito.verify(repository).getCatBreeds()
        val result = viewModel.breedsStateObservable.first()
        Assert.assertTrue(result is UiState.Error)
    }
}