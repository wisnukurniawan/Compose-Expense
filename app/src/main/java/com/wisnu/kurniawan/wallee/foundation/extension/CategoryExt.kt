package com.wisnu.kurniawan.wallee.foundation.extension

import androidx.compose.ui.graphics.Color
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.foundation.emoji.EmojiData
import com.wisnu.kurniawan.wallee.model.CategoryType

fun CategoryType.getEmojiAndText(): Pair<String, Int> {
    return when (this) {
        CategoryType.MONTHLY_FEE -> Pair(getEmoji("shopping_cart"), R.string.category_monthly_fee)
        CategoryType.ADMIN_FEE -> Pair(getEmoji("money_with_wings"), R.string.category_admin_fee)
        CategoryType.PETS -> Pair(getEmoji("cat2"), R.string.category_pets)
        CategoryType.DONATION -> Pair(getEmoji("palms_up_together"), R.string.category_donations)
        CategoryType.EDUCATION -> Pair(getEmoji("books"), R.string.category_education)
        CategoryType.FINANCIAL -> Pair(getEmoji("bank"), R.string.category_financial)
        CategoryType.ENTERTAINMENT -> Pair(getEmoji("popcorn"), R.string.category_entertainment)
        CategoryType.CHILDREN_NEEDS -> Pair(getEmoji("baby"), R.string.category_children_needs)
        CategoryType.HOUSEHOLD_NEEDS -> Pair(getEmoji("house"), R.string.category_household_needs)
        CategoryType.SPORT -> Pair(getEmoji("muscle"), R.string.category_sport)
        CategoryType.OTHERS -> Pair(getEmoji("thinking"), R.string.category_others)
        CategoryType.FOOD -> Pair(getEmoji("stew"), R.string.category_food)
        CategoryType.PARKING -> Pair(getEmoji("parking"), R.string.category_parking)
        CategoryType.FUEL -> Pair(getEmoji("fuelpump"), R.string.category_fuel)
        CategoryType.MOVIE -> Pair(getEmoji("movie_camera"), R.string.category_movie)
        CategoryType.AUTOMOTIVE -> Pair(getEmoji("blue_car"), R.string.category_automotive)
        CategoryType.TAX -> Pair(getEmoji("receipt"), R.string.category_tax)
        CategoryType.INCOME -> Pair(getEmoji("moneybag"), R.string.category_income)
        CategoryType.BUSINESS_EXPENSES -> Pair(getEmoji("briefcase"), R.string.category_business_expenses)
        CategoryType.SELF_CARE -> Pair(getEmoji("lotion_bottle"), R.string.category_self_care)
        CategoryType.LOAN -> Pair(getEmoji("credit_card"), R.string.category_loan)
        CategoryType.SERVICE -> Pair(getEmoji("hammer_and_wrench"), R.string.category_service)
        CategoryType.SHOPPING -> Pair(getEmoji("shopping"), R.string.category_shopping)
        CategoryType.BILLS -> Pair(getEmoji("bulb"), R.string.category_bills)
        CategoryType.TAXI -> Pair(getEmoji("oncoming_taxi"), R.string.category_tax)
        CategoryType.CASH_WITHDRAWAL -> Pair(getEmoji("dollar"), R.string.category_cash_withdrawal)
        CategoryType.PHONE -> Pair(getEmoji("iphone"), R.string.category_phone)
        CategoryType.TOP_UP -> Pair(getEmoji("inbox_tray"), R.string.category_top_up)
        CategoryType.PUBLIC_TRANSPORTATION -> Pair(getEmoji("bus"), R.string.category_public_transportation)
        CategoryType.TRAVEL -> Pair(getEmoji("airplane"), R.string.category_travel)
        CategoryType.UNCATEGORIZED -> Pair(getEmoji("question"), R.string.category_uncategorized)
    }
}

fun CategoryType.getColor(): Color {
    return when (this) {
        CategoryType.MONTHLY_FEE -> Color(0xFFDF1125)
        CategoryType.ADMIN_FEE -> Color(0xFFFD4154)
        CategoryType.PETS -> Color(0xFFFE4F78)
        CategoryType.DONATION -> Color(0xFF78C2FF)
        CategoryType.EDUCATION -> Color(0xFF2FD15A)
        CategoryType.FINANCIAL -> Color(0xFFFC5C42)
        CategoryType.ENTERTAINMENT -> Color(0xFFFD7036)
        CategoryType.CHILDREN_NEEDS -> Color(0xFFEDB4AD)
        CategoryType.HOUSEHOLD_NEEDS -> Color(0xFFE0694E)
        CategoryType.SPORT -> Color(0xFFCA6F59)
        CategoryType.OTHERS -> Color(0xFF0A83FE)
        CategoryType.FOOD -> Color(0xFFFC473C)
        CategoryType.PARKING -> Color(0xFF717F8A)
        CategoryType.FUEL -> Color(0xFFFFA008)
        CategoryType.MOVIE -> Color(0xFFC9A676)
        CategoryType.AUTOMOTIVE -> Color(0xFFFFEB6D)
        CategoryType.TAX -> Color(0xFF80E220)
        CategoryType.INCOME -> Color(0xFF77EA7E)
        CategoryType.BUSINESS_EXPENSES -> Color(0xFFA2EC8E)
        CategoryType.SELF_CARE -> Color(0xFF1EA5FC)
        CategoryType.LOAN -> Color(0xFF1871AC)
        CategoryType.SERVICE -> Color(0xFFF29C98)
        CategoryType.SHOPPING -> Color(0xFF383B65)
        CategoryType.BILLS -> Color(0xFF5F41B2)
        CategoryType.TAXI -> Color(0xFF578887)
        CategoryType.CASH_WITHDRAWAL -> Color(0xFF7A6E49)
        CategoryType.PHONE -> Color(0xFF715D50)
        CategoryType.TOP_UP -> Color(0xFFFFD408)
        CategoryType.PUBLIC_TRANSPORTATION -> Color(0xFF383B65)
        CategoryType.TRAVEL -> Color(0xFFD8C9AF)
        CategoryType.UNCATEGORIZED -> Color(0xFF788991)
    }
}

private fun getEmoji(key: String): String {
    return EmojiData.DATA[key] ?: throw Exception("Emoji for $key not found!")
}
