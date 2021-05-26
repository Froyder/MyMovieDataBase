package com.example.mymoviedatabase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val name : String = "",
    val realisedAt: Int = 0,
    val genre : String = "",
    val rating : Double = 0.0,
    val description :String = ""
) : Parcelable

fun getDefaultMovie() = Movie("Avengers 6", 2028, "comedy", 7.7,
    "They gonna save the planet. Again!" )

fun getNewListFromStorage(): List<Movie> {
    return listOf(
        Movie("\"Avengers 1\"", 2020, "comedy", 7.7,
            "They gonna save the planet. Again!" ),
        Movie("\"Avengers 2\"", 2022, "comedy", 7.7,
            "They gonna save the planet. Again!" ),
        Movie("\"Avengers 3\"", 2023, "comedy", 7.7,
            "They gonna save the planet. Again!" ),
        Movie("\"Avengers 4\"", 2024, "comedy", 7.7,
            "They gonna save the planet. Again!" ),
        Movie("\"Avengers 5\"", 2025, "comedy", 7.7,
            "They gonna save the planet. Again!" ),
        Movie("\"Avengers 6\"", 2026, "comedy", 7.7,
    "They gonna save the planet. Again!" ))
}

fun getPopularListFromStorage(): List<Movie> {
    return listOf(
        Movie("\"Citizen Kane\"", 1941, "drama", 8.8,
            "Following the death of publishing tycoon Charles Foster Kane, reporters scramble to uncover the meaning of his final utterance; 'Rosebud'." ),
        Movie("\"The Shawshank Redemption\"", 1994, "drama", 9.3,
            "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency."),
        Movie("\"The Godfather\"", 1972, "crime, drama", 9.2,
            "An organized crime dynasty's aging patriarch transfers control of his clandestine empire to his reluctant son."),
        Movie("\"The Dark Knight\"", 2008, "action, crime, drama", 9.0,
            "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, " +
                    "Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice."),
        Movie("\"12 Angry Men\"", 1957, "crime, drama", 8.9,
            "A jury holdout attempts to prevent a miscarriage of justice by forcing his colleagues to reconsider the evidence."),
        Movie("\"Schindler's List\"", 1993, "biography, drama, history", 8.8,
            "In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes " +
                    "concerned for his Jewish workforce after witnessing their persecution by the Nazis.")
    )
}