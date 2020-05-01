# Android Template

Latest version: https://git.ita.chalmers.se/courses/dit341/group-00-android (public Github [mirror](https://github.com/dit341/group-00-android))

This [Android](https://www.android.com/) templates contains a basic Android app with two activities and demonstrates a HTTP request to the Camel backend server.

## Requirements

* [Server](https://git.ita.chalmers.se/courses/dit341/group-00-web/tree/master/server) backend running on `http://localhost:3000`
* [Android Studio](https://developer.android.com/studio) (v3.4)
  * Considerable bandwidth required for downloading Android Studio (~1GB), extra components (~700MB), project dependencies (~200MB) and a emulator system image (~1.1GB).

## Project setup

1. Clone the template repository

    ```bash
    # Clone repository
    git clone git@git.ita.chalmers.se:courses/dit341/group-00-android.git

    # Change into the directory
    cd group-00-android
    ```

2. In Android Studio, *Open an existing Android Studio project* and select the template directory `group-00-android`
3. Wait for the first build to finish, which might take a while (few minutes) as Gradle downloads lots of dependencies.
4. Run the app by selecting *Run* -> *Run 'Run app'*
5. In the new window *Select Deployment Target*, create a new virtual device (emulator). Choose the *Nexus 5X* phone with API Level 28 (Android 9.0). Downloading this new system image might take a while.
6. Clicking *Ok* will automatically launch the device, deploy the app (as packaged apk), and start the main activity of the app.

Checkout [Run apps on a hardware device](https://developer.android.com/studio/run/device) to run your app on your Android device.

## Project structure

* *manifests* - Contains the AndroidManifest.xml. This file has all the definitions (of Activities etc.)
* *java* - Contains all the Java files. There is a folder for the actual App, and two test folders.
* *res* - Contains the resources. The *layout* sub-folders contains the xml UI definitions. *values* contains value definitions (e.g., strings).

Notice that the physical directory structure is slightly different than this
Android Studio view.

Creating an Activity, Service, etc. in Android Studio (right-click on the root or java folder -> *New* -> *Activity* -> *Empty Acitivity*) automatically creates the UI file, the Java file, and the corresponding definition in the manifest.

## HTTP Requests

Clicking the Get Camels button in the main activity performs an HTTP GET request to the camel backend. It uses the URL defined in *res/values/urls.xml*. By default, it uses the IP address *10.0.2.2*. This is the IP address pointing from the Android Emulator to the machine running it (basically, the localhost on your computer).

If you have your app running on an actual device, you will have to have both the device and your computer running in the same network and point the URL to the computer IP. Additionally, you will have to disable any firewall rules that block external HTTP request to your HTTP port (e.g., 3000). This does *not* work within eduroam/NOMAD.

## onClick Listeners

In Android, buttons are connected to methods using so-called *Listeners*. If you open any of the UI XML definitions (e.g., *res/layour/acitivity_main.xml*) and click on a button, you can assign a method name to the *onClick* property in the *Attributes* tab. This will then execute the method with the same name in the Activity's Java file whenever the button is clicked. The method needs to have return parameter *void* and an input parameter *View view*.

## Testing

1. Right-click the `test` directory and select *Run 'Run unit tests''*
2. Right-click the `androidTest` directory and select *Run 'Run instrumented tests''*

You can also select different run profiles via the menu *Run* -> *Run...*

## Debugging

1. Set a breakpoint in Android Studio
2. Run the app by selecting *Run* -> *Debug 'app'*

Read more in the [Android debug docs](https://developer.android.com/studio/debug) or try the [Android debugger tutorial](https://codelabs.developers.google.com/codelabs/android-training-using-debugger)
