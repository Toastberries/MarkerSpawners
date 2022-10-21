# MarkerSpawners

## About
A simple mythicmobs add-on plugin that gives server owners the ability to create vanilla spawners that spawn mythicmobs. These spawners can be used in generated structures also.

## Features
+ Spawn mythicmobs by ID
+ Set normally hardcoded spawner mechanics (like mob spawn limit)
+ Set specific level for spawned mobs OR use level scaling defined by mythicmobs config
+ Protect spawners from players and explosions

## Usage
##### Creating Spawners
Everything is set with entity tags! Here's the list of variables and default values and explanations. 
The tag "MarkerSpawner" is mandatory!
```
 String |      id       |  -  | ID of a MythicMob, case sensitive
Intiger |     level     | -1  | Level of spawned mob, -1 = level scaling
Intiger |  spawnLimit   |  6  | maximum number of spawned mobs inside checkDistance
 Double | checkDistance |  9  | size of cube centered on spawner that counts mobs
 Double |    yOffset    |  1  | offset upward, if 0 mobs will spawn in the floor
```
Besides these you can also use vanilla spawner settings, read up on them [here](https://minecraft.fandom.com/wiki/Spawner#Block_data)! (MaxNearbyEntities shouldn't be used)

And here are some examples of how to make a spawner. 
Entity should be marker and variable name and value should be separated with an underscore.
```
levles scaling Skeletal Knight, the simplest usage
/setblock ~ ~ ~ minecraft:spawner{SpawnData:{entity:{id:marker,Tags:[MarkerSpawner, id_SkeletalKnight]}}}

lvl 100 Skeleton King, good example for a boss (also uses custom vanilla spawner settings)
/setblock ~ ~ ~ minecraft:spawner{SpawnCount:1,MinSpawnDelay:2400,MaxSpawnDelay:2400,SpawnData:{entity:{id:marker,Tags:[MarkerSpawner, id_SkeletonKing, level_100, spawnLimit_1, CheckDistance_100]}}}

level scaling AngrySludge that spawns 20 blocks in the air for a sure suprise
/setblock ~ ~ ~ minecraft:spawner{SpawnData:{entity:{id:marker,Tags:[MarkerSpawner, id_AngrySludge, yOffset_20]}}}
```

##### Protecting Spawners
All spawners which spawn markes are protected automagically, they cannot be broken or blown up. To be able to break the spawner the player must have the "markerspawners.break" permission node or OP


##### So...
This plugin isn't that user-friendly right now as it was created specifically for a project, may add commands later to get spawners instead.

