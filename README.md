# Setty
[![Build Status](https://travis-ci.org/huberchrigu/setty-mongo.svg?branch=master)](https://travis-ci.org/huberchrigu/setty-mongo)

"Setty" is a demo application that allows the organization of meeting groups and suggests dates when they
can meet, given all members' calendars.
This repository contains the backend implementation and persists data in MongoDB.

The UI is part of the [hateoas-navigator](https://github.com/huberchrigu/hateoas-navigator) project.

# How to get started

_The following steps are required due to this bug: [DATAREST-1096](https://jira.spring.io/browse/DATAREST-1096)_

0. Clone the customized Spring Data REST repository: https://github.com/huberchrigu/spring-data-rest
0. Run `mvn install` in its root
