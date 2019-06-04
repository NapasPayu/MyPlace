package th.co.officemate.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.napas.myplace.base.service.PlacesService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val TIMEOUT_SEC = 30L
    }

    @Singleton
    @Provides
    fun provideGson() = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideHttpClient() = OkHttpClient.Builder()
        .readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(@Named("ENDPOINT") endpoint: String, gson: Gson, okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun providePlacesService(retrofit: Retrofit) = retrofit.create(PlacesService::class.java)
}