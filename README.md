# `Smart Lamp Application (Android Only)`

This application is designed for Android devices running Android version 11 or higher. The
application is equipped with voice recognition, allowing users to issue voice commands to turn the
lamp on or off. Additionally, the application is integrated with manual control for the lamp,
enabling users to operate it from a distance using their hands. This application also implements
wake word detection, similar to Siri and Alexa.

## `Available Features`

- Voice Recognition
- Lamp Controller (ON/OFF)
- Timer
- Colour Editor
- Data Analysis

## `Library Used`

- PocketSphinx Android

```
implementation files('libs/pocketsphinx-android-5prealpha-release.aar')
```

- Volley

```
implementation 'com.android.volley:volley:1.2.1'
```

- HoloColorPicker

```
implementation 'com.larswerkman:HoloColorPicker:1.5'
```

- MPAndroidChart

```
implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'
```

## `Wake Word Key Phrase`

To enable voice commands, you need to call 'Phoenix.' Then, you need to say the word 'commands' to
make Phoenix ready to accept commands from you.

- These two words are compulsory to activate Phoenix for commands:

```
Hey Phoenix
```

```
Commands
```

## `Supported Commands`

- Turning On/Off Lamp

```
Turn on lamp <1|2|3>
```

```
Turn off lamp <1|2|3>
```

```
Turn on all lamps
```

```
Turn off all lamps
```

- Setting Lamp Timer

```
Set timer to <1 to 60> <seconds|minutes|hours> for lamp <1|2|3>
```

- Setting Lamp Colour

```
Set <red|yellow|green|blue|crimson|lavender|pink|indigo> colour for lamp <1|2|3>
```