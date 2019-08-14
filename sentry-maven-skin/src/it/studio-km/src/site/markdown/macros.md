# Time/Date Formats for Macros


> This section describes the time formats available for the %{TIME:…} and %{LASTTIME:…} macros for the Command Line, the Folder and the File Monitors as well as for all the Alert Actions.

|Format |Description   |
|-------|-----------------------------------------------------------------------   |
|EPOCH |is replaced by the number of seconds that have elapsed since 00:00:00 GMT, Jan 01, 1970.|  
| %%   |is replaced by a single % character.|
| %a   |is replaced by the locale's abbreviated weekday name (Sun, Mon, Tue, Wed, Thu, Fri or Sat).|
| %A   |is replaced by the locale's full weekday name (Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, or Saturday).|
| %b   |is replaced by the locale's abbreviated month name (Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov or Dec).|
| %B   |is replaced by the locale's full month name (January, February, March, April, May, June, July, August, September, October, November or December). |
| %c   |is replaced by the locale's date and time representation (mm/dd/yy hh:mm:ss).                                  |
| %C   |is replaced by the century (a year divided by 100 and truncated to an integer) as a decimal number (00-99). |
| %d   |is replaced by the day of the month as a decimal number (01-31).                                |
| %D   |is equivalent to ```%m/%d/%y``` (mm/dd/yy).   |
| %e   |is replaced by the day of month as a decimal number (1-31); single digits are preceded by a blank.|
| %h   |is replaced by the locale's abbreviated name of the month.   |
| %H   |is replaced by the hour (24-hour clock) as a decimal number (00-23).|
| %I   |is replaced by the hour (12-hour clock) as a decimal number (01-12).|
| %j   |is replaced by the day of the year as a decimal number (001-366).|
| %k   |is replaced by the hour (24-hour clock) as a decimal number (0-23); single digits are preceded by a blank.|
| %l   |is replaced by the hour (12-hour clock) as a decimal number (1-12); single digits are preceded by a blank.|
| %m   |is replaced by the month as a decimal number (01-12).|
| %M   |is replaced by the minute as a decimal number (00-59).|
| %n   |is replaced by a newline.|
| %p   |is replaced by locale's equivalent of AM (ante meridiem) or PM (post meridiem) as appropriate.|
| %r   |is equivalent to ```%I:%M:%S %p``` (hh:mm:ss AM/PM).|
| %R   |is equivalent to ```%H:%M``` (hh:mm).|
| %S   |is replaced by the second as a decimal number (00-60).|
| %t   |is replaced by a tab.|
| %T   |is equivalent to ```%H:%M:%S``` (hh:mm:ss).|
| %u   |is replaced by the weekday (Monday as the first day of the week) as a decimal number (1-7).|
| %U   |is replaced by the week number of the year (the first Sunday as the first day of week 1) as a decimal number (00-53).|
| %V   |is replaced by the week number of the year (the first Monday as the first day of week 1) as a decimal number (00-53).|
| %w   |is replaced by the weekday (Sunday as the first day of the week) as a decimal number (0-6).|
| %W  |is replaced by the week number of the year (the first Monday as the first day of week 1) as a decimal number (00-53).|
| %x  |is replaced by the locale's date representation.  In the default locale, it is equivalent to ```%m/%d/%y``` (mm/dd/yy).|
| %X  |is replaced by the locale's time representation.  In the default locale, it is equivalent to ```%H:%M:%S``` (hh:mm:ss).|
| %y  |is replaced by the year without century as a decimal number (00-99).|
| %Y  |is replaced by the year with century as a decimal number.|
| %Z  |is replaced by the time zone name.|
| %Ec |is replaced by an alternative appropriate date and time representation .|
| %EC |is replaced by the name of the base year (period) in the locale's alternative representation.|
| %Ex |is replaced by the locale's alternative date representation.|
| %EX |is replaced by the locale's alternative time representation.|
| %Ey |is equivalent to the offset from %EC (year only) in the locale's alternative representation.|
| %EY |is replaced by the alternative representation of the year in full.|
| %Od |is replaced by the day of the month using the locale's alternative numeric symbols.|
| %Oe |is equivalent to %Od.|
| %OH |is replaced by the hour (24-hour clock) using the locale's alternative numeric symbols.|
| %OI |is replaced by the hour (12-hour clock) using the locale's alternative numeric symbols.|
| %Om |is replaced by the month using the locale's alternative numeric symbols.|
| %OM |is replaced by the minutes using the locale's alternative numeric symbols.|
| %OS |is replaced by the seconds using the locale's alternative numeric symbols.|
| %OU |is replaced by the week of the year (Sunday as the first day of the week) using the locale's alternative numeric symbols.|
| %Ow |is replaced by the day of week (Sunday=0) using the locale's alternative numeric symbols.|
| %OW |is replaced by the week of the year (Monday as the first day of the week) using the locale's alternative numeric symbols.|
| %Oy |is replaced by the year (offset from %C) in the locale's alternative representation and using the locale's alternative numeric symbols.|
