# Issues with Spring Data REST

## JsonSchema

0. Arrays of associations (uris) are not allowed. See my pull request.
0. Any unknown object like `OffsetTime` has a `$ref` attribute, even though it is a `string`.

## Hal+Json

0. Any unknown object, even though it has a Serializer that returns a String, like `OffsetTime`,
   returns something like `{content: 'the actual serialized value'}`. Workaround: `@JsonUnwrapped`.
   
# Guidelines
## Dates and Times

To circumvent above issues, do not use any `Offset...` or `Zoned...` date classes. Instead either
only use `Local...` or the old `Date`. When using the first, only use UTC dates and times.