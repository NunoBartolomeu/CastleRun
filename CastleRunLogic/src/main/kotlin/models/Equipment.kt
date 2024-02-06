package org.example.models

data class Equipment(val mainClazz: Clazz, val type: Type) {
    enum class Clazz { Utility, Defense, Offense }
    enum class Type { Sword, Axe, Shield, Bow, Boots, Cape, Dagger, Robe, Chestplate, Rope , Helm , Goat}
}
