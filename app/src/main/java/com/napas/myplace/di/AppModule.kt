package th.co.officemate.di

import android.app.Application
import android.content.Context
import android.location.Geocoder
import com.napas.myplace.R
import dagger.Module
import dagger.Provides
import th.co.officemate.di.qualifier.ApplicationContext
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    @ApplicationContext
    fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    fun provideLocale() = Locale.getDefault()

    @Singleton
    @Provides
    fun provideGeocoder(@ApplicationContext context: Context, locale: Locale) = Geocoder(context, locale)

    @Named("API_KEY")
    @Singleton
    @Provides
    fun provideApiKey(@ApplicationContext context: Context) = context.getString(R.string.google_apis_key)

}