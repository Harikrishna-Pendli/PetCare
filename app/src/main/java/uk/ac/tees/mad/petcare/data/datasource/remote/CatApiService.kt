package uk.ac.tees.mad.petcare.data.datasource.remote

import retrofit2.http.GET
import retrofit2.http.Query
import uk.ac.tees.mad.petcare.data.model.DogFact

interface CatApiService {

    @GET("fact")
    suspend fun getCatFacts(
    ): DogFact
}