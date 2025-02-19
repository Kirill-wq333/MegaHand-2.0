@file:Suppress("ConstPropertyName")
package com.evothings.data.network.config

object NetworkConfig {

    const val MHAND_BASE_URL = "https://mhand.ru/api/v2/"
    const val BASE_URL = "https://dev.avtovozina.ru/api/v2/"
    const val NEW_BASE_URL = "http://92.53.97.86/api/"

    object Routes {

        object Auth {
            const val refreshToken = "${NEW_BASE_URL}authentication/refresh-token/"
            const val newRefreshToken = "${NEW_BASE_URL}authentication/refresh-refresh-token/"
            const val verifyCaptcha = "${BASE_URL}hcaptcha/"

            const val getCode = "${NEW_BASE_URL}authentication/get-code/"
            const val confirmCode = "${NEW_BASE_URL}authentication/confirm-code/"
            const val requestSMS = "${NEW_BASE_URL}authentication/get-sms-code/"

            object SecureCode {
                const val set = "${NEW_BASE_URL}profile-login-code/set-login-code/"
                const val loginBy = "${NEW_BASE_URL}authentication/login-by-code/"
            }
        }

        object Header {
            const val cities = "${MHAND_BASE_URL}cities/"
        }

        object Splash {
            const val appSettings = "${BASE_URL}settings/"
        }

        object Coupon {
            const val isAvailable = "${NEW_BASE_URL}promocode/can-get-promocode/"
            const val getCode = "${NEW_BASE_URL}promocode/get-code/"
            const val confirmCode = "${NEW_BASE_URL}promocode/confirm-code/"
            const val getReward = "${NEW_BASE_URL}promocode-reward/"
        }

        object Main {
            const val brands = "${MHAND_BASE_URL}brands/"
            const val collections = "${MHAND_BASE_URL}collections/"
            const val shops = "${NEW_BASE_URL}shops/"
            const val banners = "${MHAND_BASE_URL}banners/"
            const val stories = "${NEW_BASE_URL}stories/"
            const val userSurvey = "${MHAND_BASE_URL}registration-channels/"
            const val transliterate = "${MHAND_BASE_URL}cities/transliterate-city/"
        }

        object News {
            const val list = "${NEW_BASE_URL}articles/"
            const val tags = "${NEW_BASE_URL}articles/get-tags/"
        }

        object Favourites {
            const val add = "${NEW_BASE_URL}wishlist/add/"
            const val remove = "${NEW_BASE_URL}wishlist/remove/"
            const val categories = "${NEW_BASE_URL}wishlist/get-filter-category/"
            const val list = "${NEW_BASE_URL}wishlist/"
            const val flush = "${NEW_BASE_URL}wishlist/flush/"
        }

        object Catalog {
            const val categories = "${NEW_BASE_URL}categories/"
            const val filterValues = "${NEW_BASE_URL}characteristic-filters/"
            const val searchHints = "${NEW_BASE_URL}search/"
        }

        object Product {
            const val list = "${NEW_BASE_URL}products/"
        }

        object Cart {
            const val getInfo = "${NEW_BASE_URL}basket/get-info/"
            const val calculateCheckout = "${NEW_BASE_URL}basket/calculate-price/"
            const val add = "${NEW_BASE_URL}basket/add/"
            const val remove = "${NEW_BASE_URL}basket/remove/"
        }

        object Order {
            const val create = "${NEW_BASE_URL}order/move-to-order/"
            const val order = "${NEW_BASE_URL}order/"
            const val getCdekPickupPoints = "${NEW_BASE_URL}cdek/get-branches/"
        }

        object Profile {
            const val clientInfo = "${NEW_BASE_URL}profile/get-info/"
            const val deleteAccount = "${NEW_BASE_URL}profile/delete/"
            const val getReferals = "${NEW_BASE_URL}referral-system/get-referral-info/"
            const val ordersHistory = "${NEW_BASE_URL}profile-order/"

            const val editProfile = "${NEW_BASE_URL}profile/update/"
            const val updateUserPhone = "${NEW_BASE_URL}profile-phone/request-update-number/"
            const val confirmChangedPhone = "${NEW_BASE_URL}profile-phone/confirm-update-number/"
        }
        object Address {
            const val root = "${NEW_BASE_URL}profile-address/"
        }

        object Card {
            const val cardInfo = "${NEW_BASE_URL}profile/get-card-info/"
            const val userTransactions = "${NEW_BASE_URL}profile/get-transactions/"
            const val checkCityAvailable = "${MHAND_BASE_URL}cities/check-city/"
        }

        object Notifications {
            const val subscribeToTopic = "${BASE_URL}notifications/subscribe-to-topic/"
            const val renewToken = "${BASE_URL}notifications/renew-token/"
            const val incrementOpen = "${BASE_URL}notifications/increment-open/"
            const val unsubscribeDevice = "${BASE_URL}notifications/unsubscribe-device/"
            const val addAvailableStatus = "${BASE_URL}notifications/add-available-status/"
        }

    }

}