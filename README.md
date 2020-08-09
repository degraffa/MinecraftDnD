# MinecraftDnD
Play Dungeons and Dragons in Minecraft!

## Commands
### Roll Commands
|        Command                     |                        Description                                  |   |   |   | 
|------------------------------------|---------------------------------------------------------------------|---|---|---|
|  /roll <dice notation>             | Rolls a specified dice roll using d20 notation.                     |   |   |   |
| /rolldm <dice notation>            | Like roll, but the results are only sent to you and DMs             |   |   |   |
| /rollme<dice notation>             | Like roll, but the results are only sent to you                     |   |   |   |
| /rollto <username> <dice notation> | Like roll, but the results are only sent to you and to another user |   |   |   |

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
    - Pattern 2: [h/H]**X** after a dice set.
- 4d6L - Rolls four 6 sided dice and drops the lowest result
    - Pattern 1: [l/L] after dice set
    - Pattern 2: [l/L]**X** after dice set
- +d20 - Rolls a 20 sided die at advantage
    - Pattern: +d**X**
    - Macro for 2d**X**L
- \-d20 - Rolls a 20 sided die at disadvantage
    - Pattern: \-d**X**
    - Macro for 2d**X**H