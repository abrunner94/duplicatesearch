# DuplicateSearch
Duplicate Search is a search utility written in JavaFX with the goal of finding duplicate files.
The duplicate files are found by comparing the hash codes of the files searched within a directory.

## Threads
With the addition of the `Task<Void>` interface, it is now much easier to develop multi-threaded applications - DuplicateSearch searches for files within a separate thread.

## Tables
While searching for duplicate files, a `TableView` structure is constantly being updated in a separate thread. Whenever a new duplicate file is found, the file gets added to a `TableView` structure and marked as such.

## Files
The `FileUtils` library offers a great tool set when iterating over multiple directories and files. However, there are certainly some things to improve on in the code - and, I would say, some of it may even seem way too outdated already.

## Improvements
- [ ] Make better use of the file structure, such as using `Files.walk()` for example.
- [ ] Allow for file filtering (possible using Java 8 lambdas and the `Path` class.
