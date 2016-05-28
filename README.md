project: Monthly Wage Calculation System

version: Version 1.0 beta

bug report to: rui.yang at aalto.fi

## Setting
### Mandatory:
- change the "dataSrcPath" to the path that contains the file with salary;
- change and "dataSrcName" to the name of the file with salary.

### Optional:
- read through the other settings, change the value if needed.

## How to use
0.1. make sure the input file is in correct syntax, for instance:

'''
Person Name,Person ID,Date,Start,End
Scott Scala,2,2.3.2014,6:00,14:00
'''

0.2. import the whole project into eclipse;
1. adjust settings (please refer to the Setting section);
2. enable assertions in eclipse; (for how to enable, check [here](http://tutoringcenter.cs.usfca.edu/resources/enabling-assertions-in-eclipse.html));
3. click run, the output will be showed in the console.

TODO: integrate into Java Jersey Framework.