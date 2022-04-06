# Gatling Corp Scala interview project

Thank you for applying to Gatling Corp. We will use these small exercises in our hiring process.

Please code as you would do in your everyday work.

Please **do not open a pull request with your solution**. We will simply ask you to share access to your repository
before the interview.

## Getting started

Tools:

- You need to install a JDK, version 8 or later (11 or 17 are fine too).
- We recommend using [IntelliJ IDEA](https://www.jetbrains.com/fr-fr/idea/) (the free community edition is completely
  sufficient) as an editor. You will need to install IntelliJ's official Scala plugin.
- We include the `sbtx` script which can be used to build, run, or execute tests (using the SBT build tool), so you
  don't need to install anything else. For instance, running `./sbtx "testOnly io.gatling.SanityCheckTest"` in the
  project directory should successfully run this one test.
- Note that your Scala code will be automatically formatted every time SBT compiles it.

If you are new to Scala, here are some resources to help get you started quickly:

- [A Scala Tutorial for Java Programmers](https://docs.scala-lang.org/tutorials/scala-for-java-programmers.html): a
  selection of the most important things to know if you come from a Java (or similar language) background
- [Tour of Scala](https://docs.scala-lang.org/tour/tour-of-scala.html): an introduction to all the main features of the
  language
- [Homepage for the official documentation](https://docs.scala-lang.org/) (note that we currently use Scala 2, not yet
  Scala 3)

There are of course plenty of other learning resources to be found on the web, these are suggestions.

## Pure Scala

Create a "concat" method which takes 2 `Option[String]`s and concatenate their contents when both exist. A functional
style is expected.

```scala
def concat(opt1: Option[String],
           opt2: Option[String]): Option[String] = ???

concat(Some("foo"), Some("bar")) // Some("foobar")
concat(Some("foo"), None)        // None
concat(None, Some("bar"))        // None
concat(None, None)               // None
```

You can solve this in the file [`src/test/scala/io/gatling/ConcatSpec.scala`](./src/test/io/gatling/ConcatSpec.scala),
and run it in IntelliJ, or in your console:

```console
./sbtx "testOnly io.gatling.ConcatSpec"
```

## Computer Database CLI

The major library you'll need to use is [cats effect](https://typelevel.org/cats-effect/docs/2.x/getting-started). Note
that we currently use cats effect 2, not yet the version 3.

You can find here the basis of the project. Feel free to modify the architecture to your taste.

The goal of this part is to create a command line app that stores and reads data about computers in a file (it's
inspired by our demo website: https://computer-database.gatling.io/computers).

A computer is represented by:
- an id
- a name
- an optional introduced date
- an optional discontinued date

Data is stored in the [computers.json](computers.json) file at the root of the project directory.

### Run the project

You can run the `io.gatling.interview.Main` class from IntelliJ (you will need to modify the run configuration to set
the command-line arguments), or in your console (with all the command-line arguments within the quotes):

```console
./sbtx "run list"
```

This will execute the existing `list` command, which lists all computers in the file.

### Specifications

Add two commands:
- a command to display a single computer (with the ID as parameter)
- a command to add a computer to the file (with the name and optional dates as parameters)
