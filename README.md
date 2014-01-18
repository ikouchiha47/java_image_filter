java_image_filter
=================
I have tried to keep it as simple as possible.
Basically there are two packages, filters and effects.
All the primary and compound effects are placed inside the filters package.
I have divided and grouped basic effects under a few common geners. For example : two seperate classes 
Brightness and Contrast are placed in Adjustments.java. Similarly Colors.java has classes like
Colorize, Saturation, Vobrance, Vigenetting. 
The values passed to the parameter in Sepia has to be negative so that the image exposure doesnot increase.
The Gaussian Blur and saturation algorithm has been adapated from wikipedia.
The Layer Effects like Add, Overlay and etc. inside BlendComposite has also been taken from wikipedia.
The Curves.java is used for color curves adjustments. It takes 5 inputs, the starting and ending points
and the two intermediate points. the starting point must have the first element of its array as 0 ,
i.e. [0,5] [0,0],[0,62] and so o. Similarly the ending point must have its second element value as 255.
The first element describes the input or 'x' co-ordinates value and the second is the output or 'y' co-ordinates
value. These points are taken directly from the curves adjustements done in photoshop or gimp. 
P.S.: some manual adjustments to the points may be necessary. If the exact values of the control points are
taken, the tuneUpWithControls() method is to be used, and if arbitraru points are taken from the curve then
tuneUpWithPoints() is used. The 5 th parameter is the channel parameter [red,green,blue] . 1 denotes on 
'selected' 0 denotes 'ignore'. [0,0,1], indiacates the green channel is on.The tuneUp() function is adummy function 
and returns null.
The source image pointer is passed to the contructor of most of the classes.
The function name tuneUp() has been used in most claases like the Adjustments, Colors, and converTo() has been used
in the Desaturate.java.
All these must become trasperent if on looks at the abstract class of each java file.
The BlendComposite is analogous to AlphaComposite. Using BlekndComposite one csn achieve the layer effects like
Overlay, Add, Substract, Darken,SoftLight etc. 
Some compound effects like Amber, SandBlast, Speckel, Crossprocess, Potra etc. are a combination of different
simple filters that have been discussed above.

The package effects is quite simple. It has an Effects interface, which has the name of the compound effects.
If someone wants to add a new effect, register/mention the effect in the interface and hen add a class to the
filters package and implement th interface.
The SwingExample.java just creates an interface for the whole project.
I am looking forward to implement the camera photo film effects like Agfa, Velvia, Provia.
There is also a bug existance with the project: once you choose a file with jFileChoser and u need to select 
another new image, then you can defintely select it with the jFileChooser but, the left side jPanel won't be
repainted with the new image. For this we need ti reopen the application.
