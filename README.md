# filehasher

### What the hell for?

Simple tool to generate SHA1 hash for specified files.

### Ho to use

1. Build with mvn package
2. Run with **./scripts/filehasher**

### Options available

 - **-d** or **--dir** Specifies the directory containing files to generate hashes for. Uses execution directory if not provided.
 - **-e** or **--ext** Specifies the extension of the files to hash. Generates hashes for all files in the directory if not specified.
 - **-c** or **--copy** If provided, generated hash will be appended to the files and and files will be copied to specified folder.
 - **-h** or **--help** Displays help.