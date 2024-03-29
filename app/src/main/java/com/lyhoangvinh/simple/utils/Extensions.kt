package com.lyhoangvinh.simple.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Handler
import android.text.TextUtils
import android.transition.ChangeBounds
import android.transition.Slide
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.transition.TransitionManager
import com.lyhoangvinh.simple.R
import com.squareup.picasso.Picasso
import dagger.hilt.android.qualifiers.ActivityContext
import java.lang.ref.WeakReference
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.hypot

fun ImageView.loadImageIssues(url: String) {
//    Picasso.get()
//        .load(url)
//        .placeholder(R.drawable.ic_placeholder_rectangle_200px)
//        .error(R.drawable.ic_placeholder_rectangle_200px)
//        .centerCrop()
//        .fit()
//        .into(this)
}
/**
 * Allows calls like
 *
 * `viewGroup.inflate(R.layout.foo)`
 */
fun ViewGroup.inflate(@ActivityContext context:Context, @LayoutRes layout: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}

fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}

fun ImageView.loadImage(url: String) {
    if (url.isEmpty()) {
        Picasso.get()
            .load(R.drawable.poster_show_not_available)
            .placeholder(R.drawable.ic_placeholder_rectangle_200px)
            .error(R.drawable.poster_show_not_available)
            .fit()
            .centerCrop()
            .into(this)
    } else {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_placeholder_rectangle_200px)
            .error(R.drawable.poster_show_not_available)
            .fit()
            .centerCrop()
            .into(this)
    }

}

fun ImageView.loadImageNotFit(url: String) {
    if (url.isNotEmpty()) {
        Picasso.get().load(url)
            .resize(512, 512)
            .centerInside()
            .placeholder(R.drawable.ic_placeholder_rectangle_200px)
            .error(R.drawable.poster_show_not_available)
            .into(this)
    } else {
        setImageResource(R.drawable.poster_show_not_available)
    }
}

fun Activity.createDialog(): Dialog {
    val progressDialog = Dialog(this)
    progressDialog.let {
        it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        it.setContentView(R.layout.progress_dialog)
        it.setCancelable(false)
        it.setCanceledOnTouchOutside(false)
        return it
    }
}

@SuppressLint("SimpleDateFormat")
fun TextView.formatDate(time: Long) {
    try {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val netDate = Date(time)
        this.text = sdf.format(netDate)
    } catch (e: Exception) {
        this.text = e.toString()
    }
}

fun getAppDateFormatter(createdDate: String): String? {
    var out: String? = null
    var dateFormatter: Date? = null
    if (!TextUtils.isEmpty(createdDate)) {
        dateFormatter = parseToDate(createdDate)
    }
    if (dateFormatter != null) {
        out = formatToDate(dateFormatter)
    }
    if (TextUtils.isEmpty(out)) {
        out = createdDate
    }
    return out
}

fun formatToDate(date: Date): String {
    var result = ""
    try {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        result = sdf.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return result
}

fun parseToDate(date: String?): Date? {
    var d: Date? = null
    if (!TextUtils.isEmpty(date)) {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        //sdf.setTimeZone(...);
        try {
            d = sdf.parse(date!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

    }
    return d
}

fun Activity.showToastMessage(message: String) {
    if (!message.isEmpty()) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.showToastMessage(message: String) {
    if (this.context != null && !message.isEmpty()) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Fragment.showKeyboard(editText: EditText) {
    activity?.showKeyboard(editText)
}

fun Activity.hideKeyboard() {
    if (currentFocus == null) View(this) else currentFocus?.let { hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.showKeyboard(yourEditText: EditText) {
    try {
        val input = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        input.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        input.showSoftInput(yourEditText, InputMethodManager.SHOW_IMPLICIT)
    } catch (ignored: Exception) {
    }
}

fun View.setVisibility(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.setVisibilityAnim(isVisible: Boolean) {
    val cx = width / 2
    val cy = height / 2

    val radius = hypot(cx.toFloat(), cy.toFloat())
    val anim = if (isVisible) {
        ViewAnimationUtils.createCircularReveal(this, cx, cy, 0f, radius)
    } else {
        ViewAnimationUtils.createCircularReveal(this, cx, cy, radius, 0f)
    }
    anim.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            super.onAnimationEnd(animation)
            setVisibility(isVisible)
        }
    })
    anim.start()
}

fun Fragment.addAnimations(): Fragment {
    return this.apply {
        val slideTransition = Slide(Gravity.END)
        slideTransition.duration = 300L
        val changeBoundsTransition = ChangeBounds()
        changeBoundsTransition.duration = 300L
        enterTransition = slideTransition
        allowEnterTransitionOverlap = false
        allowReturnTransitionOverlap = false
        sharedElementEnterTransition = changeBoundsTransition
    }
}

fun Activity.startActivityTransition(cls: Class<*>, finishAct: Boolean) {
    this.let {
        val pairs = TransitionUtil.createSafeTransitionParticipants(it, true)
        val intent = Intent(it, cls)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(it, *pairs)
        it.startActivity(intent, options.toBundle())
        if (finishAct)
            Handler().postDelayed({ it.finish() }, 300L)
    }
}

inline fun <reified T> genericCastOrNull(anything: Any): T {
    return anything as T
}

fun TextView.startCollapsingAnimation(finalText: String, duration: Long) {
    this.startCustomAnimation(true, finalText, duration)
}

fun TextView.cancelAnimation() {
    val o = this.tag
    if (o != null && o is ValueAnimator) {
        o.cancel()
    }
}

fun TextView.startCustomAnimation(isCollapsing: Boolean, finalText: String, duration: Long) {
    cancelAnimation()
    val mStartText = this.text
    val animator =
        if (isCollapsing) ValueAnimator.ofFloat(1.0f, 0.0f)
        else ValueAnimator.ofFloat(0.0f, 1.0f)
    animator.addUpdateListener {
        val currentValue = it.animatedValue as Float
        val ended =
            (isCollapsing && currentValue == 0.0f) || (!isCollapsing && currentValue == 1.0f)
        if (ended) {
            this.text = finalText
        } else {
            val n = (mStartText.length * currentValue).toInt()
            val text = mStartText.substring(0, n)
            if (text != this.text) {
                this.text = text
            }
        }
    }
    this.tag = animator
    animator.duration = duration
    animator.start()
}

@Suppress("DEPRECATION")
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun Activity.setStatusBarGradients() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val background = resources.getDrawable(R.drawable.bg_gradient_evening_sunshine)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(android.R.color.transparent)
        window.navigationBarColor = resources.getColor(android.R.color.transparent)
        window.setBackgroundDrawable(background)
    }
}

fun Fragment.setStatusBarGradient() {
    activity?.setStatusBarGradients()
}

fun Activity.setStatusBarColor(@ColorRes id: Int) {
    window.statusBarColor = ContextCompat.getColor(this, id)
}

fun Fragment.setStatusBarColor(@ColorRes id: Int) {
    activity?.setStatusBarColor(id)
}

fun Activity.removeStatusBar() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}

fun Fragment.removeStatusBar() {
    activity?.removeStatusBar()
}

fun Context.calculateNoOfColumnsShow(): Int {
    val displayMetrics = this.resources.displayMetrics
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density
    return (dpWidth / 220).toInt()
}

fun Activity.calculateNoOfColumnsShow(): Int = applicationContext.calculateNoOfColumnsShow()

fun Fragment.calculateNoOfColumnsShow(): Int = activity?.calculateNoOfColumnsShow()!!

fun View.setDelayedClickable(clickable: Boolean, delayedMillis: Long) {
    if (delayedMillis > 0) {
        val weakView = WeakReference<View>(this)
        weakView.get()?.postDelayed({
            weakView.get()?.isClickable = clickable
        }, delayedMillis)
    } else {
        isClickable = clickable
    }
}

fun View.setDelayedClickable(clickable: Boolean) {
    setDelayedClickable(clickable, 300L)
}

fun <T> MutableLiveData<T>.updateValueIfNew(newValue: T) {
    if (this.value != newValue) value = newValue
}

fun <T> List<T>.compare(actual: List<T>): Boolean {
    for (t in this) {
        return actual.any { it == t }
    }
    return false
}

/**
 * Excute room
</T> */

//fun execute(action: () -> Unit) {
//    Completable.fromAction {
//        action.invoke()
//    }.subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe()
//}
//https://medium.com/androiddevelopers/android-data-binding-list-tricks-ef3d5630555e

fun<T> ViewGroup.bindLayout(inflater: LayoutInflater , layoutId:Int, entry: T) : ViewDataBinding = entry.let {
    val binding : ViewDataBinding = DataBindingUtil.inflate(inflater, layoutId, this, false)
//    binding.setVariable(BR.dto, it)
    return@let binding
}

fun ViewGroup.resetViews(layoutId:Int, entries: ObservableList<Any>){
    removeAllViews()
    if (layoutId == 0) {
        return
    }
    val inflater : LayoutInflater = genericCastOrNull(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)!!)
    for (entry in entries) {
        val binding =  bindLayout(inflater, layoutId, entry)
        addView(binding.root)
    }
}

fun<T> ViewGroup.resetViews(layoutId:Int, entries: List<T>){
    removeAllViews()
    if (layoutId == 0) {
        return
    }
    val inflater : LayoutInflater = genericCastOrNull(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)!!)
    for (entry in entries) {
        val binding = bindLayout(inflater, layoutId, entry)
        addView(binding.root)
    }
}

fun ViewGroup.entryChangeListener(layoutId: Int) = object : ObservableList.OnListChangedCallback<ObservableList<Any>>() {

    override fun onChanged(sender: ObservableList<Any>?) {
        resetViews(layoutId, sender!!)
    }

    override fun onItemRangeRemoved(sender: ObservableList<Any>?, positionStart: Int, itemCount: Int) {
        resetViews(layoutId, sender!!)
        TransitionManager.beginDelayedTransition(this@entryChangeListener)
        for (i in 0 until itemCount) {
            removeViewAt(positionStart)
        }
    }

    override fun onItemRangeMoved(sender: ObservableList<Any>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
        TransitionManager.beginDelayedTransition(this@entryChangeListener)
        for (i in 0 until itemCount) {
            val view = getChildAt(fromPosition)!!
            removeViewAt(fromPosition)
            val destination = if (fromPosition > toPosition) toPosition + i else toPosition
            addView(view, destination)
        }
    }

    override fun onItemRangeInserted(sender: ObservableList<Any>?, positionStart: Int, itemCount: Int) {
        val end = positionStart + itemCount
        TransitionManager.beginDelayedTransition(this@entryChangeListener)
        val inflater : LayoutInflater = genericCastOrNull(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)!!)
        for (i in end - 1 downTo positionStart){
            val data = sender?.get(i)!!
            val binding = bindLayout(inflater, layoutId, data)
            addView(binding.root, positionStart)
        }
    }

    override fun onItemRangeChanged(sender: ObservableList<Any>?, positionStart: Int, itemCount: Int) {
        val end = positionStart + itemCount
        val inflater : LayoutInflater = genericCastOrNull(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)!!)
        TransitionManager.beginDelayedTransition(this@entryChangeListener)
        for (i in positionStart until end){
            val data = sender?.get(i)!!
            val binding = bindLayout(inflater, layoutId, data)
//            binding.setVariable(BR.dto, data)
            removeViewAt(i)
            addView(binding.root, i)
        }
    }
}

/**
 * Observes only non-null content
 */
fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, nonNullContent: T.() -> Unit) {
    this.observe(owner, androidx.lifecycle.Observer {
        it?.let(nonNullContent)
    })
}
