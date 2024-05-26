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
        given(repository.getBreeds()).willReturn(catBreeds)

        viewModel.fetchBreeds()

        Mockito.verify(repository).getBreeds()
        val result = viewModel.getBreedsResultObservable.first()
        Assert.assertTrue(result is GetBreedsResult.Success)
        Assert.assertEquals(catBreeds, (result as GetBreedsResult.Success).breeds)
    }

    @Test
    fun fetchBreeds_repositoryReturnsEmptyList_emptyIsReturned() = runTest {
        val catBreeds = emptyList<CatBreed>()
        given(repository.getBreeds()).willReturn(catBreeds)

        viewModel.fetchBreeds()

        Mockito.verify(repository).getBreeds()
        val result = viewModel.getBreedsResultObservable.first()
        Assert.assertTrue(result is GetBreedsResult.Empty)
    }

    @Test
    fun fetchBreeds_repositoryThrowsException_errorIsReturned() = runTest {
        given(repository.getBreeds()).willThrow(MockitoException(""))

        viewModel.fetchBreeds()

        Mockito.verify(repository).getBreeds()
        val result = viewModel.getBreedsResultObservable.first()
        Assert.assertTrue(result is GetBreedsResult.Error)
    }

    @Test
    fun searchBreeds_repositoryReturnsListOfBreeds_successIsReturned() = runTest {
        val query = "Breed"
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
        given(repository.searchBreedsByName(breedName = query)).willReturn(catBreeds)

        viewModel.searchBreeds(query)

        Mockito.verify(repository).searchBreedsByName(breedName = query)
        val result = viewModel.getBreedsResultObservable.first()
        Assert.assertTrue(result is GetBreedsResult.Success)
        Assert.assertEquals(catBreeds, (result as GetBreedsResult.Success).breeds)
    }

    @Test
    fun searchBreeds_repositoryReturnsEmptyList_emptySearchResultIsReturned() = runTest {
        val query = "Breed"
        val catBreeds = emptyList<CatBreed>()
        given(repository.searchBreedsByName(breedName = query)).willReturn(catBreeds)

        viewModel.searchBreeds(query)

        Mockito.verify(repository).searchBreedsByName(breedName = query)
        val result = viewModel.getBreedsResultObservable.first()
        Assert.assertTrue(result is GetBreedsResult.EmptySearchResult)
        Assert.assertEquals(query, (result as GetBreedsResult.EmptySearchResult).breedName)
    }

    @Test
    fun searchBreeds_repositoryThrowsException_errorIsReturned() = runTest {
        val query = "Breed"
        given(repository.searchBreedsByName(breedName = query)).willThrow(MockitoException(""))

        viewModel.searchBreeds(query)

        Mockito.verify(repository).searchBreedsByName(breedName = query)
        val result = viewModel.getBreedsResultObservable.first()
        Assert.assertTrue(result is GetBreedsResult.Error)
    }
}