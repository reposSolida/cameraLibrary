package com.camera.model.utils

enum class SyncFlgStateType {
    Pendiente, Sincronizado, Error, EnCurso;

    override fun toString(): String {
        return when (this) {
            Pendiente -> "P"
            Sincronizado -> "S"
            Error -> "E"
            EnCurso -> "I"
        }
    }
}
