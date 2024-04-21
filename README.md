# android-graph-sensors
This is an Android app for Graph gyro & amp accelerometer sensor.
Developped by me, inpired by the internet, for my geophysics project at Sorbonne University

# Introduction.
The Earth is subject to constant movement. This movement can be attributed to natural events such as tectonics, winds, ocean waves and sometimes to man-made activities such as train movements.
With the number of seismic events recorded over the years, and the damage caused by these movements, signal detection has become important.  

The equipment used in the professional world for detecting seismic signals is expensive, even if its accuracy is high.  
The possibility of having an easy, always available and less costly means of recording seismic signals. 
This research project will attempt to answer all these questions. We present a solution to the acquisition of digitized seismic signal that is dated on an Android device using the MEMS accelerometer found in the android smartphone industry.

# State of the art.
This report describes the technology adopted to acquire a seismic signal via an Android device (smartphone or tablet). This sets us on the path that makes seismic acquisition accessible from a smartphone, i.e. to anyone, at no additional cost, and in the ideal case, without even needing to download an application (in the case of google). 

Building a digital Android platform for detecting seismic acquisition with geophones as exemplified by (Qimao Zhang et al., 2020).
Google has attempted to detect signs of earthquakes using accelerometers on Android phones. In simple terms, the cell phone becomes a seismograph that combines with millions of others to form a global detection network.

# The earthquake detector has saved many lives in the Philippines.
Launched in 2020, Android's earthquake detector is already starting to prove its worth. After the Philippines was hit by a 6.7-magnitude earthquake that left enormous damage, as shown in Figure 1, many users received the notification to move quickly to safety. As a result, many lives were saved.

![Capture d'écran 2024-04-21 181331](https://github.com/oubaaid/Android-Accelerometer/assets/98980894/2051975d-b4c1-40e9-954d-f20c3c14977c)

Earthquake damage in the Philippines in 2020

Earthquake detectors for Android released in 2020 have already begun in earnest. A magnitude 6.7 earthquake struck the Philippines, prompting many users to evacuate quickly. The result: many lives were saved.  More and more of our everyday devices are multiplying their ingenuity to save our lives. Last year, Google launched earthquake detection on Android using various sensors built into smartphones. When the ground begins to shake, users will be warned to evacuate. So says the Mountain View company.
The question is whether the technology is effective. Judging by the results obtained in the Philippines. On July 24, 2021, a magnitude 6.7 earthquake struck the capital. Since then, several residents have shared screenshots of the notifications they received. This also reminds us of the good reflexes we use when we're at risk, such as falling to the ground or sheltering under a table. Today, all smartphones are equipped with a miniature accelerometer capable of detecting seismic signals. When the phone detects what it thinks is an earthquake, it sends a signal at "the speed of light" to the earthquake detection server with the approximate location of the earthquake. The server then combines seismic and GNSS wave acquisition information from several phones to determine whether an earthquake is occurring.

Following initial tests in California, the seismic detector is now available in the USA, Greece, New Zealand, Turkey, the Philippines, Kazakhstan, Kyrgyzstan, Tajikistan, Turkmenistan and Uzbekistan.

# MEMS smartphone accelerometer.
When a smartphone's accelerometer moves, the moving rod oscillates to one side or the other. By measuring the variation in electrical capacitance between the rods, we can deduce the direction and extent of the movement.
These different sensors provide very precise and comprehensive information on all the vibrations and movements undergone by the phone.
As this measurement only takes place in one plane, three accelerometers are needed to measure displacements in different directions (longitudinal, transverse, vertical). The data is then transmitted to a microprocessor, which reconstructs the movement.

![Capture d'écran 2024-04-21 181824](https://github.com/oubaaid/Android-Accelerometer/assets/98980894/1d06db9b-d921-4ac3-b66c-ffe8291c7e45)

![Capture d'écran 2024-04-21 182010](https://github.com/oubaaid/Android-Accelerometer/assets/98980894/787ffd6a-8f3a-4890-b2bd-35b5abb3ae61)

Specs of the used smartphone's accelerometer

# Examples of similar software.
![Capture d'écran 2024-04-21 182132](https://github.com/oubaaid/Android-Accelerometer/assets/98980894/fd79fc3f-d8f3-4c29-b5c9-9fc63f4b7391)


# Application development for seismic acquisition using an Android device.
Android.Studio was chosen over any other platform because of the availability of the necessary libraries on the Internet, as well as solutions to the problems we may encounter.
This project involves using the MEMS accelerometer found in smartphones to acquire a dated, digitized seismic signal. The data from this signal will be saved as a CSV file.

![Capture d'écran 2024-04-21 182505](https://github.com/oubaaid/Android-Accelerometer/assets/98980894/6a392f18-ec27-4cea-a77d-6a7b78eee33b)

Android Studio + Java were used for this project.

# Exporting and displaying results.
![Capture d'écran 2024-04-21 182728](https://github.com/oubaaid/Android-Accelerometer/assets/98980894/d03b369d-a261-4117-9746-c0ab16ecab58)

Absolute acceleration is calculated from x,y and z data.
x,y and z data : Acc.Absolute = √(X² + Y² + Z²).

![Capture d'écran 2024-04-21 182800](https://github.com/oubaaid/Android-Accelerometer/assets/98980894/9557dc89-c3eb-48b3-b03e-614868ac09ab)

![Capture d'écran 2024-04-21 182827](https://github.com/oubaaid/Android-Accelerometer/assets/98980894/a0767269-e02e-49e7-97e5-17f897fb41ed)


# Conclusion and outlook.
The evolution of smartphones over the years has led to the development of sophisticated embedded systems, integrating hardware and software functionalities with the potential for diverse applications. Using object-oriented programming (object-programming) on Android Studio, we demonstrated one of the various applications that could be made using advanced smartphone functionality.

By exploiting the MEMS accelerometers found in smartphones, we were able to measure vibrations in x, y and z directions, and with a csv file containing absolute acceleration and time for each recording.

MEMS accelerometers, which are not overly precise, proved useful for recording signals. Given their price, further research in this area represents a real breakthrough in signal measurement.

Although the geophysical instruments currently used to measure seismic signals are more advanced, this research has shown that, with the evolution of technology, signal measurement instruments could soon be readily available.
