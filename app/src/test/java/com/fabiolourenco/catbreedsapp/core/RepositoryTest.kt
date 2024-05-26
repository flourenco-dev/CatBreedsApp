package com.fabiolourenco.catbreedsapp.core

import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed
import com.fabiolourenco.catbreedsapp.core.network.ApiHelper
import com.fabiolourenco.catbreedsapp.core.network.model.BreedImageModel
import com.fabiolourenco.catbreedsapp.core.network.model.BreedModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

class RepositoryTest {

    @Mock
    private lateinit var apiHelper: ApiHelper
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = RepositoryImpl(apiHelper = apiHelper)
    }

    // Tests follow AAA approach -> Arrange, Act and Assert
    @Test
    fun getCatBreeds_apiReturnsTwoBreedModels_twoUiBreedsAreReturned() = runBlocking {
        val breedModels = listOf(
            BreedModel(
                id = "1",
                name = "Breed1",
                origin = "Origin1",
                temperament = "Temperament1",
                description = "Description1",
                image = BreedImageModel(url = "ImageUrl1")
            ),
            BreedModel(
                id = "2",
                name = "Breed2",
                origin = "Origin2",
                temperament = "Temperament2",
                description = "Description2",
                image = BreedImageModel(url = "ImageUrl2")
            )
        )
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
        given(apiHelper.getBreeds()).willReturn(breedModels)

        val result = repository.getCatBreeds()

        Mockito.verify(apiHelper).getBreeds()
        Assert.assertEquals(catBreeds, result)
    }
}