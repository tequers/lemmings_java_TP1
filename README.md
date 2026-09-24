# Lemmings

A console version of the 1991 puzzle game, written in Java. Lemmings walk across a 10x10 board on their own. You cannot steer them directly. You change what they *are*: give one a parachute before it falls to its death, or turn another into a digger to open a floor. Get enough of them to the exit door and you win.

The game grew over three university assignments, and two constraints shaped the code more than the game did. The first was a ban: `instanceof` and `getClass()` were forbidden outright, and using either meant an automatic fail. Objects here have to work out who they are talking to without ever asking. The second arrived with the third assignment, which required saving a game to a file and loading it back, so every error had to become a typed exception carrying enough detail to tell the player what was wrong with their file.

```
Number of cycles: 3
Lemmings in board: 6
Dead lemmings: 0
Lemmings exit door: 0 ┃2

      1    2    3    4    5    6    7    8    9   10
   ┌——————————————————————————————————————————————————┐
  A┃                                     ᗺ            ┃A
  B┃                                        ▓▓▓▓▓▓▓▓▓▓┃B
  C┃                                                  ┃C
  D┃                           B   B🪂                ┃D
  E┃          ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓  B                      ┃E
  F┃               ▓▓▓▓▓ 🚪            ▓▓▓▓▓          ┃F
  G┃               XXXXX▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓          ┃G
  H┃                                                  ┃H
  I┃                                        ▓▓▓▓▓     ┃I
  J┃▓▓▓▓▓▓▓▓▓▓  B                           ▓▓▓▓▓▓▓▓▓▓┃J
   └——————————————————————————————————————————————————┘
      1    2    3    4    5    6    7    8    9   10

Command >
```

One lemming is descending under a parachute at D8. The `XXXXX` block at G4 is a metal wall, which no digger can get through. Two lemmings need to reach the door at F6.

## Run it

You need a JDK. The code compiles and runs on JDK 22.

```sh
javac -d bin $(find src -name "*.java" ! -name "Tests.java")
java -Dstdout.encoding=UTF-8 -cp bin tp1.Main 2
```

The argument is the level, from `0` to `2`. If you leave it out, you get level 0.

On Windows, keep `-Dstdout.encoding=UTF-8`. Without it the console codepage mangles the box-drawing characters and the board becomes unreadable.

`Tests.java` is excluded from the build above because it needs JUnit 5 on the classpath. See [Tests](#tests).

## Play it

| Command | Shortcut | Effect |
| --- | --- | --- |
| `setRole ROLE ROW COL` | `sr` | Give the lemming at that cell a role. Example: `sr Parachuter A 8` |
| `none` | `n`, or press return | Advance one cycle without acting |
| `reset [level]` | `r` | Restart, optionally on a different level |
| `load FILE` | `l` | Replace the current game with one read from a file |
| `save FILE` | `s` | Write the current game to a file |
| `help` | `h` | List the commands |
| `exit` | `e` | Quit |

Rows are letters `A` to `J`, columns are numbers `1` to `10`, and `ConsoleView` translates from the zero-based coordinates used internally.

| Symbol | Meaning |
| --- | --- |
| `B` | Lemming walking right |
| `ᗺ` | Lemming walking left |
| 🪂 | Lemming with the parachuter role |
| ``´･ω･` `` | Lemming with the downcaver role |
| `▓▓▓▓▓` | Wall. A downcaver can dig through it |
| `XXXXX` | Metal wall. Nothing gets through it |
| 🚪 | Exit door |

A lemming walks one cell per cycle. It turns around at a wall or a board edge, and it falls when nothing solid is under it. Falling is what kills them: a lemming that lands after dropping three rows or more dies on impact, and so does one that falls off the bottom of the board. You win when no lemmings are left on the board and at least the level's quota reached the door.

Every lemming starts as a walker. The other two roles are temporary, and each demotes itself back to walker when its job is done. The **parachuter** pins the fall force at zero, so the lemming survives any drop, but it only affects a lemming already in the air. The **downcaver** digs through the wall directly below, dropping one row per cycle, which means it never builds up a fatal fall. It switches off when there is nothing left to dig or when it meets metal.

## Architecture

Model-View-Controller across five packages.

```
src/tp1/
├── Main.java               entry point
├── Tests.java              golden-file test harness
├── control/
│   ├── Controller.java     the game loop, and the one place errors are rendered
│   └── commands/           Command pattern: one class per command
├── exceptions/             9 checked exception types
├── logic/                  the model
│   ├── Game.java           state, counters, save and load
│   ├── GameModel.java      ─┐
│   ├── GameStatus.java      ├─ four interfaces onto the model
│   ├── GameWorld.java       │
│   ├── GameConfiguration.java ─┘
│   ├── FileGameConfiguration.java   parses a saved game
│   ├── GameObjectContainer.java
│   ├── Position.java       immutable value type
│   ├── Direction.java      enum carrying an (x,y) delta
│   ├── gameobjects/        Lemming, Wall, MetalWall, ExitDoor, GameObjectFactory
│   └── lemmingRoles/       Walker, Parachuter, DownCaver, LemmingRoleFactory
├── util/
└── view/                   board rendering, ANSI colours, all user-facing strings
```

### One model, four interfaces

`Game` implements three interfaces at once, and each collaborator sees only the slice it needs.

| Interface | Used by | Exposes |
| --- | --- | --- |
| `GameStatus` | The view | Cycle count, lemming counts, `positionToString` |
| `GameModel` | Commands | `update`, `reset`, `exit`, `setRole`, `load`, `save` |
| `GameWorld` | Game objects | `isSolid`, `isInAir`, `lemmingArrived`, `addDeadLemming` |

A `Lemming` holds a `GameWorld` reference and nothing else. It cannot reach `Game`, cannot reach the container, and cannot see the other objects on the board. It asks questions about the world and reports what happened to it. That is the whole of its access.

`GameConfiguration` is the fourth, and it is the one that earns its keep. It describes a starting state: the five counters, plus the objects. `FileGameConfiguration` implements it by reading a file, and `Game.init(GameConfiguration)` takes either that or a hardcoded level without knowing which it got. The model never learns that files exist.

### Objects interact without asking what they are

With `instanceof` banned, an object cannot branch on the type of whatever it bumps into. The way out is double dispatch, where two virtual calls together resolve one pair-specific behaviour.

`GameItem` declares a `receiveInteraction` method and one `interactWith` overload per interaction target:

```java
public boolean receiveInteraction(GameItem other);
public boolean interactWith(Lemming lemming);
public boolean interactWith(Wall wall);
public boolean interactWith(ExitDoor door);
```

Every object implements `receiveInteraction` identically, as `other.interactWith(this)`. The trick is that `this` has a different static type in each class, so the compiler picks a different overload in each one. `Wall` sends the call to `interactWith(Wall)`, and `ExitDoor` sends it to `interactWith(ExitDoor)`. Neither object ever tested a type.

A digging lemming walks this path:

1. `GameObjectContainer.receiveInteractionsFrom` offers the lemming to every object on the board.
2. `Wall.receiveInteraction` calls back `other.interactWith(this)`, which resolves to `Lemming.interactWith(Wall)`.
3. `Lemming` forwards it again, to its current role: `return this.role.interactWith(obj, this)`.
4. `DownCaverRole.interactWith(Wall, Lemming)` checks the wall is the one directly below, then destroys it.

Step 3 is the part worth pointing at. The dispatch goes one level deeper than the usual textbook version, past the object and into its current role, so the same `Lemming` reacts differently to the same `Wall` depending on what it currently is. `AbstractRole` returns `false` for every interaction by default, which is why a plain walker ignores a wall it could otherwise dig.

### One parsing idiom, three factories

Commands, roles and game objects all arrive as text, from the keyboard or from a saved file. All three are parsed the same way. Each factory holds a static list of prototype instances, asks each one in turn whether it recognises the input, and returns the first that does:

```java
public static GameObject parse(String line, GameWorld game) throws ObjectParseException, OffBoardException {
	for (GameObject go : availableGameObjects) {
		GameObject gObj = go.parse(line, game);
		if (gObj != null) return gObj;
	}
	throw new ObjectParseException(Messages.UNKNOWN_GAME_OBJECT.formatted(line));
}
```

`CommandGenerator`, `LemmingRoleFactory` and `GameObjectFactory` are the same shape down to the loop. Adding a command, a role or a board object means writing the class, adding its strings to `Messages`, and adding one line to the matching list. Nothing else changes, and the help text picks new entries up on its own because it is built from the same list.

Reading the three side by side is the clearest argument in the repo that the design settled on something. In the previous version the two factories that existed returned `null` when nothing matched, which every caller had to remember to check. All three now throw.

### Errors are typed exceptions, not return codes

Nine checked exception classes replace the earlier style of returning `false` or `null` on failure.

```
Exception
├── CommandException
│   ├── CommandExecuteException
│   └── CommandParseException
├── GameLoadException
└── GameModelException
    ├── GameParseException
    │   ├── ObjectParseException
    │   └── RoleParseException
    └── OffBoardException
```

The point is what happens to the detail. A bad line deep inside a config file throws `ObjectParseException` with the offending text attached. `FileGameConfiguration` turns that into a `GameLoadException`. `LoadCommand` wraps it again as a `CommandExecuteException`, this time keeping the original as the cause. `Controller` then renders both:

```java
catch (CommandException e) {
	view.showError(e.getMessage());
	Throwable cause = e.getCause();
	if (cause != null)
		view.showError(cause.getMessage());
}
```

So the player sees where the problem is and what it was:

```
[ERROR] Error: Invalid file "conf_4" configuration
[ERROR] Error: Object position is off board: "(3,18) Lemming RIGHT 10 Walker"
```

One catch block in the controller handles every failure in the program. Because the exceptions are checked, a call site cannot quietly ignore one the way it could ignore a `null`.

### Levels are files

A saved game is a header line of five counters followed by one line per object:

```
0 4 0 0 4
(3,2) Lemming  RIGHT 0 Walker
(1,0) Wall
(1,4) MetalWall
(7,4) ExitDoor
```

Writing and reading are symmetric per class. Each `GameObject` subclass has a `toString` that produces its line and a `parse` that reads it back, and `AbstractRole.toString` supplies the role name at the end of a lemming's line. `save` writes `Game.toString()`, and `load` builds a `FileGameConfiguration` from the result.

The repository root carries `conf_0` and `conf_1`, two playable levels defined entirely as data. It also carries `conf_2` through `conf_8`, which are deliberately broken, one per failure mode: an unknown object type, an off-board column, an unparseable direction, a direction that is valid but not walkable, an unknown role, a non-numeric coordinate, and a malformed header. They exist so the test suite can exercise every branch of the exception hierarchy above.

Hardcoded levels and file levels coexist through one nullable field. `Game` remembers the last `GameConfiguration` it loaded, and a bare `reset` either re-runs the hardcoded level or re-reads that configuration.

## Tests

The tests are golden-file snapshots. `Tests.java` redirects `System.in` and `System.out`, replays a recorded script of commands through `Main.main`, and compares stdout line by line against a checked-in expected file.

Running all ten fixtures in `tests/pr3+reset+save/` against the current code:

| Fixture | Expected lines | Differing |
| --- | --- | --- |
| `00_1-play` | 243 | 1 |
| `01_1-command` | 365 | 12 |
| `01_2-play` | 243 | 1 |
| `01_3-newRoles` | 551 | 2 |
| `01_4-newRoles_errors` | 280 | 9 |
| `01_5-newRoles_conf_0` | 471 | 2 |
| `01_6-file_errors` | 92 | 9 |
| `01_7-file_reset` | 91 | 0 |
| `02_1-newRoles` | 246 | 7 |
| `02_2-reset_load_save` | 209 | 1 |
| **Total** | **2791** | **44** |

The comparison demands exact equality, so those 44 lines mean the suite reports failures. They fall into three groups:

| Cause | Lines |
| --- | --- |
| A blank line the reference prints after an error message | 29 |
| Help text indented with a tab where the reference uses spaces | 9 |
| The exit door drawn before a lemming standing on it, not after | 6 |

All three are formatting. Board states, positions, cycle counts, death counts and exit counts all match. The course staff reviewed these differences at the time and accepted them as display issues.

Reproduce the table with:

```sh
for f in tests/pr3+reset+save/*_input.txt; do
	b=$(basename "$f" _input.txt)
	java -Dstdout.encoding=UTF-8 -cp bin tp1.Main "$(echo "$b" | cut -c1-2)" NO_COLORS < "$f" > actual.txt
	n=$(diff <(tr -d '\r' < "tests/pr3+reset+save/${b}_expected.txt") <(tr -d '\r' < actual.txt) | grep -c '^<')
	echo "$b: $n differing"
done
```

## Honest notes

This is coursework from late 2024. The code is as submitted, apart from one bug fix described below. What follows is what I would still change.

**Fixed after submission: loading a game forgot which way a lemming was walking.** A saved game wrote each lemming's direction correctly, but a loaded lemming always faced right. Two bugs in `Lemming` caused this, and both had to be fixed before the round trip worked. `getLemmingDirectionFrom` mapped `"LEFT"` to `Direction.RIGHT`. Also, `copy()` built the new lemming with the constructor, which sets the direction to `RIGHT` and the fall height to `0`. A loaded game is copied into play, so the copy reset the direction even after it was parsed correctly. `copy()` now carries both fields over. This took the save-and-load fixture from 6 differing lines to 1, and the remaining line is a missing blank line.

**A failed save tells you nothing.** `SaveCommand` catches `GameModelException` and throws `new CommandExecuteException()` with no message and no cause, so the player sees `[ERROR] Error: null`. The load path carries a formatted message and its cause all the way to the user. Save and reset both discard theirs. The mechanism is right and two call sites do not use it.

**`MetalWall.copy(GameWorld, Position)` returns a `new Wall`**, which would silently turn metal into something diggable. It never fires, because `MetalWall` also overrides `parse` and constructs itself directly, duplicating the inherited method body rather than calling `copy`. Dead code covering a bug is worse than either alone.

**`AbstractRole.equals` uses `getClass()`** to decide whether two roles are the same type. It is the idiomatic place for it, and it is also the one spot in the codebase that answers the question the assignment banned.

**`GameConfiguration` declares `numLemingsExit` and `numLemmingToWin`**, each missing a letter, while its sibling `GameStatus` spells both correctly. It compiles because each implementer follows its own interface.

**`reset` declares `throws GameLoadException` but never throws it.** The signature exists to satisfy an interface shaped by the file-loading feature.

Two more things about the repository rather than the code. The file named `patata` at the root is a saved game left over from testing, not content. And `Tests.java` uses reflection to check whether `SaveCommand` exists and what `ResetCommand` extends, then picks one of three fixture directories accordingly. That is shared course grading infrastructure built to run against many students' submissions, not test design of ours.

## Built by

Alberto Antequera Fernández-Palacios and Jose Baute Fariña.

Universidad Complutense de Madrid, Técnicas de Programación, 2024-25. Built over 59 commits between November and December 2024. The briefs for the first three assignments are in [`docs/`](docs/), and they are the source for the rules and constraints described above.
