# TODO

1. **Runaway EOF loop** — `mainLoop()` in `Generator.java` catches `keyboard.nextLine()` failures, prints the menu, then continues even when there is no input left. Running the CLI without stdin causes a tight loop and high CPU until killed.

2. **Tests fail on current Java** — `GeneratorTest` mocks `Scanner`, and `mockito-inline:4.11.0` / Byte Buddy do not support Java 21. Failure: "Mockito cannot mock this class: class java.util.Scanner" and "Java 21 (65) is not supported by the current version of Byte Buddy". See `GeneratorTest.java` and `build.gradle`.

3. **CLI crashes on zero/negative password length** — `requestPassword()` only validates that the length is an integer. Entering 0 or a negative number causes `GeneratePassword()` to throw `IllegalArgumentException`, uncaught in the CLI flow. See `Generator.java`.

4. **Generator API crashes with empty alphabet** — `new Generator(false, false, false, false).GeneratePassword(1)` reaches `charAt(0)` on an empty string instead of rejecting the configuration cleanly. The CLI tries to prevent this, but the public/protected generation path itself is unsafe. See `Generator.java`.

5. **Password generation uses `Math.random()`** — Not intended for secret generation; generated passwords should use a cryptographically secure RNG (e.g. `SecureRandom`). See `Generator.java`.

6. **Strength checker mishandles passwords with spaces** — `checkPassword()` reads with `keyboard.next()`, so it only checks the first whitespace-delimited token. The remainder stays in the scanner and can be misread as menu input afterward. See `Generator.java`.
