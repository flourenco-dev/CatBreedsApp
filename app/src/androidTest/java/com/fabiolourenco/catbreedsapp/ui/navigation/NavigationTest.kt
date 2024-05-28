package com.fabiolourenco.catbreedsapp.ui.navigation

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fabiolourenco.catbreedsapp.core.storage.database.dao.BreedsDao
import com.fabiolourenco.catbreedsapp.core.storage.database.dao.FavoriteBreedsDao
import com.fabiolourenco.catbreedsapp.core.storage.database.entity.BreedEntity
import com.fabiolourenco.catbreedsapp.core.storage.database.entity.FavoriteBreedEntity
import com.fabiolourenco.catbreedsapp.ui.CatBreedsActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<CatBreedsActivity>()

    @Inject
    lateinit var breedsDao: BreedsDao

    @Inject
    lateinit var favoriteBreedsDao: FavoriteBreedsDao

    @Before
    fun setUp() {
        hiltRule.inject()
        runBlocking {
            breedsDao.insertAll(
                listOf(
                    BreedEntity(
                        id = "abys",
                        name = "Abyssinian",
                        origin = "Egypt",
                        temperament = "Active, Energetic, Independent, Intelligent, Gentle",
                        description = "The Abyssinian is easy to care for, and a joy to have.",
                        imageUrl = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg",
                        lifeSpan = 15,
                        isFavorite = false
                    ),
                    BreedEntity(
                        id = "aege",
                        name = "Aegean",
                        origin = "Greece",
                        temperament = "Affectionate, Social, Intelligent, Playful, Active",
                        description = "Native to the Greek islands known as the Cyclades.",
                        imageUrl = "https://cdn2.thecatapi.com/images/ozEvzdVM-.jpg",
                        lifeSpan = 12,
                        isFavorite = true
                    )
                )
            )
            favoriteBreedsDao.insertFavoriteBreed(
                FavoriteBreedEntity(id = "aege")
            )
        }
    }

    @After
    fun tearDown() {
        runBlocking {
            breedsDao.clearAll()
            favoriteBreedsDao.clearAll()
        }
    }

    @Test
    fun testNavigationToFavorites() {
        composeTestRule.onNodeWithText("FAVORITES").performClick()

        composeTestRule.onNodeWithText("Favorite Breeds").assertExists()
    }

    @Test
    fun testNavigationToPaginatedBreeds() {
        composeTestRule.onNodeWithText("PAGES").performClick()

        composeTestRule.onNodeWithText("Paginated Breeds").assertExists()
    }
}