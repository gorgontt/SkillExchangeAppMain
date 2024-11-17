package com.example.skillexchange.models

import android.content.Intent

class AndroidUtils {

    public fun passUserModelAsIntent(intent: Intent, model: UserRv) {
        intent.putExtra("username", model.name)
        intent.putExtra("userId", model.userId)
    }

}