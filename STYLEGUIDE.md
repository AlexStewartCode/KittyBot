KittyBot Style Guide
---

#### Indenting
Tabs are used so that individual developers can configure their own width settings, and they won't be enforced on others working on the project. 

`
// No indent!
	// One indent!
		// Two indents!
`

#### Braces
When braces are present, Allman style is used which means that braces are on the next line. Braces should be used to prevent scope confusion. Omitting braces on single-line statements is fine so long as the statement is clear.

`
if(something)
{
	System.out.println("This");
	System.out.println("has");
	System.out.println("braces!");
}

if(something)
	System.out.println("No braces!");
`

#### Naming
Class names and function names are in PascalCase! Variable names are in camelCase! 

`
public class MyClass
{
	private String myString;

	public function MyFuction()
	{
		// ...
	}
}
`

#### Ternary Operator
Don't.

#### <more to come>