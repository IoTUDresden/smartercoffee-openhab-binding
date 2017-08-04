SmarterCoffee OpenHAB Binding
===

* See: [How to create openHAB binding](http://docs.openhab.org/developers/development/bindings.html#setup-and-run-the-binding)

---

### How to include `smartercoffee` binding  
1. Copy the `org.openhab.binding.smartercoffee` to `<openhab2-workspace>/git/openhab2-addons/addons/binding/`
2. Add the binding in *pom.xml*  (`<openhab2-workspace>/git/openhab2-addons/addons/binding/pom.xml`)

```xml 
   </modules>
        ...
        <module>org.openhab.binding.smartercoffee</module>
   </modules>
```  

Or you can import the folder into workspace using eclipse

3. Add the binding in *feature.xml* (`<openhab2-workspace>/git/openhab2-addons/addons/features/openhab-addons/src/main/feature/feature.xml`)

```xml
	<feature name="openhab-binding-smartercoffee" description="SmarterCoffee Machine Binding" version="${project.version}">
		<feature>openhab-runtime-base</feature>
		<bundle start-level="80">mvn:org.openhab.binding/org.openhab.binding.smartercoffee/${project.version}</bundle>
	</feature>
```


