package com.camera.utils

enum class PlatformTypes {
    Mobile, Web;

    override fun toString(): String {
      return  when(this) {
            Mobile -> "M"
            Web -> "W"
        }
    }
}