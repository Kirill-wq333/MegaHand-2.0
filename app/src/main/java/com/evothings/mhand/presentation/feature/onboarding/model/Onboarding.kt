package com.evothings.mhand.presentation.feature.onboarding.model

import androidx.annotation.DrawableRes
import com.evothings.mhand.R

sealed class Onboarding(
    @DrawableRes
    val iconRes: Int = 0,
    val title: String = "",
    val description: String = "",
    val itemsCounter: String = "",
    val cardAlignment: CardAlignment = CardAlignment.BOTTOM
) {

    data object Home {
        data object Header : Onboarding(
            iconRes = R.drawable.ic_home,
            title = "Интерактивная шапка приложения",
            itemsCounter = "1/4",
            description = "Всегда показываем твой баланс. Можно выбрать город, а также посмотреть уведомления",
        )
        data object Stories : Onboarding(
            iconRes = R.drawable.ic_home,
            title = "Сторис - быстрые новости Мегахенда",
            itemsCounter = "2/4",
            description = "Рассказываем о брендах, показываем образы, оповещаем об открытии магазинов",
        )
        data object Loyality : Onboarding(
            iconRes = R.drawable.ic_home,
            title = "Быстрый доступ к системе лояльности",
            itemsCounter = "3/4",
            description = "Сумма накопленных баллов, ваш процент кэшбека, а также кнопка просмотра Вашей карты",
        )

        data object NewProducts : Onboarding(
            iconRes = R.drawable.ic_home,
            title = "Самые свежие новинки товаров прямо под рукой",
            itemsCounter = "4/4",
            description = "Выбирай на свой вкус и цвет, добавляй в Корзину или Избранное, и конечно же покупай",
            cardAlignment = CardAlignment.TOP
        )

        val scenario = listOf(Header, Stories, Loyality, NewProducts)
    }

    data object Catalog {

        data object Categories : Onboarding(
            iconRes = R.drawable.ic_catalog,
            title = "Категории каталога",
            itemsCounter = "1/1",
            description = "Пять категорий, в которых ты найдешь подходящее для себя"
        )

    }

    data object Card {
        data object CardInformation : Onboarding(
            iconRes = R.drawable.ic_card,
            title = "Личная карта системы лояльности",
            itemsCounter = "1/2",
            description = "Которую ты можешь показать кассиру Мегахенд при покупке для получения бонусных рублей",
        )
        data object Transactions : Onboarding(
            iconRes = R.drawable.ic_card,
            title = "История операций по карте",
            itemsCounter = "2/2",
            description = "Показываем когда и сколько ты потратил или заработал. Есть возможность фильтрации",
            cardAlignment = CardAlignment.TOP
        )

        val scenario = listOf(CardInformation, Transactions)
    }

    object Cart {
        data object CartItem : Onboarding(
            iconRes = R.drawable.ic_shop,
            title = "Корзина",
            itemsCounter = "1/1",
            description = "Можно добавлять, очищать и покупать товары"
        )
    }

    object Profile {
        data object PersonalInfo : Onboarding(
            iconRes = R.drawable.ic_profile,
            title = "Личная информация",
            itemsCounter = "1/3",
            description = "Чтобы мы знали, кому отправлять заказ",
        )
        data object CashbackBar : Onboarding(
            iconRes = R.drawable.ic_profile,
            title = "Шкала кешбэка",
            itemsCounter = "2/3",
            description = "Заполняй поля в профиле с иконкой подарка и получай больше выгоды",
        )
        data object ReferalSystem : Onboarding(
            iconRes = R.drawable.ic_profile,
            title = "Реферальная система",
            itemsCounter = "3/3",
            description = "Используй код и получай 350 бонусных рублей на свой баланс за каждого приведенного друга",
            cardAlignment = CardAlignment.TOP
        )

        val scenario = listOf(PersonalInfo, CashbackBar, ReferalSystem)
    }

    object Other {
        data object BottomSheet : Onboarding(
            iconRes = R.drawable.ic_more,
            title = "Другое",
            description = "Карта магазинов, новости мира моды, вакансии нашей компании а также помощь с заказом — все это ты сможешь найти именно здесь. А еще можно сменить тему",
            itemsCounter = "1/1",
            cardAlignment = CardAlignment.TOP
        )
    }

}
