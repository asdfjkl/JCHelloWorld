# Java Card - Hello World

It's kinda tricky to set up all the tooling to build a simple JavaCard project. 
Fortunately, it got way easier thanks to the excellent (GlobalPlatformPro)[https://github.com/martinpaljak/GlobalPlatformPro]
and (ant-javacard)[https://github.com/martinpaljak/ant-javacard].

This repo is a simple HelloWorld for JavaCard, including all the tooling. It is assumed that you work in typical Linux environment (e.g. Ubuntu 22.04). These are the steps to build your first applet, install it on a JavaCard, and communicate with the applet.

## Clone & Setup

1. clone Project, install Eclipse
   for example in Ubuntu, that's `snap install eclipse --classic`
2. add JavaCard libs to directory (see below)   
2. in the project directory change the path in the file `.classpath`
3. in the project directory change the name of the project in the file `.project` to something you like. Or leave it as JCHelloWorld for now.
4. connect a reader, put on the card, start pcsc-daemon with
   `service pcscd restart`, and test the card with `pcsc_scan`. The card should show up.

## Install JavaCard libs

* go to [Oracle](https://www.oracle.com/java/technologies/javacard-sdk-downloads.html) download load the JavaCard Development Kit. 
  I suggest JavaCard 3.0.4 which should work with any JavaCard that you can buy on the open market.
  Unzipping, there should be a folder with jar's named `ant-contrib-1.xx.jar`,
  `api_classic.jar`, and more. Copy those files in the project directory to
  `JCHelloWorld/extlibs/jc304_kit/lib`. Or just copy the whole JavaCard SDK
  folder to `extlibs/jc304_kit`
* or go to [this page](https://github.com/martinpaljak/oracle_javacard_sdks) and
  download the appropriate JavaCard Development Kit there

## Setup Projectin Eclipse and Change Compiler Settings

* Start Eclipse, then 
    `File -> Import -> Existing Projects into Workspace`
     and select the path /.../JCHelloWorld, then `Finish`
* If there are linker errors, go to `Project -> Properties -> Java Build Path
  -> Libraries` and there
  * remove all libs
  * `Add External JARs...` -> select all JARs from `extlib/jc304_kit/lib`
  * add here also all proprietary libs if you have them (i.e. Smartcafe, JCOP)
* build `CAP`: right-click on `build.xml` (modify with build.xml with your own AID and other settings), 
  then `RunAs -> 1) Ant Build`.
* put applet on card: right-click on `build.xml -> RunAs -> 2) Ant Build`,
  the order should be a) buildcap b) install
* alternatively for the last step: manually use gp.jar (cf. `install+personalize.sh`) to install the `CAP`:
  Open terminal, change to `/.../tools` and then
  `java -jar tools/gp.jar -install cap/hw.cap` or `java -jar tools/gp.jar -reinstall cap/hw.cap`
  to overwrite
  
## Compiler Settings

If you get the followring error:

`[compile] error: Source option 1.5 is no longer supported. Use 1.6 or later.`

when executing `build.xml` and compiling, your JDK likely does not match.
See [the matrix here](https://github.com/martinpaljak/ant-javacard/wiki/Version-compatibility)

You then need to tell Eclipse as well as the build script to use another JDK.
For example for JavaCard 3.0.x, we can install OpenJDK 11 (see matrix).

`sudo apt-get install openjdk-11-jdk openjdk-11-demo openjdk-11-doc openjdk-11-jre-headless openjdk-11-source`

Then
1) in Eclipse, we change the global JDK/JRE to 11: Eclipse -> Window -> Preferences -> Java -> Installed JRE's
Then Add -> Standard VM -> set JRE Home to installed JDK. E.g. on Ubuntu that's `/usr/lib/jvm/java-1.11.0-openjdk-amd64`
Finish and close all dialogs.

2) Right-Click on build.xml -> RunAs -> External Tools Configuration. Then Click the Tab JRE, and change to Separate JRE and 
select Java-11-openjdk (the one you've installed before). The `CAP` should build now.


## Test the Applet

Install python3, then install pyscard
* `sudo apt install python3-pyscard`
* change to `/.../tools`, then run `python3 ping.py`. It should return 90 00.
