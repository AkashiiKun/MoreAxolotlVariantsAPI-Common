<center>

![Logo](https://github.com/AkashiiKun/MoreAxolotlVariantsAPI-Common/blob/master/img/title.png?raw=true)

[![](https://cdn.discordapp.com/attachments/895334913560178698/915683102192468018/JoinDiscord.png)](https://discord.gg/7BSqZa9r3P)
[![](https://media.discordapp.net/attachments/895334913560178698/915683907649822750/reportissue.png)](https://github.com/AkashiiKun/MoreAxolotlVariantsAPI-Common/issues)

[![Modrinth](https://img.shields.io/modrinth/dt/t4Ybtys2?logo=modrinth&style=for-the-badge)](https://modrinth.com/mod/mavapi)
[![Modrinth](https://img.shields.io/modrinth/game-versions/t4Ybtys2?logo=modrinth&style=for-the-badge)](https://modrinth.com/mod/mavapi)

[![Downloads](http://cf.way2muchnoise.eu/full_709964_downloads.svg?badge_style=for_the_badge)](https://www.curseforge.com/minecraft/mc-mods/mavapi)
[![Versions](http://cf.way2muchnoise.eu/versions/709964.svg?badge_style=for_the_badge)](https://www.curseforge.com/minecraft/mc-mods/mavapi)

</center>
  
&nbsp;

# **Introduction**
- Lightweight API that other mods can use to add new Axolotl Variants Variants while keeping compatibility with each other
- Does not any new variants on its own
- Gives the possibility to add custom Axolotl Bucket Item models for modded variants

# Out-Of-The-Box-Features
- Appends a tooltip for Axolotl Buckets that shows the **age**, **variant** and **mod** it comes from
- Changes the Axolotl Bucket Item Texture for all the vanilla variants to match the variant texture (Textures made by [VRAXX](https://github.com/NewSrVraxx))

&nbsp;

# **Information for everyone**

## **Summoning Command**
Summoning axolotl can be now done with a string value as the "Variant" nbt.

This is similar to the namespace system minecraft uses almost anywhere else but doesn't for some entity variants included the axolotl's.

For example this would be how to summon the vanilla lucy variant:
```
/summon minecraft:axolotl ~ ~ ~ {Variant:"minecraft:lucy"}
```

Or to summon a variant from More Axolotl:
```
/summon minecraft:axolotl ~ ~ ~ {Variant:"more-axolotl:neon"}
```

&nbsp;

# **Information for Modders**
Information dedicated to modding using the API can be found in the [Wiki](https://github.com/AkashiiKun/MoreAxolotlVariantsAPI-Common/wiki).
