# MinecraftDnD
Play Dungeons and Dragons in Minecraft! A Spigot server plugin.

## Installation
Step 1: Create a spigot server following [these instructions](https://www.spigotmc.org/wiki/spigot-installation/).  
<br />
Step 2: Download the latest jar of MinecraftDnD [here](https://github.com/degraffa/MinecraftDnD/releases).  
<br />
Step 3: Paste **MCDnD.jar** into your spiogt server's **plugins** folder.  
<br />
Step 4: Set up user permissions as listed below or just op all players.   
\- Can use another plugin such as [LuckPerms](https://www.spigotmc.org/resources/luckperms.28140/). 

Step 5: Start the Spigot server. You should see minecraft-dnd enable itself during startup.

## Permissions  
|  Permission  | Description                                                                                                       |
|--------------|-------------------------------------------------------------------------------------------------------------------|
| mcdnd.player | A player of DnD. Has access to rolling, character creation, and generally anything a player should be able to do. |
| mcdnd.dm     | A DM of DnD. Has access to everything related to Minecraft DnD.                                                   |

## Commands  
### Roll Commands
|        Command                     |                        Description                                  | 
|------------------------------------|---------------------------------------------------------------------|
|  /roll <dice notation>             | Rolls a specified dice roll using d20 notation.                     |
| /rolldm <dice notation>            | Like roll, but the results are only sent to you and DMs             |
| /rollme<dice notation>             | Like roll, but the results are only sent to you                     |
| /rollto <username> <dice notation> | Like roll, but the results are only sent to you and to another user |

## Dice Notation
Unless otherwise noted, bold letters like **A** and **X** represent integers

- 4d20 - Rolls four 20 sided die
    - Pattern: **A**d**X**
- d6 - Rolls a single 6 sided die
    - Pattern: d**X**
- 1d20 + 1d6 - Rolls one 20 sided die and 1 6 sided die
    - Pattern: +/- in between dice sets
- 3d8H - Rolls three 8 sided dice and drops the highest result
    - Pattern 1: [h/H] after a dice set
    - Pattern 2: [h/H]**X** after a dice set
        - Drops the highest **X** rolls
- 4d6L - Rolls four 6 sided dice and drops the lowest result
    - Pattern 1: [l/L] after dice set
    - Pattern 2: [l/L]**X** after dice set
        - Drops the lowest **X** rolls
- +d20 - Rolls a 20 sided die at advantage
    - Pattern: +d**X**
    - Macro for 2d**X**L
- \-d20 - Rolls a 20 sided die at disadvantage
    - Pattern: \-d**X**
    - Macro for 2d**X**H
