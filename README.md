# Gatling Corp Scala interview project

Thank you for applying to Gatling Corp. We will use these small exercises in our hiring process.  

Please code as you would do in your everyday work.

## Pure Scala

Create a "concat" method which takes 2 Option[String] and concatenate their content when both exist.

```scala
def concat(opt1: Option[String],
           opt2: Option[String]): Option[String] = ???

concat(Some("foo"), Some("bar")) // Some("foobar")
concat(Some("foo"), None)        // None
concat(None, Some("bar"))        // None
concat(None, None)               // None
```

You can solve this in the file `scalaExercises.sc`, which can be easily run with IntelliJ, or with Scala Metals on your favorite editor (ie: https://scalameta.org/metals/docs/editors/vscode/)

## Computer Database API

The goal of this part is to create a CRUD API of computers (just like our demo website: https://computer-database.gatling.io/computers).

The major libraries you'll need to use are:
- finch (https://finagle.github.io/finch/)
- cats effect (https://typelevel.org/cats-effect/docs/2.x/getting-started)
- circe (https://circe.github.io/circe/)

You can find here the basis of the project, feel free to modify the architecture to your taste.

A computer is represented by:
- an id
- a name
- an optional introduced date
- an optional discontinued date

### Run the project

./sbtx run

There is already a first endpoint accessible at http://localhost:8086/computers

### Specifications

The API is not connected to a database, all read and insert are mocked.

- add an API to insert a computer
- modify the json output of a computer:
  * it should not display null introduced or discontinued dates
  * it should display the lifetime of a computer (period between discontinued and introduced)
- link every computer to a company (a company can manufacture multiple computers)
- add refined (https://github.com/fthomas/refined), the computer name should be at least 5 characters long
