# Setty
[![Build Status](https://travis-ci.org/huberchrigu/setty-mongo.svg?branch=master)](https://travis-ci.org/huberchrigu/setty-mongo)

"Setty" is a demo application that allows the organization of meeting groups and suggests dates when they
can meet, given all members' calendars.
This repository contains the backend implementation and persists data in MongoDB.

The UI is part of the [hateoas-navigator](https://github.com/huberchrigu/hateoas-navigator) project.

## Setup
Use the Java compiler flag _-parameters_ to support Jackson with annotation-free constructors.

## Issues with Spring Data REST

### JsonSchema

1. Arrays of associations (uris) are not allowed. See my pull request.
2. Any unknown object like `OffsetTime` has a `$ref` attribute, even though it is a `string`.

### Hal+Json

1. Any unknown object, even though it has a Serializer that returns a String, like `OffsetTime`,
   returns something like `{content: 'the actual serialized value'}`. Workaround: `@JsonUnwrapped`.

### Alps
1. No way to add custom descriptors.

### Web

1. `@RepositoryRestController` does not work.