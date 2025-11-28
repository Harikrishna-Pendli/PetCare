package uk.ac.tees.mad.petcare.data.datasource.remote

import retrofit2.http.GET
import retrofit2.http.Query
import uk.ac.tees.mad.petcare.data.model.DogFact

interface CatApiService {

    @GET("facts")
    suspend fun getCatFacts(
        @Query("limit") limit: Int = 5
    ): List<DogFact> // same model reused
}