# Gatling Corp Scala interview project

Thank you for applying to Gatling Corp. We will use these small exercises in our hiring process.

Please code as you would do in your everyday work.

## Pure Scala

Create a "concat" method which takes 2 `Option[String]`s and concatenate their contents when both exist.

```scala
def concat(opt1: Option[String],
           opt2: Option[String]): Option[String] = ???

concat(Some("foo"), Some("bar")) // Some("foobar")
concat(Some("foo"), None)        // None
concat(None, Some("bar"))        // None
concat(None, None)               // None
```

You can solve this in the file [`src/test/scala/io/gatling/ConcatSpec.scala`](./src/test/io/gatling/ConcatSpec.scala),
which can be easily run with IntelliJ, or with Scala Metals on your favorite editor
(e.g. https://scalameta.org/metals/docs/editors/vscode/).

Or in your console:

```console
./sbtx "testOnly io.gatling.ConcatSpec"
```

## Computer Database CLI

The goal of this part is to create a command line app that stores and reads data about computers in a file (it's
inspired by our demo website: https://computer-database.gatling.io/computers).

The major libraries you'll need to use are:
- [cats effect](https://typelevel.org/cats-effect/docs/2.x/getting-started)
- [circe](https://circe.github.io/circe/)

You can find here the basis of the project, feel free to modify the architecture to your taste.

A computer is represented by:
- an id
- a name
- an optional introduced date
- an optional discontinued date

Data is stored in the [computers.json](computers.json) file at the root of the project directory. 

### Run the project

```console
./sbtx run list
```

This will execute the `list` command, which lists all computers in the file.

### Specifications

Add two commands:
- a command to display a single computer (with the ID as parameter)
- a command to add a computer to the file (with the name and optional dates as parameters)
