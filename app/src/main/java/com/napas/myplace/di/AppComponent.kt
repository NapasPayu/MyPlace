package th.co.officemate.di

import android.app.Application
import com.napas.myplace.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, NetworkModule::class, DatabaseModule::class, BuilderModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun endpoint(@Named("ENDPOINT") endpoint: String): Builder

        fun build(): AppComponent
    }

    fun inject(myApplication: MyApplication)
}