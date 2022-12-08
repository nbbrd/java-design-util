# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- Add annotations to document threading in Swing components

## [1.3.1] - 2022-08-19

### Fixed

- Fix dependency inheritance in BOM

## [1.3.0] - 2022-04-07

### Added

- Add `MightBeGenerated` annotation
- Add Maven BOM

## [1.2.2] - 2022-02-14

### Fixed

- Fix BuilderPattern processor on JDK-ea

## [1.2.1] - 2022-01-20

### Fixed

- Fix release deployment on JDK17

## [1.2.0] - 2022-01-20

This release replaces the `StringValue` annotation with a more general approach that is not limited to `String`.

### Added

- Add `RepresentableAs` annotation
- Add `RepresentableAsInt` annotation
- Add `RepresentableAsString` annotation

### Changed

- Deprecate `StringValue` annotation

## [1.1.3] - 2021-03-19

### Changed

- Migration to [Maven-Central](https://search.maven.org/search?q=g:com.github.nbbrd.java-design-util)
- **Breaking change**: Maven groupId is now `com.github.nbbrd.java-design-util`

## [1.1.2] - 2021-01-07

### Fixed

- Fix error when enclosing type has generics

## [1.1.1] - 2020-12-16

### Added

- Add `StaticFactoryMethod` annotation
- Add `StringValue` annotation

### Fixed

- Fix warning on supported source version

## [1.0.0] - 2020-10-22

### Added

- Initial release

[Unreleased]: https://github.com/nbbrd/java-design-util/compare/v1.3.1...HEAD
[1.3.1]: https://github.com/nbbrd/java-design-util/compare/v1.3.0...v1.3.1
[1.3.0]: https://github.com/nbbrd/java-design-util/compare/v1.2.2...v1.3.0
[1.2.2]: https://github.com/nbbrd/java-design-util/compare/v1.2.1...v1.2.2
[1.2.1]: https://github.com/nbbrd/java-design-util/compare/v1.2.0...v1.2.1
[1.2.0]: https://github.com/nbbrd/java-design-util/compare/v1.1.3...v1.2.0
[1.1.3]: https://github.com/nbbrd/java-design-util/compare/v1.1.2...v1.1.3
[1.1.2]: https://github.com/nbbrd/java-design-util/compare/v1.1.1...v1.1.2
[1.1.1]: https://github.com/nbbrd/java-design-util/compare/v1.0.0...v1.1.1
[1.0.0]: https://github.com/nbbrd/java-design-util/releases/tag/v1.0.0