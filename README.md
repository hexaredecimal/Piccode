# PiccodeScript

> A simple interpreted, functional programming language.

<div align="center">

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

function main() = IO.println("Hello, world")

main()
```

###### Factorial

```js
import std.io

function fact(x=1) = 
  when x {
    is 0 -> 1
    is 1 -> 1
    else -> x * fact(x - 1)
  }
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
Compiler.compile("function zero() = 0")
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
- Lua  - The simplicity
- Rust - The flexibility
- SML  - Functional programming concepts






