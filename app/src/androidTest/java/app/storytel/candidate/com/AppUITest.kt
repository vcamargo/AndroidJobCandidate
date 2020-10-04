package app.storytel.candidate.com

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.storytel.candidate.com.util.waitUntilViewIsDisplayed
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class AppUITest {
    private val mockWebServer = MockWebServer()

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    val postsJson = "[" +
            "  {" +
            "    \"userId\": 1," +
            "    \"id\": 1," +
            "    \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\"," +
            "    \"body\": \"quia et suscipitsuscipit recusandae consequuntur expedita et \"" +
            "  }," +
            "  {" +
            "    \"userId\": 1," +
            "    \"id\": 2," +
            "    \"title\": \"qui est esse\"," +
            "    \"body\": \"est rerum tempore vitaesequi sint nihil reprehenderit dolor beatae \"" +
            "  }," +
            "  {" +
            "    \"userId\": 1," +
            "    \"id\": 3," +
            "    \"title\": \"ea molestias quasi exercitationem repellat qui ipsa sit aut\"," +
            "    \"body\": \"et iusto sed quo iurevoluptatem occaecati omnis eligendi aut \"" +
            "  }," +
            "  {" +
            "    \"userId\": 1," +
            "    \"id\": 4," +
            "    \"title\": \"eum et est occaecati\"," +
            "    \"body\": \"ullam et saepe reiciendis voluptatem adipisci\"" +
            "  }," +
            "  {" +
            "    \"userId\": 1," +
            "    \"id\": 5," +
            "    \"title\": \"nesciunt quas odio\"," +
            "    \"body\": \"repudiandae veniam quaerat sunt sed\"" +
            "  }," +
            "  {" +
            "    \"userId\": 1," +
            "    \"id\": 6," +
            "    \"title\": \"dolorem eum magni eos aperiam quia\"," +
            "    \"body\": \"ut aspernatur corporis harum nihil quis provident \"" +
            "  }," +
            "  {" +
            "    \"userId\": 1," +
            "    \"id\": 7," +
            "    \"title\": \"magnam facilis autem\"," +
            "    \"body\": \"dolore placeat quibusdam ea quo vitae\"" +
            "  }," +
            "  {" +
            "    \"userId\": 1," +
            "    \"id\": 8," +
            "    \"title\": \"dolorem dolore est ipsam\"," +
            "    \"body\": \"dignissimos aperiam dolorem qui eum\"" +
            "  }," +
            "  {" +
            "    \"userId\": 1," +
            "    \"id\": 9," +
            "    \"title\": \"nesciunt iure omnis dolorem tempora et accusantium\"," +
            "    \"body\": \"consectetur animi nesciunt iure dolore\"" +
            "  }," +
            "  {" +
            "    \"userId\": 1," +
            "    \"id\": 10," +
            "    \"title\": \"optio molestias id quia eum\"," +
            "    \"body\": \"quo et expedita modi cum officia vel magnidoloribus \"" +
            "  }]"

    val photosJson = "[" +
            "  {" +
            "    \"albumId\": 1," +
            "    \"id\": 1," +
            "    \"title\": \"accusamus beatae ad facilis cum similique qui sunt\"," +
            "    \"url\": \"https://via.placeholder.com/600/92c952\"," +
            "    \"thumbnailUrl\": \"https://via.placeholder.com/150/92c952\"" +
            "  }," +
            "  {" +
            "    \"albumId\": 1," +
            "    \"id\": 2," +
            "    \"title\": \"reprehenderit est deserunt velit ipsam\"," +
            "    \"url\": \"https://via.placeholder.com/600/771796\"," +
            "    \"thumbnailUrl\": \"https://via.placeholder.com/150/771796\"" +
            "  }," +
            "  {" +
            "    \"albumId\": 1," +
            "    \"id\": 3," +
            "    \"title\": \"officia porro iure quia iusto qui ipsa ut modi\"," +
            "    \"url\": \"https://via.placeholder.com/600/24f355\"," +
            "    \"thumbnailUrl\": \"https://via.placeholder.com/150/24f355\"" +
            "  }," +
            "  {" +
            "    \"albumId\": 1," +
            "    \"id\": 4," +
            "    \"title\": \"culpa odio esse rerum omnis laboriosam voluptate repudiandae\"," +
            "    \"url\": \"https://via.placeholder.com/600/d32776\"," +
            "    \"thumbnailUrl\": \"https://via.placeholder.com/150/d32776\"" +
            "  }," +
            "  {" +
            "    \"albumId\": 1," +
            "    \"id\": 5," +
            "    \"title\": \"natus nisi omnis corporis facere molestiae rerum in\"," +
            "    \"url\": \"https://via.placeholder.com/600/f66b97\"," +
            "    \"thumbnailUrl\": \"https://via.placeholder.com/150/f66b97\"" +
            "  }," +
            "  {" +
            "    \"albumId\": 1," +
            "    \"id\": 6," +
            "    \"title\": \"accusamus ea aliquid et amet sequi nemo\"," +
            "    \"url\": \"https://via.placeholder.com/600/56a8c2\"," +
            "    \"thumbnailUrl\": \"https://via.placeholder.com/150/56a8c2\"" +
            "  }," +
            "  {" +
            "    \"albumId\": 1," +
            "    \"id\": 7," +
            "    \"title\": \"officia delectus consequatur vero aut veniam explicabo molestias\"," +
            "    \"url\": \"https://via.placeholder.com/600/b0f7cc\"," +
            "    \"thumbnailUrl\": \"https://via.placeholder.com/150/b0f7cc\"" +
            "  }," +
            "  {" +
            "    \"albumId\": 1," +
            "    \"id\": 8," +
            "    \"title\": \"aut porro officiis laborum odit ea laudantium corporis\"," +
            "    \"url\": \"https://via.placeholder.com/600/54176f\"," +
            "    \"thumbnailUrl\": \"https://via.placeholder.com/150/54176f\"" +
            "  }," +
            "  {" +
            "    \"albumId\": 1," +
            "    \"id\": 9," +
            "    \"title\": \"qui eius qui autem sed\"," +
            "    \"url\": \"https://via.placeholder.com/600/51aa97\"," +
            "    \"thumbnailUrl\": \"https://via.placeholder.com/150/51aa97\"" +
            "  }," +
            "  {" +
            "    \"albumId\": 1," +
            "    \"id\": 10," +
            "    \"title\": \"beatae et provident et ut vel\"," +
            "    \"url\": \"https://via.placeholder.com/600/810b14\"," +
            "    \"thumbnailUrl\": \"https://via.placeholder.com/150/810b14\"" +
            "  }," +
            "  {" +
            "    \"albumId\": 1," +
            "    \"id\": 11," +
            "    \"title\": \"nihil at amet non hic quia qui\"," +
            "    \"url\": \"https://via.placeholder.com/600/1ee8a4\"," +
            "    \"thumbnailUrl\": \"https://via.placeholder.com/150/1ee8a4\"" +
            "  }]"

    val commentsJson = "[" +
            "  {" +
            "    \"postId\": 1," +
            "    \"id\": 1," +
            "    \"name\": \"id labore ex et quam laborum\"," +
            "    \"email\": \"Eliseo@gardner.biz\"," +
            "    \"body\": \"laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium\"" +
            "  }," +
            "  {" +
            "    \"postId\": 1," +
            "    \"id\": 2," +
            "    \"name\": \"quo vero reiciendis velit similique earum\"," +
            "    \"email\": \"Jayne_Kuhic@sydney.com\"," +
            "    \"body\": \"est natus enim nihil est dolore omnis voluptatem numquam\\net omnis occaecati quod ullam at\\nvoluptatem error expedita pariatur\\nnihil sint nostrum voluptatem reiciendis et\"" +
            "  }," +
            "  {" +
            "    \"postId\": 1," +
            "    \"id\": 3," +
            "    \"name\": \"odio adipisci rerum aut animi\"," +
            "    \"email\": \"Nikita@garfield.biz\"," +
            "    \"body\": \"quia molestiae reprehenderit quasi aspernatur\\naut expedita occaecati aliquam eveniet laudantium\\nomnis quibusdam delectus saepe quia accusamus maiores nam est\\ncum et ducimus et vero voluptates excepturi deleniti ratione\"" +
            "  }," +
            "  {" +
            "    \"postId\": 1," +
            "    \"id\": 4," +
            "    \"name\": \"alias odio sit\"," +
            "    \"email\": \"Lew@alysha.tv\"," +
            "    \"body\": \"non et atque\\noccaecati deserunt quas accusantium unde odit nobis qui voluptatem\\nquia voluptas consequuntur itaque dolor\\net qui rerum deleniti ut occaecati\"" +
            "  }," +
            "  {" +
            "    \"postId\": 1," +
            "    \"id\": 5," +
            "    \"name\": \"vero eaque aliquid doloribus et culpa\"," +
            "    \"email\": \"Hayden@althea.biz\"," +
            "    \"body\": \"harum non quasi et ratione\\ntempore iure ex voluptates in ratione\\nharum architecto fugit inventore cupiditate\\nvoluptates magni quo et\"" +
            "  }" +
            "]"

    @Before
    @Throws(IOException::class, InterruptedException::class)
    fun setup() {
        val mDispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when {
                    request.path?.contains("/posts") == true -> {
                        return MockResponse().setBody(postsJson)
                    }
                    request.path?.contains("/photos") == true -> {
                        MockResponse().setBody(photosJson)
                    }
                    request.path?.contains("/comments") == true -> {
                        MockResponse().setBody(commentsJson)
                    }
                    else -> {
                        MockResponse().setResponseCode(404)
                    }
                }
            }
        }
        mockWebServer.dispatcher = mDispatcher
        mockWebServer.start(8080)
    }

    @After
    @Throws(IOException::class)
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun postsListTest() {
        // Loading Spinner
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))

        // Wait Until mock data fetch is completed
        waitUntilViewIsDisplayed(withId(R.id.postsList))

        // Check if Loading spinner is not visible
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))

        // Check if Recyclerview is displayed
        onView(withId(R.id.postsList)).check(matches(isDisplayed()))
    }
}