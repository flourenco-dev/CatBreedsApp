package com.fabiolourenco.catbreedsapp.core

import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed
import com.fabiolourenco.catbreedsapp.core.network.ApiHelper
import com.fabiolourenco.catbreedsapp.core.network.model.BreedImageModel
import com.fabiolourenco.catbreedsapp.core.network.model.BreedModel
import com.fabiolourenco.catbreedsapp.core.storage.StorageHelper
import com.fabiolourenco.catbreedsapp.core.storage.database.entity.FavoriteBreedEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

// Tests follow AAA approach -> Arrange, Act and Assert
class RepositoryTest {

    @Mock
    private lateinit var apiHelper: ApiHelper

    @Mock
    private lateinit var storageHelper: StorageHelper
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = RepositoryImpl(
            apiHelper = apiHelper,
            storageHelper = storageHelper
        )
    }

    // getBreeds
    // apiReturnsTwoBreedModelsAndStorageReturnsOneFavorite
    // twoUiBreedsAreReturned
    @Test
    fun getBreeds1() = runBlocking {
        val breedModels = listOf(
            BreedModel(
                id = "1",
                name = "Breed1",
                origin = "Origin1",
                temperament = "Temperament1",
                description = "Description1",
                image = BreedImageModel(url = "ImageUrl1"),
                lifeSpan = "1 - 10"
            ),
            BreedModel(
                id = "2",
                name = "Breed2",
                origin = "Origin2",
                temperament = "Temperament2",
                description = "Description2",
                image = BreedImageModel(url = "ImageUrl2"),
                lifeSpan = "11 - 12"
            )
        )
        val favoriteBreedEntities = listOf(
            FavoriteBreedEntity(
                id = "1",
                name = "Breed1",
                origin = "Origin1",
                temperament = "Temperament1",
                description = "Description1",
                imageUrl = "ImageUrl1",
                lifeSpan = 10
            )
        )
        val catBreeds = listOf(
            CatBreed(
                id = "1",
                name = "Breed1",
                origin = "Origin1",
                temperament = "Temperament1",
                description = "Description1",
                imageUrl = "ImageUrl1",
                lifeSpan = 10,
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
        given(apiHelper.getBreeds()).willReturn(breedModels)
        given(storageHelper.getAllFavoriteBreeds()).willReturn(favoriteBreedEntities)

        val result = repository.getBreeds()

        Mockito.verify(apiHelper).getBreeds()
        Mockito.verify(storageHelper).getAllFavoriteBreeds()
        Assert.assertEquals(catBreeds, result)
    }

    // searchBreedsByName
    // apiReturnsTwoBreedModelsAndStorageReturnsOneFavorite
    // twoUiBreedsAreReturned
    @Test
    fun searchBreedsByName1() = runBlocking {
        val query = "Breed"
        val breedModels = listOf(
            BreedModel(
                id = "1",
                name = "Breed1",
                origin = "Origin1",
                temperament = "Temperament1",
                description = "Description1",
                image = BreedImageModel(url = "ImageUrl1"),
                lifeSpan = "1 - 10"
            ),
            BreedModel(
                id = "2",
                name = "Breed2",
                origin = "Origin2",
                temperament = "Temperament2",
                description = "Description2",
                image = BreedImageModel(url = "ImageUrl2"),
                lifeSpan = "11 - 12"
            )
        )
        val favoriteBreedEntities = listOf(
            FavoriteBreedEntity(
                id = "1",
                name = "Breed1",
                origin = "Origin1",
                temperament = "Temperament1",
                description = "Description1",
                imageUrl = "ImageUrl1",
                lifeSpan = 10
            )
        )
        val catBreeds = listOf(
            CatBreed(
                id = "1",
                name = "Breed1",
                origin = "Origin1",
                temperament = "Temperament1",
                description = "Description1",
                imageUrl = "ImageUrl1",
                lifeSpan = 10,
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
        given(apiHelper.getBreedsByName(breedName = query)).willReturn(breedModels)
        given(storageHelper.getAllFavoriteBreeds()).willReturn(favoriteBreedEntities)

        val result = repository.searchBreedsByName(breedName = query)

        Mockito.verify(apiHelper).getBreedsByName(breedName = query)
        Assert.assertEquals(catBreeds, result)
    }

    // addFavoriteBreed
    // storageAddFavoriteCalled
    @Test
    fun addFavoriteBreed1() = runBlocking {
        val catBreed = CatBreed(
            id = "1",
            name = "Breed1",
            origin = "Origin1",
            temperament = "Temperament1",
            description = "Description1",
            imageUrl = "ImageUrl1",
            lifeSpan = 12
        )
        val favoriteBreedEntity = FavoriteBreedEntity(
            id = "1",
            name = "Breed1",
            origin = "Origin1",
            temperament = "Temperament1",
            description = "Description1",
            imageUrl = "ImageUrl1",
            lifeSpan = 12
        )

        repository.addFavoriteBreed(catBreed)

        Mockito.verify(storageHelper).addFavoriteBreed(favoriteBreedEntity)
    }

    // removeFavoriteBreed
    // storageRemoveFavoriteCalled
    @Test
    fun removeFavoriteBreed1() = runBlocking {
        val catBreed = CatBreed(
            id = "1",
            name = "Breed1",
            origin = "Origin1",
            temperament = "Temperament1",
            description = "Description1",
            imageUrl = "ImageUrl1",
            lifeSpan = 12
        )
        val favoriteBreedEntity = FavoriteBreedEntity(
            id = "1",
            name = "Breed1",
            origin = "Origin1",
            temperament = "Temperament1",
            description = "Description1",
            imageUrl = "ImageUrl1",
            lifeSpan = 12
        )

        repository.removeFavoriteBreed(catBreed)

        Mockito.verify(storageHelper).removeFavoriteBreed(favoriteBreedEntity)
    }
}