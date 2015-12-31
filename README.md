# TerraInveditDroid

A simple project to edit the player's inventory in pocket-Terraria.

## Requirements

* A rooted Android device running 4.0.1+
* A compatible "root app", preferable [SuperSU 2.46](https://download.chainfire.eu/696/supersu/)
* "Mount samespace separation" turned off
* Multiuser turned off in your "root app"
* Stock Android *unmodified* kernel (this app is known not working for users with XPosed framework installed)

> WARNING! BEFORE YOU DO ANYTHING, PLAEASE DO A BACKUP OF YOUR CHARACTER!

## FAQ

Q: What is the required Android version?
A: You'll need to use 4.0.1 or above. Sorry 2.3 users :(

Q: I have XPosed framework installed / I have modified the kernel / I have a newer samsung device. Will it work?
A: Sadly, no. I don't know why, because I can't test it without the required hardware. It's just not working.

Q: What "root app" do you recommend?
A: I have [SuperSU 2.46](https://download.chainfire.eu/696/supersu/). It's the best for 4.x

Q: Why do I need "mount namespace separation" turned off?  
A: For some devices Android doesn't allow the app to access the `/data/data/` folder.

Q: Why does this app complain about "Busybox"? What is it?
A: It's an utility binary that is usually better than the system's binaries, so it's recommended that you install it from [the market](market://stericson.busybox) Just press "Install", and it'll install it.
> NOTE: It has a VERY, VERY LOW chance that it'll softbrick your device, so be careful. Read the insttructions for your device!

Q: I get a similar error:
```
java.lang.RuntimeException: Unable to start activity ComponentInfo{MarcusD.TerraInvedit/Marcu­sD.TerraInvedit.InveditActivity}: java.lang.ArrayIndexOutOfBoundsException­: length=0; index=89
```
A: You forgot to do something that I mentioned above, or you have a samsung device. Get a normal device! Like a Nexus :P

## Notes

> This wont work on devices with SEAndroid (like galaxy s4), because SEAndroid is way too Security Enhanced :P

> If you do a backup, please make sure that all .player files have this format: `playername-##########.player` (where # is a digit, and yes, that's 10digits)


# Downloads
- [APK](https://github.com/MarcuzD/TerraInveditDroid/blob/master/bin/TerraInveditDroid.apk?raw=true)
- [EXE](https://github.com/MarcuzD/MarcusD.TerraInvedit)
