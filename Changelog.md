# cclib Changelog

## Legend

- `[+]` Added for new features
- `[-]` Removed for now removed features
- `[C]` Changed for changes in existing functionality
- `[F]` Fixed for any bug fixes
- `[O]` Obsolete for soon-to-be removed features
- `[T]` Technical change that doesn't affect the API (eg. refactoring)

## Next release

- [T] javadoc warning fixed in Consumo.java: @return tag cannot be used in method with void return type.

## v0.4.9

- [F] volatile modifier to every integer attribute in Semaphore: meanSleepTimeAfterAwait_ms.
- [F] volatile modifier to every integer attribute in Monitor: inPurgatory, pendingSignals and meanSleepTimeAfterAwait.
- [F] Fabrica and Consumo allow 0 mean times now.

## v0.4.8

- [T] Makefile added
- [T] Changelog.md added
- [T] README.md added
- [+] Bringing current implementation of cclib (0.4.8) under git
