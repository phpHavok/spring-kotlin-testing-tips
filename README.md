Types:

A = General design/testing strategy and tips

B = Specific tricks to use when you run into issues



Private trick for data models (confidence = 5, type = B)

Encapsulation

Information hiding / don’t expose things you don’t need

Kotlin generates getters and setters which you never call





Use recursive comparison (confidence = 2-3, type = B)

Triggers the getters for each field

Maybe equals() doesn’t?





Slots when you need to access data that’s just being passed to another function (confidence = 5, type = B)





Jackson constructor with single private field (bug?) (confidence = 5, type = B)

Enable flag on object mapper (lookup later)





Sentinel values for ultra thin slices (confidence = 5, type = B)





Repository ITs to test data migrations (confidence = 5, type = B)





Keep behavior/data together (type = A)





100% coverage doesn’t mean bug free (type = A)

James Kopel – layers of code

Your runtime works is what you mean

Be aware of what is possible with your code

Impossible to prove code is bug free in general





Unit test mock everything outside your unit (assume they work completely) (type = A)





IT test only mock things outside your code (type = A)

Mock services you call

Mock code that calls your service (Mock MVC to initialize a REST request to your controller)

Mock framework (Spring) is okay because of IoC (inversion of control, framework calls you). You might not be able to artificially create all the scenarios a framework may call you in an IT without mocking it. RequestContextHolder.getAttributes can technically return null, but I’ve never seen that happen before

Seed real/local database with seed data if you have a local DB 
