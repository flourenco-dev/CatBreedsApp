package com.fabiolourenco.catbreedsapp.core

import com.fabiolourenco.catbreedsapp.core.network.ApiHelper
import com.fabiolourenco.catbreedsapp.core.network.model.BreedImageModel
import com.fabiolourenco.catbreedsapp.core.network.model.BreedModel
import com.fabiolourenco.catbreedsapp.core.storage.StorageHelper
import com.fabiolourenco.catbreedsapp.core.storage.database.entity.BreedEntity
import com.fabiolourenco.catbreedsapp.core.storage.database.entity.FavoriteBreedEntity
import com.fabiolourenco.common.uiModel.CatBreed
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.never
import org.mockito.kotlin.times

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

    // getBreedsObservable
    // storageReturnsTwoBreedEntitiesAndOneIsFavorite
    // twoUiBreedsAreReturned
    @Test
    fun getBreedsObservable1() = runBlocking {
        val breedEntities = listOf(
            BreedEntity(
                id = "1",
                name = "Breed1",
                origin = "Origin1",
                temperament = "Temperament1",
                description = "Description1",
                imageUrl = "ImageUrl1",
                lifeSpan = 10,
                isFavorite = true
            ),
            BreedEntity(
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
        given(storageHelper.getAllBreedsObservable()).willReturn(flowOf(breedEntities))

        val result = repository.getBreedsObservable()

        Mockito.verify(storageHelper).getAllBreedsObservable()
        Assert.assertEquals(catBreeds, result.first())
    }

    // fetchBreeds
    // apiReturnsTwoBreedModelsAndStorageReturnsOneIsFavorite
    // twoBreedEntitiesAreStored
    @Test
    fun fetchBreeds1() {
        runBlocking {
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
            val favoriteEntityId1 = "1"
            val favoriteEntityId2 = "2"
            val breedEntities = listOf(
                BreedEntity(
                    id = "1",
                    name = "Breed1",
                    origin = "Origin1",
                    temperament = "Temperament1",
                    description = "Description1",
                    imageUrl = "ImageUrl1",
                    lifeSpan = 10,
                    isFavorite = true
                ),
                BreedEntity(
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
            given(storageHelper.isFavoriteBreed(favoriteEntityId1)).willReturn(true)
            given(storageHelper.isFavoriteBreed(favoriteEntityId2)).willReturn(false)
            given(storageHelper.getAllBreeds()).willReturn(breedEntities)

            repository.fetchBreeds()

            Mockito.verify(apiHelper).getBreeds()
            Mockito.verify(storageHelper, times(2)).isFavoriteBreed(any())
            Mockito.verify(storageHelper).addBreeds(breedEntities)
            Mockito.verify(storageHelper).getAllBreeds()
            Mockito.verify(storageHelper, never()).removeBreeds(any())
        }
    }

    // fetchBreeds
    // apiReturnsOneBreedModelAndStorageReturnsTwoBreedEntities
    // oneBreedEntityIsStoredAndOneDeleted
    @Test
    fun fetchBreeds2() {
        runBlocking {
            val breedModels = listOf(
                BreedModel(
                    id = "1",
                    name = "Breed1",
                    origin = "Origin1",
                    temperament = "Temperament1",
                    description = "Description1",
                    image = BreedImageModel(url = "ImageUrl1"),
                    lifeSpan = "1 - 10"
                )
            )
            val favoriteEntityId1 = "1"
            val convertedEntities = listOf(
                BreedEntity(
                    id = "1",
                    name = "Breed1",
                    origin = "Origin1",
                    temperament = "Temperament1",
                    description = "Description1",
                    imageUrl = "ImageUrl1",
                    lifeSpan = 10,
                    isFavorite = true
                )
            )
            val localEntities = listOf(
                BreedEntity(
                    id = "1",
                    name = "Breed1",
                    origin = "Origin1",
                    temperament = "Temperament1",
                    description = "Description1",
                    imageUrl = "ImageUrl1",
                    lifeSpan = 10,
                    isFavorite = true
                ),
                BreedEntity(
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
            val toDeleteEntities = listOf(
                BreedEntity(
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
            given(storageHelper.isFavoriteBreed(favoriteEntityId1)).willReturn(true)
            given(storageHelper.getAllBreeds()).willReturn(localEntities)

            repository.fetchBreeds()

            Mockito.verify(apiHelper).getBreeds()
            Mockito.verify(storageHelper).isFavoriteBreed(favoriteEntityId1)
            Mockito.verify(storageHelper).addBreeds(convertedEntities)
            Mockito.verify(storageHelper).getAllBreeds()
            Mockito.verify(storageHelper).removeBreeds(toDeleteEntities)
        }
    }

    // getBreedsByNameObservable
    // storageReturnsTwoBreedEntitiesAndOneIsFavorite
    // twoUiBreedsAreReturned
    @Test
    fun getBreedsByNameObservable1() = runBlocking {
        val query = "Breed"
        val breedEntities = listOf(
            BreedEntity(
                id = "1",
                name = "Breed1",
                origin = "Origin1",
                temperament = "Temperament1",
                description = "Description1",
                imageUrl = "ImageUrl1",
                lifeSpan = 10,
                isFavorite = true
            ),
            BreedEntity(
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
        given(storageHelper.getBreedsByNameObservable(query)).willReturn(flowOf(breedEntities))

        val result = repository.getBreedsByNameObservable(query)

        Mockito.verify(storageHelper).getBreedsByNameObservable(query)
        Assert.assertEquals(catBreeds, result.first())
    }

    // fetchBreedsByName
    // apiReturnsTwoBreedModelsAndStorageReturnsOneIsFavorite
    // twoBreedEntitiesAreStored
    @Test
    fun fetchBreedsByName1() {
        runBlocking {
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
            val favoriteEntityId1 = "1"
            val favoriteEntityId2 = "2"
            val breedEntities = listOf(
                BreedEntity(
                    id = "1",
                    name = "Breed1",
                    origin = "Origin1",
                    temperament = "Temperament1",
                    description = "Description1",
                    imageUrl = "ImageUrl1",
                    lifeSpan = 10,
                    isFavorite = true
                ),
                BreedEntity(
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
            given(apiHelper.getBreedsByName(query)).willReturn(breedModels)
            given(storageHelper.isFavoriteBreed(favoriteEntityId1)).willReturn(true)
            given(storageHelper.isFavoriteBreed(favoriteEntityId2)).willReturn(false)
            given(storageHelper.getBreedsByName(query)).willReturn(breedEntities)

            repository.fetchBreedsByName(query)

            Mockito.verify(apiHelper).getBreedsByName(query)
            Mockito.verify(storageHelper, times(2)).isFavoriteBreed(any())
            Mockito.verify(storageHelper).addBreeds(breedEntities)
            Mockito.verify(storageHelper).getBreedsByName(query)
            Mockito.verify(storageHelper, never()).removeBreeds(any())
        }
    }

    // fetchBreedsByName
    // apiReturnsOneBreedModelAndStorageReturnsTwoBreedEntities
    // oneBreedEntityIsStoredAndOneDeleted
    @Test
    fun fetchBreedsByName2() {
        runBlocking {
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
                )
            )
            val favoriteEntityId1 = "1"
            val convertedEntities = listOf(
                BreedEntity(
                    id = "1",
                    name = "Breed1",
                    origin = "Origin1",
                    temperament = "Temperament1",
                    description = "Description1",
                    imageUrl = "ImageUrl1",
                    lifeSpan = 10,
                    isFavorite = true
                )
            )
            val localEntities = listOf(
                BreedEntity(
                    id = "1",
                    name = "Breed1",
                    origin = "Origin1",
                    temperament = "Temperament1",
                    description = "Description1",
                    imageUrl = "ImageUrl1",
                    lifeSpan = 10,
                    isFavorite = true
                ),
                BreedEntity(
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
            val toDeleteEntities = listOf(
                BreedEntity(
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
            given(apiHelper.getBreedsByName(query)).willReturn(breedModels)
            given(storageHelper.isFavoriteBreed(favoriteEntityId1)).willReturn(true)
            given(storageHelper.getBreedsByName(query)).willReturn(localEntities)

            repository.fetchBreedsByName(query)

            Mockito.verify(apiHelper).getBreedsByName(query)
            Mockito.verify(storageHelper).isFavoriteBreed(favoriteEntityId1)
            Mockito.verify(storageHelper).addBreeds(convertedEntities)
            Mockito.verify(storageHelper).getBreedsByName(query)
            Mockito.verify(storageHelper).removeBreeds(toDeleteEntities)
        }
    }

    // addFavoriteBreed
    // storageUpdateBreedCalled
    // storageAddFavoriteBreedCalled
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
        val breedEntity = BreedEntity(
            id = "1",
            name = "Breed1",
            origin = "Origin1",
            temperament = "Temperament1",
            description = "Description1",
            imageUrl = "ImageUrl1",
            lifeSpan = 12,
            isFavorite = true
        )
        val favoriteBreedEntity = FavoriteBreedEntity(
            id = "1"
        )

        repository.addFavoriteBreed(catBreed)

        Mockito.verify(storageHelper).updateBreed(breedEntity)
        Mockito.verify(storageHelper).addFavoriteBreed(favoriteBreedEntity)
    }

    // removeFavoriteBreed
    // storageUpdateBreedCalled
    // storageRemoveFavoriteBreedCalled
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
        val breedEntity = BreedEntity(
            id = "1",
            name = "Breed1",
            origin = "Origin1",
            temperament = "Temperament1",
            description = "Description1",
            imageUrl = "ImageUrl1",
            lifeSpan = 12,
            isFavorite = false
        )
        val favoriteBreedEntity = FavoriteBreedEntity(
            id = "1"
        )

        repository.removeFavoriteBreed(catBreed)

        Mockito.verify(storageHelper).updateBreed(breedEntity)
        Mockito.verify(storageHelper).removeFavoriteBreed(favoriteBreedEntity)
    }

    // getFavoriteBreedsObservable
    // storageReturnsTwoFavoriteBreedEntities
    // twoUiBreedsAreReturned
    @Test
    fun getFavoriteBreedsObservable1() = runBlocking {
        val favoriteBreedEntities = listOf(
            FavoriteBreedEntity(
                id = "1"
            ),
            FavoriteBreedEntity(
                id = "2"
            )
        )
        val favoriteBreedEntityIds = listOf(
            "1",
            "2"
        )
        val breedEntities = listOf(
            BreedEntity(
                id = "1",
                name = "Breed1",
                origin = "Origin1",
                temperament = "Temperament1",
                description = "Description1",
                imageUrl = "ImageUrl1",
                lifeSpan = 10,
                isFavorite = true
            ),
            BreedEntity(
                id = "2",
                name = "Breed2",
                origin = "Origin2",
                temperament = "Temperament2",
                description = "Description2",
                imageUrl = "ImageUrl2",
                lifeSpan = 12,
                isFavorite = true
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
                isFavorite = true
            )
        )
        given(storageHelper.getAllFavoriteBreedsObservable())
            .willReturn(flowOf(favoriteBreedEntities))
        given(storageHelper.getBreedsByIds(favoriteBreedEntityIds)).willReturn(breedEntities)

        val result = repository.getFavoriteBreedsObservable()

        Mockito.verify(storageHelper).getAllFavoriteBreedsObservable()
        Assert.assertEquals(catBreeds, result.first())
    }

    // getBreedById
    // storageReturnBreedEntity
    // uiBreedIsReturned
    @Test
    fun getBreedById1() = runBlocking {
        val favoriteBreedEntityId = "1"
        val breedEntity = BreedEntity(
            id = "1",
            name = "Breed1",
            origin = "Origin1",
            temperament = "Temperament1",
            description = "Description1",
            imageUrl = "ImageUrl1",
            lifeSpan = 10,
            isFavorite = true
        )
        val catBreed = CatBreed(
            id = "1",
            name = "Breed1",
            origin = "Origin1",
            temperament = "Temperament1",
            description = "Description1",
            imageUrl = "ImageUrl1",
            lifeSpan = 10,
            isFavorite = true
        )
        given(storageHelper.getBreedById(favoriteBreedEntityId)).willReturn(breedEntity)

        val result = repository.getBreedById(favoriteBreedEntityId)

        Mockito.verify(storageHelper).getBreedById(favoriteBreedEntityId)
        Assert.assertEquals(catBreed, result)
    }

    // getBreedsPage
    // apiReturnsTwoBreedModelsAndStorageReturnsOneIsFavorite
    // twoBreedEntitiesPageIsReturned
    @Test
    fun getBreedsPage1() = runBlocking {
        val page = 0
        val limit = 5
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
        val favoriteEntityId1 = "1"
        val favoriteEntityId2 = "2"
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
        given(apiHelper.getBreedsPage(page, limit)).willReturn(breedModels)
        given(storageHelper.isFavoriteBreed(favoriteEntityId1)).willReturn(true)
        given(storageHelper.isFavoriteBreed(favoriteEntityId2)).willReturn(false)

        val result = repository.getBreedsPage(page, limit)

        Mockito.verify(apiHelper).getBreedsPage(page, limit)
        Mockito.verify(storageHelper, times(2)).isFavoriteBreed(any())
        Assert.assertEquals(catBreeds, result)
    }
}