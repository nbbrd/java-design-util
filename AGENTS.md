# AGENTS.md — Java Design Util

## Overview

**Java Design Util** (`com.github.nbbrd.java-design-util`) is a lightweight Java library that provides source-level annotations to document and enforce design decisions at compile time.
It targets Java 8+ and is published to Maven Central under the EUPL license.

The annotations cover:
- **Design patterns**: `@BuilderPattern`, `@DecoratorPattern`, `@FacadePattern`, `@FlyweightPattern`, `@PrototypePattern`, `@Singleton`
- **Type constraints**: `@Immutable`, `@Mutable`, `@SealedType`, `@DirectImpl`, `@Trait`, `@PrimitiveReplacementOf`
- **Thread-safety**: `@ThreadSafe`, `@NotThreadSafe`, `@ThreadHostile`
- **Representation**: `@RepresentableAs`, `@RepresentableAsInt`, `@RepresentableAsString`, `@StringValue` (deprecated)
- **Method contracts**: `@StaticFactoryMethod`, `@ReturnNew`, `@ReturnImmutable`, `@Unsafe`, `@Demo`, `@SystemDependent`
- **Lifecycle & intent**: `@Development`, `@Internal`, `@VisibleForTesting`, `@MightBePromoted`, `@MightBeGenerated`, `@NextJdk`, `@LombokWorkaround`
- **Value constraints**: `@NonNegative`
- **Naming**: `@ClassNameConstant`
- **Processing control**: `@SkipProcessing`
- **Swing-specific**: `@swing.OnEDT`, `@swing.OnAnyThread`

A companion annotation processor validates a subset of these annotations during compilation, emitting errors when structural rules are violated (e.g. `@Immutable` types must be `final` with `private final` fields, `@DirectImpl` types must not extend a class).

## Architecture

The project is a multi-module Maven build with three modules:

```
java-design-parent (root POM)
├── java-design-annotation   ─ compile-time annotations (no dependencies)
├── java-design-processor    ─ annotation processor (shaded jar, depends on annotation module + JavaPoet)
└── java-design-bom          ─ Bill of Materials POM for consumers
```

### `java-design-annotation`
- **Package**: `nbbrd.design` (+ `nbbrd.design.swing`)
- Pure annotation definitions — no runtime dependencies, no annotation processors
- JPMS module name: `nbbrd.design` (via `Automatic-Module-Name` manifest entry)
- All annotations use `@Retention(SOURCE)` except `@Internal` (`RUNTIME`), `@PrimitiveReplacementOf` and `@SystemDependent` (`CLASS`)

### `java-design-processor`
- **Public package**: `nbbrd.design.processor` (contains only a placeholder class for Javadoc)
- **Internal package**: `internal.nbbrd.design` — one `AbstractProcessor` subclass per validated annotation
- **Internal utilities**: `internal.nbbrd.design.proc` — shared processing helpers (`Rule`, `Processing`, `Processors`, `Elements2`, `TypeRules`, `ExecutableRules`)
- Processors are registered via `@ServiceProvider(Processor.class)` (using `java-service-util`), which generates `META-INF/services/javax.annotation.processing.Processor`
- Validation is rule-based: each processor defines a `Rule<TypeElement>` (or `Rule<Element>`) chain that checks structural constraints and reports errors through `Messager`
- The `@SkipProcessing` annotation lets users bypass validation with a documented reason
- **JavaPoet** (`com.squareup:javapoet`) is shaded and relocated into the jar to avoid classpath conflicts
- Tests use `compile-testing` (Google) to verify processor diagnostics against source fixtures

### `java-design-bom`
- A `pom`-packaged module that declares `dependencyManagement` for `java-design-annotation` and `java-design-processor`
- Houses release automation (JReleaser, changelog extraction)

## Build & Test

```shell
mvn clean install                 # full build + tests + enforcer checks
mvn clean install -Pyolo          # skip all checks (fast local iteration)
mvn test -pl <module-name> -Pyolo # fast test a single module
mvn test -pl <module-name> -am    # full test a single module
```

- **Java 8 target** with JPMS `module-info.java` compiled separately on JDK 9+ (see `java8-with-jpms` profile in root POM)
- **JUnit 5** with parallel execution enabled (`junit.jupiter.execution.parallel.enabled=true`); **AssertJ** for assertions
- `heylogs-api` publishes a **test-jar** (`tests/` package) reused by extension modules for shared test fixtures

## Key Conventions

- **Lombok**: use lombok annotations when possible. Config in `lombok.config`: `addNullAnnotations=jspecify`, `builder.className=Builder`
- **Nullability**: `@org.jspecify.annotations.Nullable` for nullable; `@lombok.NonNull` for non-null parameters. Return types use `@Nullable` or the `OrNull` suffix (e.g., `getThingOrNull`)
- **Design annotations** use annotations from `java-design-util` such as `@VisibleForTesting`, `@StaticFactoryMethod`, `@DirectImpl`, `@MightBeGenerated`, `@MightBePromoted`
- **Internal packages**: `internal.<project>.*` are implementation details; public API lives in the root and `spi` packages
- **Static analysis**: `forbiddenapis` (no `jdk-unsafe`, `jdk-deprecated`, `jdk-internal`, `jdk-non-portable`, `jdk-reflection`), `modernizer`
- **Reproducible builds**: `project.build.outputTimestamp` is set in the root POM
- **Formatting/style**: 
  - Use IntelliJ IDEA default code style for Java
  - Follow existing formatting and match naming conventions exactly
  - Follow the principles of "Effective Java"
  - Follow the principles of "Clean Code"
- **Java/JVM**: 
  - Target version defined in root POM properties; some modules may require higher versions
  - Use modern Java feature compatible with defined version

## Agent behavior

- Do respect existing architecture, coding style, and conventions
- Do prefer minimal, reviewable changes
- Do preserve backward compatibility
- Do not introduce new dependencies without justification
- Do not rewrite large sections for cleanliness
- Do not reformat code
- Do not propose additional features or changes beyond the scope of the task
