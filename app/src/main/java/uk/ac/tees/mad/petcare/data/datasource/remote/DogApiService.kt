package uk.ac.tees.mad.petcare.data.datasource.remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import uk.ac.tees.mad.petcare.data.model.DogFact

interface DogApiService {

    @GET("facts/dog")
    suspend fun getDogFacts(
    ): DogFact
}
