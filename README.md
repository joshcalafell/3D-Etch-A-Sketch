3d-Etch-A-Sketch
================

Navigation
-----------
[3d-Etch-A-Sketch](#3d-etch-a-sketch) |
[Abstract](#abstract) |
[Screenshot](#screenshot) |
[Team](#team) |
[Requirements](#requirements) |
[Folder Structure](#folder-structure) |
[Configuration](#configuration) |
[Building the program](#building-the-program) |
[Running the Program](#running-the-program) |
[Contact](#contact) |
[Licenses](#licenses) |
[TODO](#todo)

Abstract
========

This program acts like a "3D Etch-a-sketch in space essentially. I can't think of a better way to describe it at the moment. You are in some 3D space, and you can lay down blocks or erase them anywhere you move (forwards, backwards, up down, left, or right @ 1 block size unit at a time). This was a project for CS390A-Fundamentals of Games Programming @ MSUDenver, taught by Professor Jerry Shultz. 

Screenshot
----------
![Picture](http://rabbitfighter.net/wp-content/uploads/2013/11/3DEtchASketchInAction.png)

Team
----------------
<ul>
<li>Joshua Michael Waggoner</li>
<li>Dylan Otto Krider</li>
</ul>

Requirements
------------
<ul>
<li> Java vers 1.7+ </l1>
<li> Gradle vers 2.0+ (for installation help go to <a href="https://gradle.org/">Gradle's home page</a>)</li>
</ul>

Configuration
-------------
This program requires Slick2D and LWJGL vers 2.9.3 (Light Weight Java Gaming Library) libraries as well as natives for Windows, Linux, and OSX that come with it. As per our Gradle build program, these files must be in the correct folders in the project structure or the program will fail. Fortunately, all you need to do is clone this repository to get the project in the proper form.

Folder Structure
----------------
<pre>
.
├── build.gradle
├── libs
│   ├── jar
│   │   ├── lwjgl.jar
│   │   ├── lwjgl_util.jar
│   │   └── slick.jar
│   └── natives
│       ├── linux
│       │   ├── libjinput-linux64.so
│       │   ├── libjinput-linux.so
│       │   ├── liblwjgl64.so
│       │   ├── liblwjgl.so
│       │   ├── libopenal64.so
│       │   └── libopenal.so
│       ├── macosx
│       │   ├── libjinput-osx.dylib
│       │   ├── liblwjgl.dylib
│       │   └── openal.dylib
│       └── windows
│           ├── jinput-dx8_64.dll
│           ├── jinput-dx8.dll
│           ├── jinput-raw_64.dll
│           ├── jinput-raw.dll
│           ├── lwjgl64.dll
│           ├── lwjgl.dll
│           ├── OpenAL32.dll
│           └── OpenAL64.dll
├── README.md
├── res
│   └── images
│       └── future.png
└── src
    └── main
        └── java
            └── org
                └── gradle
                    └── rabbitfighter
                        └── game
                            ├── Basic.java
                            ├── Ex6.java
                            └── Triple.java

15 directories, 26 files
</pre>

Building the Program
--------------------
<ol>
<li>Navigate into the project directory. From the project directory, run <code>gradle build</code>.</li>
</ol>

Running the Program
-------------------
<ol>
<li>Run the program by typing <code>gradle runJar</code> in the root project directory</li>
<li>Enjoy!</li>
</ol>

Contact
-------
<table>
  <tr>
    <th>Name</th>
    <th>Email</th>		
    <th>Twitter</th>
  </tr>
  <tr>
    <td>Joshua Michael Waggoner</td>
    <td>rabbitfighter@cryptolab</td>		
    <td><a href="https://twitter.com/rabbitfighter81">@rabbitfighter81</a></td>
  </tr>
  <tr>
    <td>Dylan Otto Krider</td>
    <td>dkrider@comcast.net</td>		
    <td><a href="https://twitter.com/dokrider">@dokrider</a></td>
  </tr>
</table>

Licences
---------
<ul>
<li>LWJGL sources and binaries: Copyright (c) 2002-2007 Lightweight Java Game Library Project</li>
<li>All other: CCO Licence (See LICENCE-CCO)</li>
</ul>

TODO
----
This game is a work in progress. We hope you have fun, learn something, or at least kill some time. We will add updates, and clean up code as time allows. For now it is working though. :)




