import android.content.Context
import com.example.kotlinmidproject.data.UserPreferences

object UserPreferencesManager {
    private lateinit var userPreferences: UserPreferences

    fun initialize(context: Context) {
        userPreferences = UserPreferences(context)
    }

    fun getUserPreferences(): UserPreferences {
        return userPreferences
    }
}
