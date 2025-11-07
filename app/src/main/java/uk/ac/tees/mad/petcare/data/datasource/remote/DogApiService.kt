package uk.ac.tees.mad.petcare.data.datasource.remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import uk.ac.tees.mad.petcare.data.model.DogFact

interface DogApiService {

    @GET("facts")
    suspend fun getDogFacts(
        @Header("x-api-key") apiKey: String,
        @Query("limit") limit: Int = 10
    ): List<DogFact>
}
