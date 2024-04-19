package com.example.ozinsheapp.presentation.onboarding.components

import androidx.annotation.DrawableRes
import com.example.ozinsheapp.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int,
)

val pages = listOf(
    Page(
        "ÖZINŞE-ге қош келдің!",
        "Фильмдер, телехикаялар, ситкомдар,\nанимациялық жобалар, телебағдарламалар\nменреалити-шоулар, аниме және тағы\nбасқалары",
        R.drawable.image_onboarding1
    ),
    Page(
        "ÖZINŞE-ге қош келдің!",
        "Кез келген құрылғыдан қара\nСүйікті фильміңді  қосымша төлемсіз\nтелефоннан, планшеттен, ноутбуктан қара",
        R.drawable.image_onboarding2
    ),
    Page(
        "ÖZINŞE-ге қош келдің!",
        "Тіркелу оңай. Қазір тіркел де қалаған\nфильміңе қол жеткіз",
        R.drawable.image_onboarding3
    ),
)