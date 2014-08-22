Colorer
=======

HTML from multiple logs generator

Usage

java -jar colorer.jar -generate number_of_files <= will generate a colorer.xml file that can be filled with information about files to be "colored"
java -jar colorer.jar                           <= will try to load, parse and execute colorer.xml
java -jar colorer.jar  file_name                <= will try to load, parse and execute file_name

colorer.xml example
<colorer>
  <destination>output.html</destination>                     <- where the report is saved
  <dateformat>yyyy-MM-dd HH:mm:ss</dateformat>               <- date format in report
  <file>
    <dateformat>yyyy-MM-dd HH:mm:ss</dateformat>             <- date format in file
    <compressed>true</compressed>                            <- compressed file(gz)
    <color>blue</color>                                      <- color in HTML report
    <path>log.log</path>                                     <- file path
    <from>2014-04-18 06:30:00</from>                         <- lower time limit
    <to>2014-04-18 06:31:00</to>                             <- upper time limit
  </file>                                                    (specified in file date format)
  <file>
    <dateformat>yyyy-MM-dd HH:mm:ss</dateformat>
    <compressed>false</compressed>
    <color>red</color>
    <path>log.log</path>
    <from></from>                                           <- from the beginning of time
    <to></to>                                               <- to the end of time
  </file>
</colorer>

Build command
mvn package 
