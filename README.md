# 🧪 JUnit 5 — Software Testing Course Projects

A progressive series of hands-on projects completed as part of a **Software Testing** university course. Each module builds on the previous one, introducing a new testing concept using **JUnit 5**, **Mockito**, and **Maven**.

---

## 📂 Project Structure

```
small-Projects-To-Learn-Junit-5/
├── 01-SimpleUnitTests/          # Basic unit tests with @Test and assertions
├── 02-ParameterizedTests/       # Data-driven tests with @ParameterizedTest
├── 03-Mocking Components in automated Tests/   # Dependency mocking with Mockito
└── 04-Sudoku/                   # Integration tests + unit/mock tests on a real app
```

---

## 🔢 01 — Simple Unit Tests

**Concept:** Writing your first unit tests with JUnit 5.

### What I built
A `Triangle` utility class with a single static method `numberOfEqualSides(a, b, c)` that classifies a triangle:

| Return value | Meaning |
|---|---|
| `3` | Equilateral (all sides equal) |
| `2` | Isosceles (two sides equal) |
| `1` | Scalene (no sides equal) |
| `-1` | Invalid input (zero/negative side or triangle inequality violated) |

### Test cases covered
- Equilateral triangle → `3`
- All three permutations of an isosceles triangle → `2`
- Scalene triangle → `1`
- Invalid inputs (zero side lengths) → `-1`

### What I learned
- Structuring a Maven project for testing
- Writing `@Test` methods and using `assertEquals`
- Thinking in **equivalence classes** and **boundary values** when choosing test inputs

---

## 🔄 02 — Parameterized Tests

**Concept:** Eliminating test duplication by driving one test method with many input/output pairs.

### What I built
The same `Triangle` classifier, now tested with `@ParameterizedTest` + `@CsvSource` — a single test method that covers **8 different scenarios** in a table:

```java
@CsvSource({
    "6,6,6,3",   // equilateral
    "6,6,7,2",   // isosceles (AB equal)
    "6,7,6,2",   // isosceles (AC equal)
    "7,6,6,2",   // isosceles (BC equal)
    "7,8,9,1",   // scalene
    "0,3,4,-1",  // invalid
    "1,0,4,-1",  // invalid
    "0,1,4,-1"   // invalid
})
```

### What I learned
- `@ParameterizedTest` and `@CsvSource` annotations
- How parameterized tests reduce boilerplate and make test coverage more visible
- The difference between data-driven testing and repeating nearly identical `@Test` methods

---

## 🎭 03 — Mocking Components in Automated Tests

**Concept:** Isolating the unit under test by replacing its dependencies with **Mockito mocks**.

### What I built
A new class `TriangleFromPoints` that accepts 2-D coordinates (x, y pairs) instead of pre-computed side lengths. It:
1. Calculates Euclidean distances between the three points
2. Rounds each distance to the nearest integer
3. Delegates to `Triangle.numberOfEqualSides()` for classification

```
TriangleFromPoints  ──────►  Triangle
  (point geometry)            (side classifier)
```

### Test strategy
- `TriangleFromPointsTest` uses `@Mock Triangle` so tests **verify only the geometry/rounding logic**, independent of the `Triangle` classifier.
- `verify(triangle).numberOfEqualSides(...)` confirms the exact rounded side lengths are passed through.

### Test cases
| Points | Expected rounded sides | Mocked return |
|---|---|---|
| `(0,0), (3,4), (6,0)` | `5, 5, 6` | `2` (isosceles) |
| `(0,0), (4,0), (4,3)` | `4, 3, 5` | `1` (scalene) |
| `(0,0), (1,1), (2,0)` | `1, 1, 2` | `-1` (invalid, rounding tested) |

### What I learned
- `@Mock` and `@ExtendWith(MockitoExtension.class)` setup
- `when(...).thenReturn(...)` for stubbing
- `verify(...)` to assert that the real dependency was called with the correct arguments
- **Dependency injection** via constructor to make classes testable

---

## 🟦 04 — Sudoku (Unit + Mock + Integration Tests)

**Concept:** Testing a more complex application with three layers of tests — unit tests, mock-based unit tests, and integration tests.

### What I built

#### `SudokuSolver`
A validator class with three public methods:

| Method | Purpose |
|---|---|
| `isValidSudokuBoard(board)` | Checks board is exactly 9×9 |
| `isSolvable(board)` | Checks no duplicates in any row, column, or 3×3 region |
| `isSolved(board)` | Checks every cell is filled (non-zero) |

#### `SudokuCreator`
Randomly generates a valid, solvable Sudoku board pre-filled with a requested number of cells (1–10). It:
1. Picks a random empty cell
2. Assigns a random value (1–9)
3. Asks `SudokuSolver.isSolvable()` — retries if not solvable
4. Repeats until the requested number of cells are filled

```
SudokuCreator  ──────►  SudokuSolver
   (board gen)           (validation)
```

### Tests

#### `SudokuSolverTest` — pure unit tests
| Test | What it checks |
|---|---|
| Valid 9×9 board accepted | `isValidSudokuBoard` happy path |
| Wrong row count rejected | Dimension validation |
| Wrong column count rejected | Dimension validation |
| Empty board is solvable | Zeroes don't count as duplicates |
| Duplicate in same row | `isSolvable` → false |
| Duplicate in same column | `isSolvable` → false |
| Duplicate in same 3×3 region | `isSolvable` → false |
| Partially filled valid board | `isSolvable` → true |
| Board with an empty cell | `isSolved` → false |
| Fully filled board | `isSolved` → true |

#### `SudokuCreatorTest` — Mockito unit tests
Mocks `SudokuSolver` to test `SudokuCreator` in isolation:

| Test | What it checks |
|---|---|
| Out-of-range prefill count (0, -1, 11) | `IllegalArgumentException` thrown; `isSolvable` **never** called |
| Board dimensions and filled count | Correct 9×9 structure with exactly N filled fields |
| Values inside valid range (1–9) | No cell contains an out-of-range value |
| Retry until solvable | Mock returns `false` then `true` → `isSolvable` called **twice** |

#### `SudokuCreatorITTest` — Integration tests
Wires the **real** `SudokuSolver` into `SudokuCreator` to verify end-to-end behaviour:

| Test | What it checks |
|---|---|
| Board is valid and solvable | Real solver confirms output |
| Prefill counts 1, 5, 10 | Correct number of filled fields with real logic |
| Values in range | All non-zero cells are 1–9 with real solver |
| Invalid prefill → exception | Real wiring still rejects bad input |

### What I learned
- The difference between **unit tests**, **mock-based unit tests**, and **integration tests**
- `@BeforeEach` for test setup
- `@ValueSource` with `@ParameterizedTest` for multiple scalar inputs
- `verify(..., never())` and `verify(..., times(N))` for interaction assertions
- `when(...).thenReturn(value1, value2)` to simulate a sequence of return values
- `ArgumentMatchers.any()` for flexible stubbing
- `assertThrows` for verifying exceptions
- How to design classes for testability using **constructor injection**

---

## 🛠 Tech Stack

| Tool | Version |
|---|---|
| Java | 17 |
| JUnit Jupiter | 6.0.1 |
| Mockito | 5.21.0 |
| Build Tool | Maven |
| IDE | IntelliJ IDEA |

---

## 🚀 Running the Tests

Each sub-project is an independent Maven module. Navigate into any project folder and run:

```bash
mvn test
```

Example:
```bash
cd 04-Sudoku/sudoku
mvn test
```

---

## 📚 Key Takeaways

> Testing is not just about catching bugs — it's about **designing better software**.

Working through these projects in sequence taught me:

1. **Start simple** — plain `@Test` with `assertEquals` covers most cases clearly.
2. **Parameterize to scale** — when the same logic needs many inputs, `@ParameterizedTest` keeps tests readable and exhaustive.
3. **Mock to isolate** — a failing test should point to *one* class, not a chain of dependencies.
4. **Integration tests verify the wiring** — mocks prove each component works; integration tests prove they work *together*.
5. **Testability drives design** — constructor injection, small focused classes, and clear interfaces are not just good OOP; they are requirements for good tests.
