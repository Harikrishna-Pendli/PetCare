package uk.ac.tees.mad.petcare.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.petcare.data.datasource.remote.RetrofitInstance
import uk.ac.tees.mad.petcare.data.model.DogFact

class DogFactsViewModel : ViewModel() {

    private val _facts = MutableStateFlow<List<DogFact>>(emptyList())
    val facts: StateFlow<List<DogFact>> = _facts

    private val apiKey = "live_bddE6mgA93mlUqrDrxCb8VFeDHr2ZioXYNIOMarviVc56ry5cYu7On2Z1Ubd6u1y"

    fun fetchDogFacts() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getDogFacts(apiKey, limit = 5)
                _facts.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
