Nock API for Scala.js
================================
[nock](https://www.npmjs.com/package/nock) - Nock is an HTTP mocking and expectations library for Node.js.

### Description

Nock can be used to test modules that perform HTTP requests in isolation.

For instance, if a module performs HTTP requests to a CouchDB server or makes HTTP requests to the Amazon API, you can test that module in isolation.

### Build Requirements

* [SBT v0.13.16](http://www.scala-sbt.org/download.html)


### Build/publish the SDK locally

```bash
 $ sbt clean publish-local
```

### Running the tests

Before running the tests the first time, you must ensure the npm packages are installed:

```bash
$ npm install
```

Then you can run the tests:

```bash
$ sbt test
```

### Examples

#### On your test, you can setup your mocking object like this:

```scala
import io.scalajs.npm.nock._
import scala.scalajs.js

val couchdb = Nock("http://myapp.iriscouch.com")
    .get("/users/1")
    .reply(200, js.Dictionary(
      "_id" -> "123ABC",
      "_rev" -> "946B7D1C",
      "username" -> "pgte",
      "email" -> "pedro.teixeira@gmail.com"
    ))
```

#### The request hostname can be a string or a RegExp.

Using a string:

```scala
import io.scalajs.nodejs.console
import io.scalajs.npm.nock._
import scala.scalajs.js

val scope = Nock("http://www.example.com")
    .get("/resource")
    .reply(200, "domain matched")

if (!scope.isDone()) {
    console.info("active mocks: %j", scope.activeMocks())
    console.info("pending mocks: %j", scope.pendingMocks())
}
```

Using a RegExp:

```scala
import io.scalajs.nodejs.console
import io.scalajs.npm.nock._
import scala.scalajs.js
import scala.scalajs.js.RegExp

val scope = Nock(RegExp("/example\\.com/"))
    .get("/resource")
    .reply(200, "domain matched")

if (!scope.isDone()) {
    console.info("active mocks: %j", scope.activeMocks())
    console.info("pending mocks: %j", scope.pendingMocks())
}
```

Using a function:

```scala
import io.scalajs.npm.nock._

val scope = Nock("http://www.example.com")
    .get((uri: String) => uri.indexOf("cats") >= 0)
    .reply(200, "path using function matched")
```

#### You can specify the request body to be matched as the second argument to the get, post, put or delete specifications like this:

```scala
import io.scalajs.npm.nock._
import scala.scalajs.js

val scope = Nock("http://myapp.iriscouch.com")
    .post("/users", js.Dictionary(
      "username" -> "pgte",
      "email" -> "pedro.teixeira@gmail.com"
    ))
    .reply(201, js.Dictionary(
      "ok" -> true,
      "id" -> "123ABC",
      "rev" -> "946B7D1C"
    ))
```

#### Optional Requests

By default every mocked request is expected to be made exactly once, and until it is it'll appear in scope.pendingMocks(), 
and scope.isDone() will return false (see expectations). In many cases this is fine, but in some (especially cross-test 
setup code) it's useful to be able to mock a request that may or may not happen. You can do this with optionally(). 
Optional requests are consumed just like normal ones once matched, but they do not appear in pendingMocks(), and isDone() 
will return true for scopes with only optional requests pending.

```scala
import io.scalajs.npm.nock._
import scala.scalajs.js

val example = Nock("http://example.com")
example.pendingMocks() // []
example.get("/pathA").reply(200)
example.pendingMocks() // ["GET http://example.com:80/path"]

// ...After a request to example.com/pathA:
example.pendingMocks() // []

example.get("/pathB").optionally().reply(200)
example.pendingMocks() // []
```

#### Allow unmocked requests on a mocked hostname

If you need some request on the same host name to be mocked and some others to really go through the HTTP stack, 
you can use the allowUnmocked option like this:

```scala
import io.scalajs.npm.nock._
import scala.scalajs.js

val options = js.Dictionary("allowUnmocked" -> true)
val scope = Nock("http://my.existing.service.com", options)
    .get("/my/url")
    .reply(200, "OK!")
    

 // GET /my/url => goes through nock
 // GET /other/url => actually makes request to the server    
```

Bear in mind that, when applying {allowUnmocked: true} if the request is made to the real server, no interceptor is removed.

#### Specifying request query string

Nock understands query strings. Instead of placing the entire URL, you can specify the query part as an object:

```scala
import io.scalajs.npm.nock._
import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined 

Nock("http://example.com")
    .get("/users")
    .query(new Person(name = "pedro", surname = "teixeira"))
    .reply(200, new Items(results = js.Array(new Item(id = "pgte"))))

@ScalaJSDefined
class Person(val name: String, val surname: String) extends js.Object

@ScalaJSDefined
class Items(val results: js.Array[Item]) extends js.Object

@ScalaJSDefined
class Item(val id: String) extends js.Object
```

Nock supports array-style/object-style query parameters. The encoding format matches with request module.

```scala
import io.scalajs.npm.nock._
import scala.scalajs.js

Nock("http://example.com")
    .get("/users")
    .query(js.Dictionary(
      "names" -> js.Array("alice", "bob"),
      "tags" -> js.Dictionary(
        "alice" -> js.Array("admin", "tester"),
        "bob" -> js.Array("tester")
      )))
    .reply(200, js.Dictionary("results" -> js.Array(js.Dictionary("id" -> "pgte"))))
```

```scala
import io.scalajs.npm.nock._
import scala.scalajs.js

Nock("http://www.google.com")
    .get("/cat-poems")
    .replyWithError(js.Dictionary("message" -> "something awful happened", "code" -> "AWFUL_ERROR"))
```

### Artifacts and Resolvers

To add the `Nock` binding to your project, add the following to your build.sbt:  

```sbt
libraryDependencies += "io.scalajs.npm" %%% "nock" % "0.4.1"
```

Optionally, you may add the Sonatype Repository resolver:

```sbt   
resolvers += Resolver.sonatypeRepo("releases") 
```
