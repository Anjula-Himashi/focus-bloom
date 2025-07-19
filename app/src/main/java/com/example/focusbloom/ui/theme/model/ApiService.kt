import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/google")
    fun sendGoogleToken(@Body tokenRequest: TokenRequest): Call<UserResponse>
}