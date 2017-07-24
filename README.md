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


