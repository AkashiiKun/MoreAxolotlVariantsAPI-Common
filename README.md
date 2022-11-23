# More Axolotl Variants API
[![Java](https://img.shields.io/badge/Made%20with-JAVA-red?style=for-the-badge)](https://java.com/)
[![License: ARR](https://img.shields.io/badge/license-ARR-red.svg?style=for-the-badge)](LICENSE)

[![Modrinth](https://img.shields.io/modrinth/dt/t4Ybtys2?logo=modrinth&style=for-the-badge)](https://modrinth.com/mod/mavapi)
[![Modrinth](https://img.shields.io/modrinth/game-versions/t4Ybtys2?logo=modrinth&style=for-the-badge)](https://modrinth.com/mod/mavapi)

[![Downloads](http://cf.way2muchnoise.eu/full_709964_downloads.svg?badge_style=for_the_badge)](https://www.curseforge.com/minecraft/mc-mods/mavapi)
[![Versions](http://cf.way2muchnoise.eu/versions/709964.svg?badge_style=for_the_badge)](https://www.curseforge.com/minecraft/mc-mods/mavapi)


This API simply lets you implement new axolotl variants.

To summon variants you use said identifiers. Example:

/summon minecraft:axolotl ~ ~ ~ {Variant:"minecraft:lucy"}

To add a new Axolotl Variant you just have to use these methods:
ModdedAxolotlVariant.register(new ResourceLocation("yourmodid", "yourvariantname")).natural().build(); //to set a normal variant

ModdedAxolotlVariant.register(new ResourceLocation("yourmodid", "yourvariantname")).build(); //to set a breeding only variant