package com.example.inventory.ui.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.data.Item
import com.example.inventory.ui.AppViewModelProvier
import com.example.inventory.ui.item.formatedPrice
import com.example.inventory.ui.navigation.NavigationDestination

object HomeDestination: NavigationDestination{
    override val route: String="Home"

    override val titleRes: Int= R.string.app_name
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    modifier: Modifier=Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvier.Factory)
) { val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            InventoryTopAppBar(
                title = stringResource(id = HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
    ) {
HomeBody(
    itemList = homeUiState.itemList,
    onItemClick = navigateToItemUpdate,
    modifier= Modifier
        .padding(it)
        .fillMaxSize())
    }
}
@Composable
fun HomeBody(
    itemList: List<Item>, onItemClick: (Int) -> Unit, modifier:Modifier=Modifier
){
   Column(
       horizontalAlignment = Alignment.CenterHorizontally,
       modifier = modifier
   ) {
       if(itemList.isEmpty()){
           Text(
              text= stringResource(id = R.string.no_item_description),
               textAlign = TextAlign.Center,
               style = MaterialTheme.typography.titleLarge
           )
       }
       else{
           Log.d("TestSave","Testing error")
           InventoryList(
               itemList = itemList,
               onItemClick = { onItemClick(it.id) },
               modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
           )
       }
   }
}
@Composable
fun InventoryList(
    itemList: List<Item>, onItemClick: (Item) -> Unit, modifier: Modifier=Modifier
){
    LazyColumn(modifier =modifier){
        items(items= itemList, key = {it.id}){
            item ->
            InventoryItem(
                item = item,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(item) }
            )
        }
    }
}
@Composable
private fun InventoryItem(
    item: Item,
    modifier: Modifier=Modifier
){
    Card(
        modifier=modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier=Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
          Row(
              modifier=Modifier.fillMaxWidth()
          ){
              Text(text = item.name, style = MaterialTheme.typography.titleLarge)
              Spacer(modifier = Modifier.weight(1f))
              Text(text = item.formatedPrice(),
                  style = MaterialTheme.typography.titleMedium)
          }
            Text(
                text = stringResource(id = R.string.in_stock, item.quantity),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
