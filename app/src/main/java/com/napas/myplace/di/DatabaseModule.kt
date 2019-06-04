package th.co.officemate.di

import android.app.Application
import androidx.room.Room
import com.napas.myplace.base.database.MyPlaceDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    companion object {
        private const val DATABASE_NAME = "MyPlace_db"
    }

    @Singleton
    @Provides
    fun provideMyPlaceDatabase(application: Application) =
        Room.databaseBuilder(application, MyPlaceDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun providePlacesDao(myPlaceDatabase: MyPlaceDatabase) = myPlaceDatabase.placesDao()
}