![platforms](https://img.shields.io/badge/platforms-macos|linux|windows-lightgrey.svg)
[![MIT License](https://img.shields.io/badge/license-MIT-brightgreen.svg)](https://github.com/deboer-tim/icpctools/blob/master/LICENSE.txt)
[![pipeline status](https://gitlab.com/icpctools/icpctools/badges/master/pipeline.svg)](https://gitlab.com/icpctools/icpctools/commits/master)

Welcome to the ICPC Tools!
==========================

The ICPC Tools are a set of tools to support running programming contests. These tools were built to support the
International Collegiate Programming Contest (ICPC) World Finals and have been used there for many years, but
the intention is that they are usable for local and regional contests as well. For more information on the ICPC,
please go to https://icpc.baylor.edu.

Each of the ICPC tools can be used individually, or together in any combination. They are all designed to support
the REST-based Contest API as defined by the Competitive Learning Initiative (CLI): https://clics.ecs.baylor.edu/index.php/Contest_API.


## The ICPC Tools

Tool | Description
--- | ---
Balloon Utility | Manages and prints which teams to award a balloon
Contest Data Server (CDS) | Single-point URL services for accessing all contest data
Presentation | Animated display of scoreboards or other contest data
Presentation Admin | Remote administration of multiple presentations (requires the CDS)
Resolver | Animated reveal of final contest results
Coach View | Ability to remotely see the webcam and desktop of a team
Problem Set Editor | Generate/Edit YAML descriptions of problem sets for input to CLICS-compatible Contest Control Systems.
Contest Utilities | A variety of useful contest-related utilities: event feed validation, scoreboard comparison, floor map generators, and more!


## Contest Control System Compatibility

The ICPC Tools are built to work with any Contest Control System (CCS) that supports the REST-based [Contest API](https://clics.ecs.baylor.edu/index.php/Contest_API). To be more specific, the only part of the Contest API that is
strictly required is the [event feed](https://clics.ecs.baylor.edu/index.php/Contest_API#Event_feed) and any file
references that the feed refers to. If your CCS correctly supports the event feed, then all of the ICPC Tools will
work even if the rest of the API is not implemented.

The one exception to this is the CDS' and contest utility support for comparing scoreboards - to compare a scoreboard,
the CCS must have one, of course.

The CCS that has been most thoroughly proven to work with the ICPC Tools (through its role as primary CCS at the
2018 and 2019 ICPC World Finals) is [DOMjudge](https://www.domjudge.org).

Most of the core ICPC Tools still retain support for the deprecated [XML Event Feed](https://clics.ecs.baylor.edu/index.php?title=Event_Feed_2016). If your CCS supports the XML Event Feed as
specified at this link then the tools should still work, albeit with some missing function. Both [Kattis](https://www.kattis.com) and some versions of [PC^2](https://pc2.ecs.csus.edu) have support for the XML feed and
work well with the ICPC Tools.


## Contributing

The ICPC Tools are developed, tested, and maintained by a group of ICPC volunteers. Bug reports, feature requests,
and even just knowing what worked or didn't for your contest are always appreciated.

To become a committer you must have a history of high quality bug reports, PRs, and be approved by Tim,
John Clevenger, and Jeff Donahoo.


## License

All of the tools are provided under the included MIT license and are "Free as in Beer". We welcome you to use
and enjoy them, but if you ever run into anyone who has contributed to them - Tim, John, Nicky, Sam, Troy, etc.
we would greatly appreciate it if you'd buy us a beer, a stroopwafel, or some other suitable token!

All ICPC Tools are Copyright © by the ICPC.