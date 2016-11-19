# TerraInveditDroid

A simple project to edit the player's inventory in pocket-Terraria.

## Requirements

* A rooted Android device running 4.0+
* A compatible "root app", preferably [SuperSU 2.46](https://download.chainfire.eu/696/supersu/)
* "Mount samespace separation" turned off
* Stock Android (this app is known not working for users with XPosed framework installed)
* [Busybox](https://play.google.com/store/apps/details?id=stericson.busybox) (this is the most important, it'll render your device incompatible if you don't install this)

> WARNING! BEFORE YOU DO ANYTHING, PLEASE BACKUP YOUR PLAYER FILE!

## FAQ

Q: I have XPosed framework installed / I have modified the kernel / I have a newer samsung device. Will it work?  
A: There's a very low chance that it will, but most likely not. I don't know why, it's just not working.

Q: What "root app" do you recommend?  
A: I have [SuperSU 2.46](https://download.chainfire.eu/696/supersu/) installed.

Q: Why do I need "mount namespace separation" turned off?  
A: If you have it turned on, the application will have no access to Terraria's save folder.

Q: What is "Busybox"?  
A: tl;dr: it's an utility binary that contains programs. It's needed, because some devices have outdated, or even non-functional binaries installed by default, and that may cause undefined behavior, so updating those binaries is recommended for most devices.
You can get the installer from the [Play Store](https://play.google.com/store/apps/details?id=stericson.busybox).
> NOTE: Read the instructions before you install it! If you're not careful, you can brick your device!

Q: I get a similar error:  
```
java.lang.RuntimeException: Unable to start activity ComponentInfo{MarcusD.TerraInvedit/MarcusD.TerraInvedit.InveditActivity}:  
java.lang.ArrayIndexOutOfBoundsException­: length=0; index=89
```
A: Geez, that's very old! Install the newest version!

## Notes

> This application utilizes [Chainfire's libsuperuser](https://github.com/Chainfire/libsuperuser)


# Downloads
- [APK](https://github.com/MarcuzD/TerraInveditDroid/blob/master/bin/TerraInveditDroid.apk?raw=true)
