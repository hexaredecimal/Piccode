# PiccodeScript

> A simple interpreted, functional programming language.

<div align="center">

<img src="./images/mascot.png" width="50%" />

[![Java CI with Maven](https://github.com/Glimmr-Lang/PiccodeScript/actions/workflows/maven.yml/badge.svg)](https://github.com/Glimmr-Lang/PiccodeScript/actions/workflows/maven.yml)
 
</div>


## Why?

PiccodeScript started as a dsl for an image editor (Picasso Code) I was building, and overtime 
I fell in love with the simple syntax and I wanted to make to its own project, possibly for 
embedding in my java programs or for general scripting. 

## Installation

You can download binary releases from the Official website or visit the file server to find previous versions. 

##### Latest

- [Official Release](http://piccodescript.fly.dev/)

##### Previous releases

- [Release Server](https://picasso-releases.fly.dev/piccodescript/)


## Build From Source

- Step 1 - Clone the repo.
```sh
$ git clone git@github.com:Glimmr-Lang/PiccodeScript.git
```

- Step 2 - Enter inside the dir.
```sh
$ cd PiccodeScript
```

- Step 3 - Run the build script.
```sh
$ ./all.sh
```

## Documentation
- [Standard library reference](https://piccodescriptdocs.fly.dev/)

# Example

###### Hello World 

```js
import std.io

main :: () = IO::println("Hello, world")
```

###### Factorial

```js
import std.io

fact :: (x=1) = 
  when x {
    is 0 -> 1
    is 1 -> 1
    else -> x * fact(x - 1)
  }

result := fact(5)
IO::println(result)
```

## Embedding API

To use the language in your project simply add it your `pom.xml` file as a dependency:


```xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>

<dependency>
	<groupId>com.github.Glimmr-Lang</groupId>
	<artifactId>PiccodeScript</artifactId>
	<version>Tag</version>
</dependency>
```
#### Note replace `Tag` with a release tag from the [releases](https://github.com/Glimmr-Lang/PiccodeScript/releases) page.

 and then add the following code to your solution:

```java
Compiler
	.compile("zero :: () = 0")
	.execute(null);
```

If you want to get a list of AST nodes do this:

```java
Compiler.prepareGlobalScope();
List<Ast> nodes = Compiler.parse(input);

// Then you can execute them or do what you want
for (var expr: nodes) {
	expr.execute();
}
```

To add symbols to the current scope you can do the following: 

```java
Context.top.putLocal("hello", new PiccodeString("Hello, world"));

Compiler.compile("hello + 20"); // concate hello, world and 20 
```

## Inspired by
- [Lua](https://www.lua.org/) - The simplicity
- [Rust](https://www.rust-lang.org/) - The flexibility
- [SML](https://www.smlnj.org/sml.html) - Functional programming concepts
- [Java](https://www.java.com/en/) - Threading model



