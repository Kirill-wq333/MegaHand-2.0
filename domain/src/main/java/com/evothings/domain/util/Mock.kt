package com.evothings.domain.util

import com.evothings.domain.feature.address.model.Address
import com.evothings.domain.feature.card.model.Card
import com.evothings.domain.feature.card.model.Transaction
import com.evothings.domain.feature.card.model.TransactionType
import com.evothings.domain.feature.catalog.model.ProductCategory
import com.evothings.domain.feature.catalog.model.SearchHint
import com.evothings.domain.feature.catalog.model.SubcategoryHint
import com.evothings.domain.feature.catalog.model.TextHint
import com.evothings.domain.feature.checkout.model.MapPoint
import com.evothings.domain.feature.checkout.model.PaymentMethod
import com.evothings.domain.feature.checkout.model.PickupPoint
import com.evothings.domain.feature.home.model.Brand
import com.evothings.domain.feature.home.model.Story
import com.evothings.domain.feature.news.model.NewsArticle
import com.evothings.domain.feature.news.model.NewsCategory
import com.evothings.domain.feature.paymentMethod.model.SBPBank
import com.evothings.domain.feature.profile.model.Order
import com.evothings.domain.feature.profile.model.Referal
import com.evothings.domain.feature.shops.model.DiscountDay
import com.evothings.domain.feature.shops.model.Shop
import com.evothings.domain.feature.shops.model.enumeration.DiscountType
import com.evothings.domain.feature.product.model.Product
import com.evothings.domain.feature.profile.model.OrderHistoryProduct

object Mock {

    val demoBrand = listOf<Brand>(
        Brand(
            id = 0,
            photoLink = "https://s3-alpha-sig.figma.com/img/997c/f6cf/1ca7984783573f3aa9869d9638c2aeef?Expires=1737936000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=Qh7sOf~C-36uqICgJIBIx0Q23EiMjQrtnHjUshq8NNBVDaFHvbsElHTnVy2PcACw-3-kpi1A40W9FehECtyh6OnbosIzJ1YYtRSvrub39aPrq7GLHXDkzp5EiAPcA-21BDiuMYaAMsASha53ib4qPPPVJBNjsviJ-MvCzF9UUhFBmdiJBYxq54pPnWWC51dRhlFUohb2Zp2f6RkBn4SNZVR2RI2SiaIKGN8sisVdfbSn7-j7cdMgoUkxR-zafpGf6zGuZBCCs~N91xaF5KkS8gtDyg7WkKegq7kdYLSDFAd230mKvEyVgpXkR4tEBk72q-LKENWAN7lrl-hR0sjNGA__",
            name = "Deno"
        ),
        Brand(
            id = 1,
            photoLink = "https://s3-alpha-sig.figma.com/img/f93f/5333/a438da1ee09e4f1370f4972e654728ed?Expires=1737936000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=N-79zHtx-p0UjdqYUOQYebjfvKPPWgyb9ZrvZr--u053ugR~65Yr52C5tj9doD~2Mjy-zevrkPIM~zfBUkoRiA0Adk3X~XMh8vtZPEPLb9-PfCouZ7JFSV5z7YMvTOZqV5SegAFocUfiLdQhEf0sIRjZzUx96bUVKgZ7wyF683VBMCGI0gSxX5XGd-YTR-hA4iQzv8-4EkLLnUwHnyr6gbo5hgP8BNIW2lSIi-jGwMU3UKeRvNuFSu4fpMA4Ahp3OqHsYlgov50Q3ddq3XezVxABFcfGBdK2hZTgMmyTlmlPdd9PXbhxvCZVn74aE349MJdT~ZKHvXZHaav-h3DYiw__",
            name = "Nike"
        ),
        Brand(
            id = 2,
            photoLink = "https://s3-alpha-sig.figma.com/img/1a62/a16a/c23a0fc1d87bc79175a0181924ff6b92?Expires=1737936000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=aguViyvNojzyuUzgTEvLd8-18sKk7JrfNjor69~X60n1IccXFTUnZ0hnd9SSVlUX3dtQvlbeNrSHaU40MzbRKNlDWcGuUpFdxHqbyGxclcmwEG3V-qqL020y9S5QKOJUm1dEMjBJU7gS-UdrNsMZlgi2KFumjpE-taqPLpZBq0-1N7TVrMn3skuTeRRr1rbV7eI85N8e56hMWXD8RT2FjiwaEjZ38YWt8o24NLONceuFNg9pmpqdNsOTkp44Umj-~l6r0Hkp0ORn3Twi0oOqQsY3F4gOEOL1nfUcQUUD1ZIEzZSBsSs5WPYAEgi6DqZXUAuIeUQTeipv85WhhDKy5g__",
            name = "Adidas"
        )
    )

    val demoStoriesList = listOf<Story>(
        Story(
            title = "О магазинах Волгограда",
            imageLink = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/45c05ef936221f87dea04ad0c0420ac97e5fb1e3",
            articleLink = "https",
            description = "",
            buttonLabel = ""
        ),
        Story(
            title = "Новые поступления",
            imageLink = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/3a2c44610e8d8d0e36b5cfe59efca342e9a4f35d",
            articleLink = "https",
            description = "",
            buttonLabel = ""
        ),
        Story(
            title = "Одежда бренда Christian Dior",
            imageLink = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/cfbb41bd10acc06b68fd0bf9cefbbe9d87dd8d7b",
            articleLink = "https",
            description = "",
            buttonLabel = ""
        ),
        Story(
            title = "Купоны и скидки в приложении",
            imageLink = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/45c05ef936221f87dea04ad0c0420ac97e5fb1e3",
            articleLink = "https",
            description = "",
            buttonLabel = ""
        ),
    )

    val demoProduct =
        Product(
            id = 0,
            title = "Кроссовки QUESTAR 2 M, черные, 43",
            photos = listOf(
                "https://avatars.mds.yandex.net/get-mpic/11722550/2a0000018b4389164775c48cbec8c88dc526/orig",
                "https://avatars.mds.yandex.net/get-mpic/3986638/img_id1570444521473320416.jpeg/orig",
                "https://www.gannett-cdn.com/presto/2020/02/25/USAT/940dfe56-9479-417f-b5b0-d69846c1f64a-GettyImages-535771789.jpg?crop=5312%2C2988%2Cx0%2Cy1249&amp;width=1200"
            ),
            actualPrice = 12010.0,
            cashbackPoints = 145.0,
            oldPrice = 19080.0,
            discount = 67.0,
            isPercentDiscount = true,
            size = "43",
            brandId = 1,
            color = "Черный",
            description = "Это не просто обувь, а настоящая икона стиля, которая с момента своего появления в 1982 году завоевала сердца миллионов. Разработанные дизайнером Брюсом Килгором, они были первыми баскетбольными кроссовками, в которых использовалась революционная технология амортизации Nike Air, обеспечивающая непревзойденный комфорт и поддержку.",
            condition = "Good",
            isInCart = false,
            isFavourite = false,
            availability = Product.Availability.IN_STOCK
        )

    val demoProductsList = listOf(
        Product(
            id = 1,
            title = "Кроссовки QUESTAR 2 M, черные, 43",
            photos = listOf(
                "https://cdn1.ozone.ru/s3/multimedia-q/c600/6733683134.jpg",
                "https://spirk.ru/files/29f/29f6b1917d70dc87f7b2f6c0408782c2.jpg"
            ),
            actualPrice = 12010.0,
            cashbackPoints = 0.0,
            oldPrice = 19800.0,
            discount = 67.0,
            isPercentDiscount = true,
            brandId = 1,
            size = "43",
            condition = "AVERAGE",
            isInCart = false,
            isFavourite = false,
            availability = Product.Availability.IN_STOCK
        ),
        Product(
            id = 2,
            title = "Спортивные штаны ICONS LAB",
            photos = listOf(
                "https://media.frgroup.kz/images/6f/17/1b9b8c2b5bcd36d52bbcc4cd7e3f.jpg",
                "https://media.frgroup.kz/images/fe/c9/fd745dad50aacb7f6410dbfba940.jpg",
                "https://media.frgroup.kz/images/11/88/4c15a010e8e421c541c643488aca.jpg"
            ),
            actualPrice = 8290.0,
            cashbackPoints = 321.0,
            oldPrice = 11290.0,
            discount = 390.0,
            isPercentDiscount = true,
            brandId = 2,
            size = "43",
            condition = "AVERAGE",
            isInCart = false,
            isFavourite = false,
            availability = Product.Availability.IN_STOCK
        ),
        Product(
            id = 3,
            title = "Джинсы мужские",
            photos = listOf(
                "https://nazya.com/anyimage/img.alicdn.com/imgextra/i2/TB1ztLpKFXXXXb1XpXXXXXXXXXX_!!0-item_pic.jpg",
                "https://cdn1.ozone.ru/s3/multimedia-t/6324632117.jpg",
                "https://nazya.com/anyimage/img.alicdn.com/imgextra/i1/2180054355/O1CN01jChvek1i2dRtVyAwf_!!2180054355.jpg"
            ),
            actualPrice = 1210.0,
            cashbackPoints = 0.0,
            oldPrice = 1990.0,
            discount = 700.0,
            isPercentDiscount = false,
            size = "M",
            condition = "GOOD",
            brandId = 2,
            isInCart = false,
            isFavourite = false,
            availability = Product.Availability.IN_STOCK
        ),
        Product(
            id = 4,
            title = "Футболка Stussy Tough Gear",
            photos = listOf("https://static.cdek.shopping/images/shopping/gmWNdR1g4ughjg1H.jpg"),
            actualPrice = 19980.0,
            cashbackPoints = 651.0,
            oldPrice = 24009.0,
            discount = 40.0,
            isPercentDiscount = true,
            size = "L",
            brandId = 1,
            condition = "LOW",
            isInCart = false,
            isFavourite = false,
            availability = Product.Availability.IN_STOCK
        ),
        Product(
            id = 5,
            title = "Свитшот Nike",
            photos = listOf(
                "https://static.street-beat.ru/upload/iblock/8d5/8d57236d91ff6a848ce93937101fb5e4.jpg",
                "https://images-eu.ssl-images-amazon.com/images/I/41ptqSQU6nL._UL1000_.jpg",
                "https://avatars.mds.yandex.net/i?id=8aa345e679a30a1ad6e077829f0690fd4a0ce736-4571961-images-thumbs&n=13",
                "https://cdn1.ozone.ru/s3/multimedia-0/6381088260.jpg"
            ),
            actualPrice = 9780.0,
            cashbackPoints = 56.0,
            oldPrice = 11200.0,
            discount = 24.0,
            isPercentDiscount = true,
            size = "S",
            condition = "AVERAGE",
            brandId = 3,
            isInCart = false,
            isFavourite = false,
            availability = Product.Availability.IN_STOCK
        )
    )


    val demoCard = Card(
        balance = 7180,
        barcodeUrl = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/2719c139e208c90fb342991b1adb967066c3c2bb",
        burnAmount = 54.3,
        burnDate = "06.03"
    )

    val demoCategories = listOf(
        ProductCategory(
            id = 0,
            title = "Одежда",
            photoLink = "",
            children = null,
            parent = null,
        ),
        ProductCategory(
            id = 1,
            title = "Обувь",
            photoLink = "",
            children = null,
            parent = null,
        ),
        ProductCategory(
            id = 2,
            title = "Аксессуары",
            photoLink = "",
            children = null,
            parent = null,
        ),
        ProductCategory(
            id = 3,
            title = "Другое",
            photoLink = "",
            children = null,
            parent = null,
        ),
        ProductCategory(
            id = 4,
            title = "Пальто",
            photoLink = "",
            children = null,
            parent = null,
        ),
    )

    val demoTransactionsMap: Map<String, List<Transaction>> = mapOf(
        "Сегодня" to listOf(
            Transaction(
                type = TransactionType.DEPOSIT,
                amount = 124.0,
                date = "11:21"
            ),
        ),
        "Вчера" to listOf(
            Transaction(
                type = TransactionType.WITHDRAW,
                amount = 500.0,
                date = "19:21"
            ),
            Transaction(
                type = TransactionType.DEPOSIT,
                amount = 487.0,
                date = "12:56"
            ),
        ),
        "03.12" to listOf(
            Transaction(
                type = TransactionType.DEPOSIT,
                amount = 58.0,
                date = "12:19"
            ),
        ),
        "27.11" to listOf(
            Transaction(
                type = TransactionType.DEPOSIT,
                amount = 192.0,
                date = "07:25"
            ),
        )
    )

    val demoSubcategories =
        listOf("Новинки", "Все", "Футболки", "Кофты", "Носки", "Свитшоты", "Худи", "Шорты")

    val demoSearchHints: List<SearchHint> =
        listOf(
            TextHint(
                selectionRange = listOf(0, 1, 2, 3),
                text = "футболка женская"
            ),
            TextHint(
                selectionRange = listOf(0, 1, 2, 3),
                text = "футболка мужская"
            ),
            TextHint(
                selectionRange = listOf(),
                text = "кроссовки мужские"
            ),
            SubcategoryHint(
                imageLink = "https://cdn1.ozone.ru/s3/multimedia-h/c600/6709395833.jpg",
                title = "Футболки",
                subtitle = "Одежда - Верхняя одежда",
                categoryObject = ProductCategory(0, "", null, null, null)
            ),
            SubcategoryHint(
                imageLink = "https://cdn1.ozone.ru/s3/multimedia-h/c600/6709395833.jpg",
                title = "Футболки",
                subtitle = "Одежда - Верхняя одежда",
                categoryObject = ProductCategory(0, "", null, null, null)
            ),
        )

    val demoAddresses = listOf<Address>(
        Address(
            id = 0,
            fullAddress = "Тольятти, ул. Революционная 52, 445000",
            city = "Тольятти",
            street = "Автостроителей",
            house = "78",
            flat = "151"
        ),
        Address(
            id = 0,
            fullAddress = "Озеры, ул. Пушкина 15, 198277",
            city = "Озеры",
            street = "Пушкина",
            house = "15",
            flat = "91"
        ),
    )

    val demoPickupPoints = listOf(
        PickupPoint("", "ул. Революционная, 28", MapPoint(53.509424, 49.284388)),
        PickupPoint("", "ул. 40 лет победы, 112", MapPoint(53.505424, 49.282388)),
        PickupPoint("", "ул. Юбилейная, 15", MapPoint(53.510424, 49.280388)),
        PickupPoint("", "ул. Жукова, 98", MapPoint(53.500424, 49.288388)),
    )

    val demoPaymentMethods = listOf(
        PaymentMethod(PaymentMethod.Provider.VISA, "VISA •• 4247", false),
        PaymentMethod(PaymentMethod.Provider.T_BANK, "Т-банк •• 9302 (СПБ)", true)
    )

    val demoReferals = listOf<Referal>(
        Referal(name = "Артем Красавин", cashback = 2, joinDate = "1 д. назад"),
        Referal(name = "Марина Васильевна", cashback = 2, joinDate = "2 д. назад"),
        Referal(name = "Дмитрий Поляков",  cashback = 2, joinDate = "1 н. назад"),
        Referal(name = "Иван Александров", cashback = 2, joinDate = "1 м. назад"),
        Referal(name = "", cashback = 2, joinDate = "1 г. назад"),
        Referal(name = "ВлаDICK Трухин", cashback = 2, joinDate = "2 г. назад"),
    )

    val demoSBPBanks = listOf<SBPBank>(
        SBPBank(
            imageLink = "https://avatars.mds.yandex.net/i?id=d0d84f496a2d3a30b042ad12680d2d86_l-10672158-images-thumbs&n=13",
            name = "T-Bank",
            isPreviouslyUsed = true
        ),
        SBPBank(
            imageLink = "https://avatars.mds.yandex.net/i?id=64c16516aa41d0b5429d250dae83c1b7_l-6875822-images-thumbs&n=13",
            name = "Сбер Банк",
            isPreviouslyUsed = true
        ),
        SBPBank(
            imageLink = "https://avatars.mds.yandex.net/i?id=e0ac118c2ff1fcd10e304949c8fd1702142724c4-10274729-images-thumbs&n=13",
            name = "Альфа Банк лого",
            isPreviouslyUsed = false
        ),
        SBPBank(
            imageLink = "https://avatars.mds.yandex.net/i?id=599d26743c62960cdb2a63f171a7c783_l-11431503-images-thumbs&n=13",
            name = "Газпромбанк",
            isPreviouslyUsed = false
        ),
        SBPBank(
            imageLink = "https://global-ex.cc/storage/payment_systems/iex-O6mpY3rIck.PNG",
            name = "ВТБ",
            isPreviouslyUsed = false
        ),
    )

    val demoHistoryProducts = listOf(
        OrderHistoryProduct(
            id = 14,
            photo = "https://static.street-beat.ru/upload/iblock/8d5/8d57236d91ff6a848ce93937101fb5e4.jpg"
        ),
        OrderHistoryProduct(
            id = 12,
            photo = "https://media.frgroup.kz/images/6f/17/1b9b8c2b5bcd36d52bbcc4cd7e3f.jpg"
        ),
        OrderHistoryProduct(
            id = 45,
            photo = "https://media.frgroup.kz/images/11/88/4c15a010e8e421c541c643488aca.jpg"
        ),
        OrderHistoryProduct(
            id = 1,
            photo = "https://cdn1.ozone.ru/s3/multimedia-q/c600/6733683134.jpg"
        ),
    )

    val demoOrder = Order(
        id = "14",
        orderDate = "21.09.2024",
        cost = 20004,
        status = Order.Status.PAYMENT_TIME_EXPIRED,
        statusDisplay = "Завершен",
        trackNumbers = listOf("472838476238746", "23534634633636"),
        products = demoHistoryProducts,
        orderId = "984717231"
    )

    val demoOrdersList = listOf(
        demoOrder,
        Order(
            id = "13",
            orderDate = "21.09.2024",
            cost = 20004,
            status = Order.Status.COMPLETED,
            trackNumbers = listOf("095817242", "23534634633636"),
            orderId = "98471723",
            statusDisplay = "Завершен",
            products = listOf(demoHistoryProducts.shuffled().first())
        ),
        Order(
            id = "10958712",
            orderDate = "11.09.2024",
            cost = 19053,
            status = Order.Status.CANCELED,
            trackNumbers = listOf("4372151111", "23534634633636"),
            orderId = "10958712",
            statusDisplay = "Отменен",
            products = listOf(demoHistoryProducts.shuffled().first())
        ),
        Order(
            id = "49018375",
            orderDate = "09.09.2024",
            cost = 481,
            status = Order.Status.DELIVERY,
            trackNumbers = listOf("472838476238746", "23534634633636"),
            orderId = "49018375",
            statusDisplay = "В доставке",
            products = listOf(demoHistoryProducts.shuffled().first())
        ),
        Order(
            id = "1059381",
            orderDate = "12.07.2024",
            cost = 1985,
            status = Order.Status.CREATED,
            trackNumbers = listOf(),
            orderId = "1059381",
            statusDisplay = "Создан",
            products = listOf(demoHistoryProducts.shuffled().first())
        ),
    )

    val demoShopsList = listOf<Shop>(
        Shop(
            point = MapPoint(53.534045, 49.342835),
            address = "ул. Тополиная, 56а",
            workSchedule = "10:00 - 20:00",
            phone = "8 920 718 17 23",
            shortAddress = null,
            productAdditionDays = "",
            email = "",
            discountWeeks = listOf<DiscountDay>(
                DiscountDay(
                    dayOfMonth = 28,
                    dayOfWeek = "Пн",
                    discount = "0",
                    isActive = false,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 29,
                    dayOfWeek = "Вт",
                    discount = "0",
                    isActive = false,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 30,
                    dayOfWeek = "Ср",
                    discount = "0",
                    isActive = false,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 28,
                    dayOfWeek = "Чт",
                    discount = "0",
                    isActive = false,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 29,
                    dayOfWeek = "Пт",
                    discount = "0",
                    isActive = false,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 30,
                    dayOfWeek = "Сб",
                    discount = "0",
                    isActive = false,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 1,
                    dayOfWeek = "Вс",
                    discount = "30",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.NEW
                ),
                DiscountDay(
                    dayOfMonth = 2,
                    dayOfWeek = "Пн",
                    discount = "40",
                    isActive = true,
                    isToday = false,
                    hasAddition = true,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 2,
                    dayOfWeek = "Вт",
                    discount = "50",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 3,
                    dayOfWeek = "Ср",
                    discount = "50",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 4,
                    dayOfWeek = "Чт",
                    discount = "300",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.ROUBLES
                ),
                DiscountDay(
                    dayOfMonth = 5,
                    dayOfWeek = "Пт",
                    discount = "30",
                    isActive = true,
                    isToday = true,
                    type = DiscountType.BLACK_FRIDAY
                ),
                DiscountDay(
                    dayOfMonth = 6,
                    dayOfWeek = "Сб",
                    discount = "30",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.TICKET
                ),
                DiscountDay(
                    dayOfMonth = 7,
                    dayOfWeek = "Вс",
                    discount = "30",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.NEW
                ),
                DiscountDay(
                    dayOfMonth = 8,
                    dayOfWeek = "Пн",
                    discount = "30",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 9,
                    dayOfWeek = "Вт",
                    discount = "40",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 10,
                    dayOfWeek = "Ср",
                    discount = "10",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 11,
                    dayOfWeek = "Чт",
                    discount = "90",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 12,
                    dayOfWeek = "Пт",
                    discount = "50",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 13,
                    dayOfWeek = "Сб",
                    discount = "80",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 14,
                    dayOfWeek = "Вс",
                    discount = "20",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 15,
                    dayOfWeek = "Пн",
                    discount = "15",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 16,
                    dayOfWeek = "Вт",
                    discount = "45",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 17,
                    dayOfWeek = "Ср",
                    discount = "70",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 18,
                    dayOfWeek = "Чт",
                    discount = "85",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 19,
                    dayOfWeek = "Пт",
                    discount = "20",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 20,
                    dayOfWeek = "Сб",
                    discount = "30",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
                DiscountDay(
                    dayOfMonth = 21,
                    dayOfWeek = "Вс",
                    discount = "50",
                    isActive = true,
                    isToday = false,
                    type = DiscountType.BY_PERCENTS
                ),
            )
        ),
        Shop(
            point = MapPoint(53.59, 49.345),
            address = "ул. Мира, 27а",
            workSchedule = "9:00 - 18:00",
            phone = "8 917 728 18 23",
            shortAddress = null,
            productAdditionDays = "",
            email = "",
            discountWeeks = emptyList()
        ),
        Shop(
            point = MapPoint(53.65, 49.415),
            address = "ул. Арцебуза, 27а",
            workSchedule = "11:00 - 21:00",
            phone = "8 919 800 82 01",
            shortAddress = null,
            productAdditionDays = "",
            email = "",
            discountWeeks = emptyList()
        ),
    )

    val demoNews = NewsArticle(
        id = "1",
        title = "Деловой офисный стиль одежды для женщин",
        publishingDate = "17.01.24",
        categories = "1" ,
        articleLink = "https://google.com/",
        content = "<h2>Уникальность тренча в деталях</h2>\\r\\n<p>Тренч &mdash; это не просто непромокаемая верхняя одежда, это настоящая базовая вещь, способная добавить изюминку в ваш образ. Классический дизайн с двубортным кроем, поднимающимся воротником и глубокими карманами, делает его удобным и стильным. Пояс с пряжкой, погоны и ружейный клапан, хоть и потеряли свою первоначальную практичность, придают тренчу неповторимый шарм.</p>\\r\\n<h2>Отличия от плаща: выбирайте с умом</h2>\\r\\n<p>Плащ &mdash; это широкое понятие, охватывающее различные виды верхней одежды, включая тренч. В отличие от плаща, тренч характеризуется множеством деталей, классическим кроем и спокойными тонами. Следовательно, при выборе между ними, учтите ваш стиль и предпочтения.</p>\\r\\n<h2>Погода и тренч: идеальное сочетание</h2>\\r\\n<p>Тренчкот &mdash; ваш надежный союзник в дождливые дни. Высокий воротник и двубортный крой обеспечивают защиту от ветра, делая его идеальным для прохладной осени или зимы. Носите его при температуре от 10 до 17 градусов для максимального комфорта. Сравнительно с плащом, тренч обеспечивает дополнительную защиту от ветра, что делает его чуть теплее.</p>\\r\\n<h2>Как выбрать идеальный тренч от \"Мегахенд\"</h2>\\r\\n<p><b>Виды и модели: подчеркните свой стиль</b></p>\\r\\n<ul>\\r\\n<li><em>Классический</em>: универсальный вариант, всегда актуальный.</li>\\r\\n<li><em>Короткий</em>: подчеркнет ноги, удобен в повседневной носке.</li>\\r\\n<li><em>Миди</em>: балансирует пропорции фигуры, подходит для разных типов силуэтов.</li>\\r\\n<li><em>Длинный</em>: идеальная защита от ветра, подходит под разные стили.</li>\\r\\n<li><em>Оверсайз</em>: модный и универсальный, не сковывает движения.</li>\\r\\n<li><em>Асимметричный крой</em>: необычно и стильно, смещает акценты.</li>\\r\\n<li><em>С капюшоном</em>: дополнительная защита от непогоды.</li>\\r\\n<li><em>Двубортный</em>: элегантный и защищает от ветра.</li>\\r\\n<li><em>Приталенный</em>: подчеркивает фигуру.</li>\\r\\n<li><em>С принтом</em>: оригинальность и выделение из толпы.</li>\\r\\n<li><em>Трансформер</em>: функциональность в каждой детали.</li>\\r\\n</ul>\\r\\n<p><b>Материал: комфорт и стиль</b></p>\\r\\n<p>Выбирайте из хлопкового габардина, костюмного хлопка, водоотталкивающей хлопковой ткани, плащевки, полиэстера, кожи, замши, лака или денима. \"Мегахенд\" предлагает разнообразие материалов для вашего удобства.</p>\\r\\n<p><b>Цвета и принты: выражайте себя</b></p>\\r\\n<p>Традиционные цвета, такие как черный, белый, бежевый и коричневый, всегда в моде. Однако \"Мегахенд\" предлагает смелые цвета, такие как зеленый, горчичный, розовый и принты, чтобы вы могли выделиться.</p>\\r\\n<p><b>Бренд: надежность \"Мегахенд\"</b></p>\\r\\n<p>Традиционно тренч выпускает \"Мегахенд\" &mdash; бренд с винтажными моделями высочайшего качества. Приобретите тренч от \"Мегахенд\" для надежности и стиля.</p>\\r\\n<h2>Как стильно сочетать тренч: советы от \"Мегахенд\"</h2>\\r\\n<p><b>С джинсами:</b></p>\\r\\n<p>Деним с необработанными краями и кожаный тренч &mdash; отличное сочетание. Светлые джинсы подойдут к плащам пастельных оттенков.</p>\\r\\n<p><b>С брюками</b></p>\\r\\n<p>Создайте офисный лук с черными брюками, дополненными тренчем.</p>\\r\\n<p><b>С брючным костюмом</b></p>\\r\\n<p>Бежевый тренч средней длины отлично гармонирует с брючным костюмом.</p>\\r\\n<p><b>С юбками</b></p>\\r\\n<p>Комбинируйте короткий тренч с мини-юбкой или выберите юбку миди с кожаным тренчем ярких цветов.</p>\\r\\n<p><b>С лосинами/леггинсами</b></p>\\r\\n<p>Создайте монохромный образ с классическим тренчем или добавьте яркие леггинсы для контраста.</p>\\r\\n<p><b>Со спортивным костюмом</b></p>\\r\\n<p>Спортивный стиль легко дополнить тренчем без рукавов от \"Мегахенд\".</p>\\r\\n<p><b>С шортами</b></p>\\r\\n<p>Высокая талия шорт и джинсовый тренч создадут стильный образ.</p>\\r\\n<p><b>С худи/толстовкой</b></p>\\r\\n<p>Спортивный лук с тренчем в спокойных оттенках &mdash; практичный и стильный выбор.</p>\\r\\n<p>Теперь, когда вы освоили искусство ношения тренча, выберите свой идеальный вариант от \"Мегахенд\". Сочетайте с умом, выражайте свою индивидуальность и наслаждайтесь комфортом в любой погоде.</p>",
        previewImageLink = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/431b470cdd88f4e7511b4076f9d7748eddab16fc"
    )

    val demoNewsCategories = listOf(
        NewsCategory(id = 1, name = "Богачи"),
        NewsCategory(id = 2, name = "Бедняки")
    )

    val demoNewsList = listOf<NewsArticle>(
        NewsArticle(
            id = "1",
            title = "Деловой офисный стиль одежды для женщин",
            publishingDate = "17.01.24",
            categories = "1",
            articleLink = "https://google.com/",
            content = "<h2>Уникальность тренча в деталях</h2>\\r\\n<p>Тренч &mdash; это не просто непромокаемая верхняя одежда, это настоящая базовая вещь, способная добавить изюминку в ваш образ. Классический дизайн с двубортным кроем, поднимающимся воротником и глубокими карманами, делает его удобным и стильным. Пояс с пряжкой, погоны и ружейный клапан, хоть и потеряли свою первоначальную практичность, придают тренчу неповторимый шарм.</p>\\r\\n<h2>Отличия от плаща: выбирайте с умом</h2>\\r\\n<p>Плащ &mdash; это широкое понятие, охватывающее различные виды верхней одежды, включая тренч. В отличие от плаща, тренч характеризуется множеством деталей, классическим кроем и спокойными тонами. Следовательно, при выборе между ними, учтите ваш стиль и предпочтения.</p>\\r\\n<h2>Погода и тренч: идеальное сочетание</h2>\\r\\n<p>Тренчкот &mdash; ваш надежный союзник в дождливые дни. Высокий воротник и двубортный крой обеспечивают защиту от ветра, делая его идеальным для прохладной осени или зимы. Носите его при температуре от 10 до 17 градусов для максимального комфорта. Сравнительно с плащом, тренч обеспечивает дополнительную защиту от ветра, что делает его чуть теплее.</p>\\r\\n<h2>Как выбрать идеальный тренч от \"Мегахенд\"</h2>\\r\\n<p><b>Виды и модели: подчеркните свой стиль</b></p>\\r\\n<ul>\\r\\n<li><em>Классический</em>: универсальный вариант, всегда актуальный.</li>\\r\\n<li><em>Короткий</em>: подчеркнет ноги, удобен в повседневной носке.</li>\\r\\n<li><em>Миди</em>: балансирует пропорции фигуры, подходит для разных типов силуэтов.</li>\\r\\n<li><em>Длинный</em>: идеальная защита от ветра, подходит под разные стили.</li>\\r\\n<li><em>Оверсайз</em>: модный и универсальный, не сковывает движения.</li>\\r\\n<li><em>Асимметричный крой</em>: необычно и стильно, смещает акценты.</li>\\r\\n<li><em>С капюшоном</em>: дополнительная защита от непогоды.</li>\\r\\n<li><em>Двубортный</em>: элегантный и защищает от ветра.</li>\\r\\n<li><em>Приталенный</em>: подчеркивает фигуру.</li>\\r\\n<li><em>С принтом</em>: оригинальность и выделение из толпы.</li>\\r\\n<li><em>Трансформер</em>: функциональность в каждой детали.</li>\\r\\n</ul>\\r\\n<p><b>Материал: комфорт и стиль</b></p>\\r\\n<p>Выбирайте из хлопкового габардина, костюмного хлопка, водоотталкивающей хлопковой ткани, плащевки, полиэстера, кожи, замши, лака или денима. \"Мегахенд\" предлагает разнообразие материалов для вашего удобства.</p>\\r\\n<p><b>Цвета и принты: выражайте себя</b></p>\\r\\n<p>Традиционные цвета, такие как черный, белый, бежевый и коричневый, всегда в моде. Однако \"Мегахенд\" предлагает смелые цвета, такие как зеленый, горчичный, розовый и принты, чтобы вы могли выделиться.</p>\\r\\n<p><b>Бренд: надежность \"Мегахенд\"</b></p>\\r\\n<p>Традиционно тренч выпускает \"Мегахенд\" &mdash; бренд с винтажными моделями высочайшего качества. Приобретите тренч от \"Мегахенд\" для надежности и стиля.</p>\\r\\n<h2>Как стильно сочетать тренч: советы от \"Мегахенд\"</h2>\\r\\n<p><b>С джинсами:</b></p>\\r\\n<p>Деним с необработанными краями и кожаный тренч &mdash; отличное сочетание. Светлые джинсы подойдут к плащам пастельных оттенков.</p>\\r\\n<p><b>С брюками</b></p>\\r\\n<p>Создайте офисный лук с черными брюками, дополненными тренчем.</p>\\r\\n<p><b>С брючным костюмом</b></p>\\r\\n<p>Бежевый тренч средней длины отлично гармонирует с брючным костюмом.</p>\\r\\n<p><b>С юбками</b></p>\\r\\n<p>Комбинируйте короткий тренч с мини-юбкой или выберите юбку миди с кожаным тренчем ярких цветов.</p>\\r\\n<p><b>С лосинами/леггинсами</b></p>\\r\\n<p>Создайте монохромный образ с классическим тренчем или добавьте яркие леггинсы для контраста.</p>\\r\\n<p><b>Со спортивным костюмом</b></p>\\r\\n<p>Спортивный стиль легко дополнить тренчем без рукавов от \"Мегахенд\".</p>\\r\\n<p><b>С шортами</b></p>\\r\\n<p>Высокая талия шорт и джинсовый тренч создадут стильный образ.</p>\\r\\n<p><b>С худи/толстовкой</b></p>\\r\\n<p>Спортивный лук с тренчем в спокойных оттенках &mdash; практичный и стильный выбор.</p>\\r\\n<p>Теперь, когда вы освоили искусство ношения тренча, выберите свой идеальный вариант от \"Мегахенд\". Сочетайте с умом, выражайте свою индивидуальность и наслаждайтесь комфортом в любой погоде.</p>",
            previewImageLink = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/431b470cdd88f4e7511b4076f9d7748eddab16fc"
        ),
        NewsArticle(
            id = "2",
            title = "Деловой офисный стиль одежды для женщин",
            publishingDate = "17.01.24",
            categories = "1",
            articleLink = "https://google.com/",
            content = "",
            previewImageLink = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/431b470cdd88f4e7511b4076f9d7748eddab16fc"
        ),
        NewsArticle(
            id = "3",
            title = "Как ухаживать за белыми кроссовками зимой?",
            publishingDate = "16.01.24",
            categories = "1",
            articleLink = "https://yandex.ru/",
            content = "",
            previewImageLink = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/ab7a88e8076efdfc69dab3c440ff828e6a912cf8"
        ),
        NewsArticle(
            id = "4",
            title = "Как ухаживать за белыми кроссовками зимой?",
            publishingDate = "16.01.24",
            categories = "1",
            articleLink = "https://yandex.ru/",
            content = "",
            previewImageLink = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/ab7a88e8076efdfc69dab3c440ff828e6a912cf8"
        ),
        NewsArticle(
            id = "5",
            title = "Как ухаживать за белыми кроссовками зимой?",
            publishingDate = "16.01.24",
            categories = "1",
            articleLink = "https://yandex.ru/",
            content = "",
            previewImageLink = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/ab7a88e8076efdfc69dab3c440ff828e6a912cf8"
        ),
        NewsArticle(
            id = "6",
            title = "Как ухаживать за белыми кроссовками зимой?",
            publishingDate = "16.01.24",
            categories = "1",
            articleLink = "https://yandex.ru/",
            content = "",
            previewImageLink = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/ab7a88e8076efdfc69dab3c440ff828e6a912cf8"
        ),
        NewsArticle(
            id = "7",
            title = "Как ухаживать за белыми кроссовками зимой?",
            publishingDate = "16.01.24",
            categories = "1",
            articleLink = "https://yandex.ru/",
            content = "",
            previewImageLink = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/ab7a88e8076efdfc69dab3c440ff828e6a912cf8"
        ),
        NewsArticle(
            id = "8",
            title = "Как ухаживать за белыми кроссовками зимой?",
            publishingDate = "16.01.24",
            categories = "1",
            articleLink = "https://yandex.ru/",
            content = "",
            previewImageLink = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/ab7a88e8076efdfc69dab3c440ff828e6a912cf8"
        ),
        NewsArticle(
            id = "9",
            title = "Как ухаживать за белыми кроссовками зимой?",
            publishingDate = "16.01.24",
            categories = "1",
            articleLink = "https://yandex.ru/",
            content = "",
            previewImageLink = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/ab7a88e8076efdfc69dab3c440ff828e6a912cf8"
        ),
        NewsArticle(
            id = "10",
            title = "Как ухаживать за белыми кроссовками зимой?",
            publishingDate = "16.01.24",
            categories = "1",
            articleLink = "https://yandex.ru/",
            content = "",
            previewImageLink = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/ab7a88e8076efdfc69dab3c440ff828e6a912cf8"
        ),
        NewsArticle(
            id = "11",
            title = "Одежда как символ трансформации: влияние н...",
            publishingDate = "15.01.24",
            categories = "1",
            articleLink = "https://vk.com/",
            content = "",
            previewImageLink = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/f77725768b4868e253fa2be3cc515fb88fc353e3"
        ),
        NewsArticle(
            id = "12",
            title = "Одежда как символ трансформации: влияние н...",
            publishingDate = "15.01.24",
            categories = "1",
            articleLink = "https://vk.com/",
            content = "",
            previewImageLink = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/f77725768b4868e253fa2be3cc515fb88fc353e3"
        ),
        NewsArticle(
            id = "13",
            title = "Советы по созданию образов для миниатюрных...",
            publishingDate = "13.01.24",
            categories = "1",
            articleLink = "https://bing.com/",
            content = "",
            previewImageLink = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/610690474eaebbdd28ae854420309e8fca6bf19c"
        ),
        NewsArticle(
            id = "14",
            title = "Как ухаживать за кашемировыми свитерами,...",
            publishingDate = "11.01.24",
            categories = "1",
            articleLink = "https://stripe.org/",
            content = "",
            previewImageLink = "https://www.figma.com/file/3nc7iwpllameuJDuV5r4Ad/image/f8e08b96e7a2444e9c21d6b01c0bd727b19a78b1"
        ),
    )

}