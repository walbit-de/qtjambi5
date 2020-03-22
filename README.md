# Qt Jambi 5+

* [Code repository](https://github.com/walbit-de/qtjambi5)

## Qt Jambi 5 is alive :)
![about qtjambi5](/info/about.png)

## Build Notes

Clone jambi5 source from git. https://github.com/walbit-de/qtjambi5.git

`~/> git clone https://github.com/tilialabs/qtjambi5.git ~/qtjambi5`

### Windows Build Environment

Use a Visual Studio 2013 command prompt with additional environment variables
to point the the Qt source and binary directories.  See qt5vars.cmd.  Create a
shortcut to use the qt5vars.cmd; set the 'Target' to 
`C:\Windows\System32\cmd.exe /E:ON /V:ON /k qt5vars.cmd`

### Build Qt Sources

Unpack, checkout or whatever the Qt source.  Version 5.12.8 is known to work,
later versions may work as well.  Source distributions can be found at
https://code.qt.io/cgit/qt/qt5.git/.  I would recommend the 
official Qt GIT repo.  Cpp source files need to be
modified, binary only distributions will not work.

Modify qt source to re-add jambi support.  There are patch files in the jambi
directory that need to be applied.  There are unix and windows flavours, I
believe they only differ in the line endings.

```
~/qt5> git apply --ignore-whitespace ~/qtjambi5/Qt5-patches/qtjambi_qt5_12_8.diff
```

Make a build directory outside the qt source directory to avoid polluting the source
if something goes wrong.

```
~/> mkdir build-qt-5.6.2
~/> cd build-qt-5.6.2
```

Configure the build settings.  Skip Qt3D, I couldn't compile it because of a missing
dependency on zlib.  I'm sure there are workarounds, but its just not needed.
Many other modules could likely be skipped as well, but peeling them
off and rebuilding jambi takes an eternity.  It's not worth the effort.
Some exploration into other opengl options, like Angle might be worth while.

```
~/build-qt-5.6.2> ~/qt-src-5.6.2/configure -opensource -release -nomake tests -nomake examples -opengl dynamic -plugin-manifests -skip qt3d
```
    
Build and install.  Note, the install step is super important.  There is a lot
of header monkeying during install and jambi requires them to be setup properly
to build.

```
~/build-qt-5.6.2> nmake
~/build-qt-5.6.2> nmake install
```

### Build Jambi

```
~/qtjambi5> ant all
```

Jars will be in ~/qtjambi5 and ~/qtjambi5/build
