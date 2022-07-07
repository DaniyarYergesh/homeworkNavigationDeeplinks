package com.example.homework_recyclerview.presentation.fragments.converter

import android.util.Log
import androidx.lifecycle.*
import com.example.convertor.R
import com.example.homework_recyclerview.data.CurrencyRepository
import com.example.homework_recyclerview.domain.repository.Currency
import com.example.homework_recyclerview.utils.Constants
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: CurrencyRepository
    ) : ViewModel() {

    var rates: Map<String, Double> = mapOf()

    var listOfRates: MutableLiveData<List<String>> = MutableLiveData(emptyList())


    private var data = mutableListOf<Currency>()
    private val _currencyList = MutableLiveData<List<Currency>>(data)
    val currencyList: LiveData<List<Currency>> = _currencyList

    private val _balance = MutableLiveData(0)
    val balance: LiveData<Int> = _balance


//    private val _newCurrencyLiveData = MutableLiveData<Currency>()
//    val newCurrencyLiveData:LiveData<Currency> = _newCurrencyLiveData

    fun addNewRate(key:String, value:Double){
        val newCurrency = Currency(Constants.id++, 0.0, key, R.drawable.image_1_3,value)
        //_newCurrencyLiveData.value = newCurrency
        data.add(newCurrency)
//        val newData = arrayListOf<Currency>()
//        newData.addAll(data)
        _currencyList.value = data
    }

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

    fun loadCurrencyRates(){
        viewModelScope.launch {
            val results = repository.getCurrencyRates()
            rates = results.rates
            listOfRates.value = rates.keys.toList()
            Log.d("ConverterViewModel", "listOfRates from retro=$listOfRates")
        }
    }
}
