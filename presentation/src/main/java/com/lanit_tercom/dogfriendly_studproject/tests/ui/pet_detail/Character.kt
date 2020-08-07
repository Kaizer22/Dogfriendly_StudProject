package com.lanit_tercom.dogfriendly_studproject.tests.ui.pet_detail

import com.lanit_tercom.dogfriendly_studproject.R

class Character(private val name: String, private val image: Int) {

    fun getName(): String { return name }
    fun getImage(): Int {return image }

    companion object{
        fun generateCharacters(): List<Character>{
            val output: ArrayList<Character> = ArrayList()

            output.add(Character("Активная", R.drawable.ic_round_circle))
            output.add(Character("Добрая", R.drawable.ic_round_circle))
            output.add(Character("Гордая", R.drawable.ic_round_circle))
            output.add(Character("Трусливая", R.drawable.ic_round_circle))

            output.add(Character("Агрессивная", R.drawable.ic_round_circle))
            output.add(Character("Гроза белок", R.drawable.ic_round_circle))
            output.add(Character("Сорванец", R.drawable.ic_round_circle))
            output.add(Character("Веселая", R.drawable.ic_round_circle))

            output.add(Character("Боевая", R.drawable.ic_round_circle))
            output.add(Character("Спортивная", R.drawable.ic_round_circle))
            output.add(Character("Неусидчивая", R.drawable.ic_round_circle))
            output.add(Character("Застенчивая", R.drawable.ic_round_circle))

            return output
        }
    }
}