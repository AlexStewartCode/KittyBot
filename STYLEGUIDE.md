KittyBot Style Guide
---

#### Indenting
Tabs are used so that individual developers can configure their own indent width settings that won't be enforced on others working on the project. 

```java
// I'm not indented!
{
	// I'm indented with one tab!
}
```

#### Braces
When braces are present, Allman style is used meaning that braces start on the next line. Braces are used to prevent scope confusion. Omitting braces on single-line statements is fine if the situation is clear.

```java
if(foo)
{
	System.out.println("This");
	System.out.println("has");
	System.out.println("braces!");
}

if(bar)
	System.out.println("No braces!");
```

#### Naming
Class names and function names are `PascalCase`. Variable names are `camelCase`. 

```java
public class MyClass
{
	private Foo myVariable;

	public function MyFuction()
	{
		// ...
	}
}
```

#### Ternary Operator (the ?:)
Don't.

#### <more to come>