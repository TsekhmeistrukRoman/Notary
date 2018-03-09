package com.tsekhmeistruk.notary.widgets.util

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.tsekhmeistruk.notary.NotaryApp
import com.tsekhmeistruk.notary.R
import com.tsekhmeistruk.notary.di.ApplicationComponent


/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */
abstract class BaseActivity : AppCompatActivity() {

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun startFragment(fragment: Fragment, addToBackStack: Boolean) {
        startFragment(fragment, addToBackStack, null)
    }

    fun startFragment(fragment: Fragment, addToBackStack: Boolean, animation: IntArray?) {
        hideKeyboard()

        val tag = fragment::class.java.simpleName
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (animation != null)
            fragmentTransaction.setCustomAnimations(
                    animation[0], animation[1], animation[2], animation[3])
        fragmentTransaction
                .replace(R.id.container, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag)
        }
        fragmentTransaction.commit()
    }

    fun getAppComponent(): ApplicationComponent {
        return (application as NotaryApp).applicationComponent()
    }
}
