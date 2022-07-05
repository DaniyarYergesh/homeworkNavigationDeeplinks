package com.example.homework_recyclerview.presentation.fragments.converter

import androidx.lifecycle.*
import com.example.homework_recyclerview.domain.repository.Currency
import kotlinx.coroutines.launch

class MainViewModel(private val dataSource: ListOfCurrencies): ViewModel() {

    private val _currencyList = MutableLiveData(dataSource.currencyList)

    val currencyList : LiveData<List<Currency>> =
        Transformations.map(_currencyList) { it.toList() }

    fun addNewItem(newItem: Currency, position: Int) = _currencyList.value!!.add(position, newItem)

    fun deleteCurrency(currency: Currency) = _currencyList.value!!.remove(currency)

    fun sortByName() = _currencyList.value!!.sortBy { it.type }

    fun sortByPrice() = _currencyList.value!!.sortBy { it.text }

    fun getPositionType(newItem: Currency) {
        _currencyList.value!!.add(newItem)
        _currencyList.value!!.sortBy { it.type }
    }

    fun getPositionName(newItem: Currency) {
        _currencyList.value!!.add(newItem)
        _currencyList.value!!.sortBy { it.text }
    }




    fun updateCurrencyData(textTenge: String) {
        for (item in _currencyList.value!!) {
            viewModelScope.launch {
                val currency = textTenge.toInt() / item.course
                item.text = currency
            }
        }
    }

    fun sortByID()=_currencyList.value!!.sortBy { it.id }
}