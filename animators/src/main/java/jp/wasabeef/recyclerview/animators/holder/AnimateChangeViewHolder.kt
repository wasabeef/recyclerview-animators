package jp.wasabeef.recyclerview.animators.holder

interface AnimateChangeViewHolder {

    fun canReuseUpdatedViewHolder(payloads: MutableList<Any>): Boolean

}