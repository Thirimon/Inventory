package com.example.inventory.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventory.InventoryApplication
import com.example.inventory.ui.home.HomeViewModel
import com.example.inventory.ui.item.ItemDetailsViewModel
import com.example.inventory.ui.item.ItemEditViewModel

import com.example.inventory.ui.item.ItemEntryViewModel

object AppViewModelProvier {
    val Factory = viewModelFactory {
        initializer {
            ItemEditViewModel(this.createSavedStateHandle(),
                inventoryApplication().container.itemsRepository)
        }
        initializer {
            ItemEntryViewModel(inventoryApplication().container.itemsRepository)
        }
        initializer {
            ItemDetailsViewModel(this.createSavedStateHandle(),
                inventoryApplication().container.itemsRepository)
        }
        initializer { HomeViewModel(inventoryApplication().container.itemsRepository) }
    }
}
fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)
