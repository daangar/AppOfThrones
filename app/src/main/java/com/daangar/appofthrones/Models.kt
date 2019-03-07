package com.daangar.appofthrones

import android.annotation.TargetApi
import android.os.Build
import java.io.Serializable
import java.util.*

data class Character(
        var id: String = UUID.randomUUID().toString(),
        var name: String,
        var born: String,
        var title: String,
        var actor: String,
        var quote: String,
        var father: String,
        var mother: String,
        var spouse: String,
        var img: String,
        var house: House)

data class House(
        var name: String,
        var region: String,
        var words: String,
        var img: String) : Serializable {

    companion object {

        private  val DEFAULT_PALETTE = arrayOf(R.color.starkOverlay, R.color.starkBase, R.drawable.ic_stark)

        private val resources = mapOf(
                Pair("stark", arrayOf(R.color.starkOverlay, R.color.starkBase, R.drawable.ic_stark)),
                Pair("lannister", arrayOf(R.color.lannisterOverlay, R.color.lannisterBase, R.drawable.ic_lannister)),
                Pair("tyrell", arrayOf(R.color.tyrellOverlay, R.color.tyrellBase, R.drawable.ic_tyrell)),
                Pair("arryn", arrayOf(R.color.arrynOverlay, R.color.arrynBase, R.drawable.ic_arryn)),
                Pair("targaryen", arrayOf(R.color.targaryenOverlay, R.color.targaryenBase, R.drawable.ic_targaryen)),
                Pair("martell", arrayOf(R.color.martellOverlay, R.color.martellBase, R.drawable.ic_martell)),
                Pair("baratheon", arrayOf(R.color.baratheonOverlay, R.color.baratheonBase, R.drawable.ic_baratheon)),
                Pair("greyjoy", arrayOf(R.color.greyjoyOverlay, R.color.greyjoyBase, R.drawable.ic_greyjoy)),
                Pair("frey", arrayOf(R.color.freyOverlay, R.color.freyBase, R.drawable.ic_frey)),
                Pair("tully", arrayOf(R.color.tullyOverlay, R.color.tullyBase, R.drawable.ic_tully))
        )


        fun getOverlayColor(houseId: String) : Int {
            val palette = getPalette(houseId)
            return palette[0]
        }

        fun getBaseColor(houseId: String): Int {
            val palette = getPalette(houseId)
            return palette[1]
        }

        fun getIcon(houseId: String): Int {
            val palette = getPalette(houseId)
            return palette[2]
        }

        private fun getPalette(houseId: String): Array<Int> {
            val palette = resources[houseId]

            return palette ?: DEFAULT_PALETTE
        }

    }

}