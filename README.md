# `Smart Lamp Application (Android Only)`

This application is designed for Android devices running Android version 11 or higher. The
application is equipped with voice recognition, allowing users to issue voice commands to turn the
lamp on or off. Additionally, this application also implements wake word detection, where you need
to speak out the key phrase to activate it, similar to Siri and Alexa. There are 3 basic features
available for commands including on/off, set timer and set colour for the lamps. Moreover, the
application is integrated with manual control for the lamp, enabling users to operate it from a
distance using their hands.

**For more details of the project:**
```
https://github.com/HazmiHazim/Smart-Lamp-Project
```

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

- Open Data Analysis

```
Open data analysis
```

## `License`
```
MIT License

Copyright (c) 2024 Hazmi Hazim Bin Zairul Fuaad

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```