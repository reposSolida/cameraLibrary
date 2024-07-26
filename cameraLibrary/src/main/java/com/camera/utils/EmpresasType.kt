package com.camera.utils

/**
 ** Created by Carlos A. Novaez Guerrero on 7/4/2023 1:34 PM
 ** cnovaez.dev@outlook.com
 **/
enum class EmpresasType {
    VCT, SAN_FRANCISCO, CANTERA, PGN, OPRUMIN, CANARIAS, ALGORTA;

    override fun toString(): String {
        return when (this) {
            VCT -> "Viña, Concha y Toro"
            SAN_FRANCISCO -> "San Francisco"
            CANTERA -> "Cantera"
            PGN -> "Pagnifique"
            OPRUMIN -> "Oprumin"
            CANARIAS -> "Canarias"
            ALGORTA -> "Algorta"
        }
    }

    fun companyNumber(): Int {
        return when (this) {
            VCT -> 21
            SAN_FRANCISCO -> 13
            CANTERA -> 17
            PGN -> 23
            OPRUMIN -> 24
            CANARIAS -> 26
            ALGORTA -> 22
        }
    }
}