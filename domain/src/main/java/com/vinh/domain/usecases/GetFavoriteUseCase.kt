package com.vinh.domain.usecases

import com.vinh.domain.model.entities.FavoriteItem
import com.vinh.domain.repo.FavoriteRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(private val favoriteRepo: FavoriteRepo) {
    private val checkedChange = MutableStateFlow<List<String>>(emptyList())
    suspend fun setCheckedChange(favoriteItem: FavoriteItem) {
        val currentChecked = checkedChange.value.toMutableList()
        val isChecked = favoriteItem.isChecked?:false
        if (isChecked) {
            currentChecked.remove(favoriteItem.favorite.id)
        } else {
            currentChecked.add(favoriteItem.favorite.id)
        }
        checkedChange.emit(currentChecked.distinct())
    }
    operator fun invoke() = checkedChange.flatMapLatest { checkedChange ->
        favoriteRepo.getFlow(checkedChange)
    }
    suspend fun resetChecked() {
        checkedChange.emit(emptyList())
    }

    fun getCheckedValue() = checkedChange.value
}