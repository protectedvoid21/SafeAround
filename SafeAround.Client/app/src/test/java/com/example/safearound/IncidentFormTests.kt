//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import kotlinx.coroutines.test.TestCoroutineDispatcher
import com.example.safearound.models.ApiResponse
import com.example.safearound.models.Category
import com.example.safearound.services.ISafeAroundClient
import com.example.safearound.viewmodels.IncidentViewModel
import com.google.android.gms.maps.model.LatLng
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.function.Consumer

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class IncidentViewModelTest {
    private lateinit var safeAroundClient: ISafeAroundClient
    private lateinit var incidentViewModel: IncidentViewModel

    @Before
    fun setUp() = runTest {
        safeAroundClient = mockk<ISafeAroundClient>()

        coEvery { safeAroundClient.getCategories() } answers {
            listOf(
                Category(1, "Test", "TEST"),
                Category(2, "Test2", "TEST2"),
                Category(3, "Test3", "TEST3")
            )
        }
        incidentViewModel = IncidentViewModel(safeAroundClient)
    }

    @Test
    fun `test title change updates title`() {
        val newTitle = "New Incident Title"
        incidentViewModel.onTitleChange(newTitle)
        assert(incidentViewModel.title == newTitle)
    }

    @Test
    fun `test description change updates description`() {
        val newDescription = "New Incident Description"
        incidentViewModel.onDescriptionChange(newDescription)
        assert(incidentViewModel.description == newDescription)
    }

    @Test
    fun `test category change updates categoryId`() {
        val newCategoryId = 2
        incidentViewModel.onCategoryChange(newCategoryId)
        assert(incidentViewModel.categoryId == newCategoryId)
    }

    @Test
    fun `test send incident success`() = runTest {
        val latLng = LatLng(0.0, 0.0)
        val onSuccess = mockk<Runnable>(relaxed = true)
        val onError = mockk<Consumer<String>>(relaxed = true)

        coEvery { safeAroundClient.addIncident(any()) } returns ApiResponse(true, "")

        incidentViewModel.send(latLng, onSuccess::run, onError::accept)

        verify { onSuccess.run() }
        verify(exactly = 0) { onError.accept(any()) }
    }

    @Test
    fun `test send incident failure`() = runTest {
        val latLng = LatLng(0.0, 0.0)
        val onSuccess = mockk<Runnable>(relaxed = true)
        val onError = mockk<Consumer<String>>(relaxed = true)

        coEvery { safeAroundClient.addIncident(any()) } returns ApiResponse(false, "")

        incidentViewModel.send(latLng, onSuccess::run, onError::accept)

        verify { onError.accept(any()) }
        verify(exactly = 0) { onSuccess.run() }
    }
}