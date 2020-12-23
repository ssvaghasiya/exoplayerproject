package com.newproject.apputils

import android.os.Environment
import java.io.File

object Constant {
    const val FOLDER_NAME = ".somerce"
    val FOLDER_RIDEINN_PATH = (Environment
            .getExternalStorageDirectory().absolutePath
            + File.separator
            + "somerce")
    const val USER_LATITUDE = "lat"
    const val FROM_PROFILE = "FROM_PROFILE"
    const val ISSOCIALLOGIN = "ISSOCIALLOGIN"
    const val miles = "miles"
    const val USER_LONGITUDE = "longi"
    const val LOGIN_INFO = "login_info"
    const val EMPLOYEE_ID = "employee_id"
    const val VISIT_DETAILS = "visit_details"
    const val AVATAR = "avatar"
    const val LANGUAGE = "No language"
    const val SUCCESSFULL_MONTHLY_VISITS = "successfulMonthlyVisits"
    const val ALL_VISITS_DATA = "AllVisitsData"

    const val NO_DEVICE_ID = "No DeviceId"
    const val FINISH_ACTIVITY = "finish_activity"
    const val APP_GOES_BACKGROUND = "app_goes_background"
    const val RESPONSE_FAILURE_CODE = 901
    const val RESPONSE_SUCCESS_CODE = 200
    const val RESPONSE_SUCCESS_CODE1 = 201
    const val VALIDATION_FAILED_CODE = 903
    const val EMAILID = "EmailId"
    const val ROLE = "role"
    const val FACEBOOK = "Facebook"
    const val GOOGLE = "Google"
    const val APP_JSON = "application/json"
    const val USERDATA = "UserData"
    const val LOCATION_UPDATED = "location_updated"
    const val LATITUDE = "LATITUDE"
    const val LONGITUDE = "LONGITUDE"
    const val SEARCHEVENTMODEL = "SearchEventModel"
    const val EVENTDETAIL = "EventDetail"
    const val SEARCH_EVENT_DATE_PATTERN = "MM/dd/yyyy"
    const val SEARCH_EVENT_TIME_PATTERN = "HH:mm"
    const val SEARCH_EVENT_12_TIME_PATTERN = "hh:mm"
    const val SHARE_FACEBOOK = "Facebook"
    const val SHARE_TWITTER = "Twitter"
    const val SHARE_INSTAGRAM = "Instagram"
    const val SHARE_OTHER = "Others"
    const val REJECTED = "Rejected"
    const val INACTIVE = "Inactive"
    const val ACTIVE = "active"
    const val PUBLIC = "Public"
    const val PRIVATE = "Private"
    const val LISTINGTYPE = "ListingType"
    const val MANAGELIST = "ManageList"
    const val SEARCHLIST = "SearchList"
    const val TOKEN_ID = "tokenID"
    const val UID = "userId"
    const val WELCOME_INTRO_DATA = "WelcomeScreenIntroScreen"
    const val FROM = "from"
    const val EMAIL_CHANGE = "email_change"
    const val PHONE_CHANGE = "phone_change"
    const val MY_VEHICLE = "my_vehicle"
    const val IS_FIRST_TIME = "is_first_time"

    const val FCM_TOKEN = "fcm_token"

    const val SERVER_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'"
    const val DISPLAY_DATE_FORMAT = "dd MMM yyyy"

    val PER_PAGE_DATA_LIMIT = 6L
    const val FROM_LOAN_APPROVED = "FROM_LOAN_APPROVED"
    const val FROM_WELCOME_SCREEN = "FROM_WELCOME_SCREEN"

    const val CURRENCY_NGN = "NGN"
    const val DEFAULT_TRANSACTION_AMT_ADD_CARD = 10



    const val PREF_USER_BANK_DETAIL = "user_bank_detail"
    const val PREF_USER_CARD_DETAIL = "user_card_detail"
    const val PREF_MY_LOANS = "my_loans"
    const val PREF_BANK_LIST = "bank_list"
    const val PREF_LOAN_SDK_DATA = "loan_sdk_data"
    const val PREF_LOAN_PROFILE_DATA = "loan_profile_data"

    const val FROM_NO_LOAN = "from_no_loan"
    const val PAYBACKAMOUNT = "paybackamount"
    const val RATE = "rate"
    const val AMOUNT = "amount"
    const val RECOMMENDED_LOAN_AMOUNT = "recommended_loan_amount"
    const val INTEREST = "interest"
    const val PRINCIPAL = "principal"

    const val CITY_AREA = "city_area"
    const val CATEGORY = "category"
    const val ALL_CATEGORY = "category"
    const val ALL_BUSINESS = "all_business"
    const val ALL_PRODUCT = "all_product"
    const val EXPLORE = "explore"
    const val USER_FAVOURITES = "user_favourites"
    const val USER_FOLLOWINGS = "user_followings"
    const val USER_FOLLOWERS = "user_followers"
    const val NOTIFICATION = "notification"
    const val OLD_ORDER = "old_order"
    const val NEW_ORDER = "new_order"
    const val SHIPPING_ADDRESS = "shipping_address"
    const val CART_PRODUCT = "cart_product"
    const val ATTENDANCE_DATA = "attendance_data"
    const val ORDERS_DETAILS_DATA = "order_details_data"
    const val HOME_FOLLOWING_DATA = "home_following_data"
    const val SEARCH_FRIEND_DATA = "search_friend_data"

    const val FEED = "feed"

    const val DASHBOARD_INFO = "dashboard_info"
    const val BUSINESS_NEW_ORDER_DATA = "business_new_order_data"
    const val BUSINESS_PAST_ORDER_DATA = "business_past_order_data"
    const val BUSINESS_ORDER_DETAILS_DATA = "business_order_details_data"
    const val PRODUCT_LINE_DATA = "product_line_data"


    const val CONTACT_SUPPORT_EMAIL = "info@intelia.io"

    fun getSuccessCode(): Int {
        return RESPONSE_SUCCESS_CODE
    }

    fun getFailureCode(): Int {
        return RESPONSE_FAILURE_CODE
    }

    fun getValidationCode(): Int {
        return VALIDATION_FAILED_CODE
    }

}
