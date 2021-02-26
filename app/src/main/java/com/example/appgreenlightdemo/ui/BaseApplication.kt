package com.example.appgreenlightdemo.ui

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * createdBy : Amit
 * description : base application for app,
 * it is required by hilt for generating and maintaining scopes and dependencies.
 */
@HiltAndroidApp
class BaseApplication : Application() {
}