package com.example.homework_recyclerview.presentation.fragments.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_recyclerview.data.CurrencyRepository
import com.example.homework_recyclerview.domain.repository.Currency
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: CurrencyRepository
    ) : ViewModel() {

    lateinit var rates: Map<String, Double>


    private var data = ListOfCurrencies.currencyList
    private val _currencyList = MutableLiveData(data)
    val currencyList: LiveData<ArrayList<Currency>> = _currencyList

    private val _balance = MutableLiveData(0)
    val balance: LiveData<Int> = _balance

    fun setBalance(value: Int) {
        _balance.value = value
    }

    fun addCurrency(newItem: Currency) {
        data.add(newItem)
        val newData = arrayListOf<Currency>()
        newData.addAll(data)
        _currencyList.value = newData
    }

    fun deleteCurrency(currency: Currency) {
        data.remove(currency)
        val newData = arrayListOf<Currency>()
        newData.addAll(data)
        _currencyList.value = newData
    }

    fun sortByID() {
        data.sortBy { it.id }
        val newData = arrayListOf<Currency>()
        newData.addAll(data)
        _currencyList.value = data
    }

    fun sortByName() {
        data.sortBy { it.type }
        val newData = arrayListOf<Currency>()
        newData.addAll(data)
        _currencyList.value = newData
    }

    fun sortByPrice() {
        data.sortBy { it.text }
        val newData = arrayListOf<Currency>()
        newData.addAll(data)
        _currencyList.value = newData
    }

    fun moveItem(from: Int, to: Int) {
        val fromEmoji = data[from]
        data.removeAt(from)
        val newData = arrayListOf<Currency>()
        newData.addAll(data)

        if (to < from) {
            newData.add(to, fromEmoji)
        }
        else {
            newData.add(to - 1, fromEmoji)
        }
        _currencyList.value = newData
    }

    init {
        loadCurrencyRates()
    }

    fun loadCurrencyRates(){
        viewModelScope.launch {
            val results = repository.getCurrencyRates()
            rates = results.rates


//            for (key in rates.keys) {
//                val coef = rates[key]
//                Log.d("TAG","key = $key, coef = $coef")
//            }

        }
    }
}
