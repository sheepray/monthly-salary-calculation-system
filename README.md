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
0. make sure the input file is in correct syntax;
1. import the whole project into eclipse;
2. adjust settings (please refer to the Setting section);
3. enable assertions in eclipse; (for how to enable, check [here](http://tutoringcenter.cs.usfca.edu/resources/enabling-assertions-in-eclipse.html));
4. click run, the output will be showed in the console.

### input data syntax example
- Person Name,Person ID,Date,Start,End
- Scott Scala,2,2.3.2014,6:00,14:00
- Janet Java,1,3.3.2014,9:30,17:00
- Scott Scala,2,3.3.2014,8:15,16:00

TODO: integrate into Java Jersey Framework.