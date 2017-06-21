# Fluk
A library for making Kotlin more functional friendly.

Most of what this project contains comes from my experience with F#, C# and Java and the tools I'd like to have at my 
disposal when I'm programming.

## Types

### Result

This type represents a value which can be a success of a failure. It can be used to create a [Railway Oriented 
Programming (ROP)](http://fsharpforfunandprofit.com/rop/) application or library. Result can be used to implement the 
either monad which is a more convenient way of using ROP in some cases. See usage below.

The meaning of Success and Failure is dependent on the context and the application. There is a lot of controversy 
about these names and the type parameters [Result discussion for F#](https://github.com/fsharp/fslang-design/issues/49). 
I like this definition so I use it.

See discriminated union [Result<'TSuccess,'TFailure>](http://fsharpforfunandprofit.com/posts/recipe-part1/)

## Usage (TODO)

## The name
Fluk is called after a project I have for the same purpose in F# named Flux. Flux + Kotlin => Fluk.

## See also:
 - http://fsharp.org/
 - http://fsharpforfunandprofit.com/
 - http://fsharpforfunandprofit.com/rop/
 - https://msdn.microsoft.com/en-us/visualfsharpdocs/conceptual/visual-fsharp
