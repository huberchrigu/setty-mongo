# Issues with Spring Data REST

## JsonSchema

0. Arrays of associations (uris) are not allowed. See my pull request.
0. Any unknown object like `OffsetTime` has a `$ref` attribute, even though it is a `string`.

## Hal+Json

0. Any unknown object, even though it has a Serializer that returns a String, like `OffsetTime`,
   returns something like `{content: 'the actual serialized value'}`. Workaround: `@JsonUnwrapped`.

## Alps
0. No way to add custom descriptors.

## Web

0. `@RepositoryRestController` does not work.