package com.example.homework_recyclerview.presentation.fragments.converter

import android.util.Log
import androidx.lifecycle.*
import com.example.homework_recyclerview.domain.repository.Currency
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

//    val _currencyList = MutableLiveData<MutableList<Currency>>(mutableListOf())

    private var data = ListOfCurrencies.currencyList
    private val _currencyList = MutableLiveData(data)
    val currencyList: LiveData<ArrayList<Currency>> = _currencyList

//    val currencyList:LiveData<List<Currency>> =
//        Transformations.map(_currencyList) { it.toList() }

    private val _balance = MutableLiveData(0)
    val balance: LiveData<Int> = _balance

    fun setBalance(value: Int) {
        _balance.value = value
    }

    fun addCurrency(newItem: Currency) {
        data.add(newItem)
        _currencyList.value = data
    }

//    fun addCurrency(currency: Currency) {
//        Log.e("ConverterViewModel", "before add currency list -> ${_currencyList.value?.size}")
//        _currencyList.value?.add(currency)
//        Log.e("ConverterViewModel", "after add currency list -> ${_currencyList.value?.size}")
//    }

    fun deleteCurrency(currency: Currency) {
        data.remove(currency)
        _currencyList.value = data
    }

    fun sortByID() = _currencyList.value!!.sortBy { it.id }

    fun sortByName() = _currencyList.value!!.sortBy { it.type }

    fun sortByPrice() = _currencyList.value!!.sortBy { it.text }

    fun moveItem(from: Int, to: Int) {
        val fromEmoji = _currencyList.value!![from]
        _currencyList.value!!.removeAt(from)
        if (to < from) {
            _currencyList.value!!.add(to, fromEmoji)
        } else {
            _currencyList.value!!.add(to - 1, fromEmoji)
        }
    }

}
