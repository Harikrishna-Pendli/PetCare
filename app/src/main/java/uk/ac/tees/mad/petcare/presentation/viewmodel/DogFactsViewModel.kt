package uk.ac.tees.mad.petcare.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.petcare.data.datasource.remote.RetrofitInstance
import uk.ac.tees.mad.petcare.data.model.DogFact
import javax.inject.Inject

@HiltViewModel
class DogFactsViewModel @Inject constructor(): ViewModel() {

    private val _facts = MutableStateFlow<List<DogFact>>(emptyList())
    val facts: StateFlow<List<DogFact>> = _facts

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow(false)
    val error: StateFlow<Boolean> = _error

    private val apiKey = "live_bddE6mgA93mlUqrDrxCb8VFeDHr2ZioXYNIOMarviVc56ry5cYu7On2Z1Ubd6u1y"

    fun fetchDogFacts() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = false
            try {
                val response = RetrofitInstance.api.getDogFacts(apiKey, limit = 5)
                _facts.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }
}
