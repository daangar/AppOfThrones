package com.daangar.appofthrones

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

const val URL_CHARACTERS = "http://5b9dd3fd133f660014c918ed.mockapi.io/characters/"

object CharactersRepo {

    private var characters: MutableList<Character> = mutableListOf()

/*
val characters: MutableList<Character> = mutableListOf()
get() {
if (field.isEmpty())
field.addAll(dummyCharacters())
return field
}
*/

    fun requestCharacters(context: Context,
                          success: ((MutableList<Character>) -> Unit),
                          error: (() -> Unit)) {

        if (characters.isEmpty()) {
            val request = JsonArrayRequest(Request.Method.GET, URL_CHARACTERS, null,
                    { response ->
                        characters = parseCharacters(response)
                        success.invoke(characters)
                    },
                    { volleyError ->
                        error.invoke()
                    }
            )

            Volley.newRequestQueue(context)
                    .add(request)
        } else {
            success.invoke(characters)
        }

    }

    private fun parseCharacters(jsonArray: JSONArray): MutableList<Character> {
        val characters = mutableListOf<Character>()
        for (index in 0..(jsonArray.length() - 1)) {
            val character = parseCharacter(jsonCharacter = jsonArray.getJSONObject(index))
            characters.add(character)
        }
        return characters
    }

    private fun parseCharacter(jsonCharacter: JSONObject): Character {
        with(jsonCharacter) {
            return Character(
                    getString("id"),
                    getString("name"),
                    getString("born"),
                    getString("title"),
                    getString("actor"),
                    getString("quote"),
                    getString("father"),
                    getString("mother"),
                    getString("spouse"),
                    getString("img"),
                    parseHouse(getJSONObject("house"))
            )
        }
    }

    private fun parseHouse(jsonHouse: JSONObject): House {
        with(jsonHouse) {
            return  House(
                    getString("name"),
                    getString("region"),
                    getString("words"),
                    getString("img")
            )
        }
    }

//    private fun dummyCharacters(): MutableList<Character> {
//        return (1..10).map(transform = this::intToCharacter).toMutableList()
//    }

    fun findCharacterByid(id: String) : Character? {
        return characters.find { character ->
            character.id == id
        }
    }

//    private fun intToCharacter(int: Int) : Character {
//        return Character(
//                name = "Personaje $int",
//                title = "Título $int",
//                born = "Nació en $int",
//                actor = "Actor $int",
//                quote = "Frase $int",
//                father = "Padre $int",
//                mother = "Madre $int",
//                spouse = "Espos@ $int",
//                house = dummyHouse()
//        )
//    }

//    private fun dummyHouse(): House {
//        val ids = arrayOf("stark", "lannister", "tyrell", "arryn", "baratheon",
//                "tully", "targaryen", "martell", "greyjoy", "frey")
//
//        val randomIdPosition = Random().nextInt(ids.size)
//
//        return House(name = ids[randomIdPosition],
//                region = "Regiónn",
//                words = "Lema")
//    }
}