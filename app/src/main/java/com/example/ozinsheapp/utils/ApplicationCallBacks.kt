package com.example.ozinsheapp.utils

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import java.util.Locale

class ApplicationCallbacks(private val context: Context, private val locale: Locale) : ComponentCallbacks {

    override fun onConfigurationChanged(newConfig: Configuration) {
        context.setLocaleInternal(locale)
    }

    override fun onLowMemory() { /* do nothing */ }
}

class ActivityLifecycleCallbacks(private val locale: Locale) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activity.setLocaleInternal(locale)
    }

    override fun onActivityStarted(activity: Activity) { /* do nothing */ }

    override fun onActivityResumed(activity: Activity) { /* do nothing */ }

    override fun onActivityPaused(activity: Activity) { /* do nothing */ }

    override fun onActivityStopped(activity: Activity) { /* do nothing */ }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) { /* do nothing */ }

    override fun onActivityDestroyed(activity: Activity) { /* do nothing */ }
    // </editor-fold>
}
fun Context.setLocaleInternal(locale: Locale) {
    Locale.setDefault(locale)

    val resources = this.resources
    val configuration = resources.configuration
    configuration.setLocale(locale)
    resources.updateConfiguration(configuration, resources.displayMetrics)
}

private val Configuration.localeCompat: Locale
    get() = locales.get(0)
