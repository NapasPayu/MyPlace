package th.co.officemate.di

import com.napas.myplace.di.scope.PerActivity
import com.napas.myplace.ui.location.LocationActivity
import com.napas.myplace.ui.location.LocationModule
import com.napas.myplace.ui.main.MainActivity
import com.napas.myplace.ui.main.MainFragmentProvider
import com.napas.myplace.ui.main.MainModule
import com.napas.myplace.ui.places.PlacesModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuilderModule {
    @PerActivity
    @ContributesAndroidInjector(modules = [MainModule::class, PlacesModule::class, MainFragmentProvider::class])
    abstract fun bindMainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [LocationModule::class])
    abstract fun bindLocationActivity(): LocationActivity
}