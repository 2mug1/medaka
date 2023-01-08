# medaka

## Getting Started

### Installation

`pom.xml`
```xml
<repository>
  <id>github</id>
  <name>medaka</name>
  <url>https://maven.pkg.github.com/2mug1/medaka</url>
</repository>

<dependency>
  <groupId>net.iamtakagi</groupId>
  <artifactId>medaka</artifactId>
  <version>1.0.2</version>
  <scope>compile</scope>
</dependency>
```

`build.gradle`
```gradle
repositories {
  maven (url = "https://maven.pkg.github.com/2mug1/medaka")
}
dependencies {
  implementation("net.iamtakagi:medaka:1.0.2")
}
```

## LICENSE
[MIT License](./LICENSE) (© 2022 iamtakagi)
