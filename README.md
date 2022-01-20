<img src="readme-images/Groovy-logo.png" height="30" width="30"> <b>Groovy</b>

# I'm README file and will tell about the project
___
Дополнительное занятие. REST API/ Groovy


## Acknowledgements
___
[Alexei Kuznetsov](https://github.com/kadehar)

[Maintenance and technical support website https://reqres.in/](https://reqres.in/)


## About home page
___
Реализовать для старых тестов (reqres.in) модели с использованием 
Lombok и попробовать силы в проверках с Groovy.


## Steps
___
1. Created a structure of the project: 
directories and basic files .gitignore, build.gradle and README.md. 
Transferred class `ReqresTests` from lesson 17.  
2. Added specifications.
3. Added models.
4. Added lombok.
5. Added the search by regular expressions and groovy method findAll.


## What's new
___
### Rest-Assured
1. Rest-Assured features
   1. We can concatenate objects `RequestSpecification`
     ```
    public static RequestSpecification requestSpec =
    RestAssured
    .with()
    .baseUri("https://reqres.in")
    .basePath("/api")
    .log().all();
   
    public static RequestSpecification requestSpecAddContentType =
            requestSpec.contentType(ContentType.JSON);
     ```
   2. Add parameters to path
      ```
      public final class EndPoints {
         public static final String usersPage2 = users +"?page={numPage}";
         }
      
      .when()
            .pathParams("numPage", 2).get(EndPoints.usersPage2)
      ```
   3. Can specify one specification for all requires:
      `RestAssured.requestSpecification = requestSpec;`
   4. Compare POJO objects
      `assertThat(user, SamePropertyValuesAs.samePropertyValuesAs(userResponse));`


### Groovy. Regular expressions (see Java patterns)
1. Match a path of a log
   1. A path is `https://reqres.in/img/faces/{number}-image.jpg`
   `println "https://reqres.in/img/faces/17-image.jpg" ==~ /^https:[\/]{2}reqres.in[\\/]img[\\/]faces[\/][0-9]{1,}.image.jpg/`
   2. A date is `2022-01-20T00:54:30.683Z`
   `println "2022-01-20T00:54:30.683Z" ==~ /^202[2-5]-[0-1][0-9]-[1-3][0-9]T[0-2][0-9]:[0-5][0-9]:[0-5][0-9][.][0-9]{3}Z/`


## Resources
___
1. [Link to repo](https://github.com/kadehar/qa_guru_lesson_18)
2. [jaxb](https://www.baeldung.com/jaxb)
3. [jaxb](https://github.com/javaee/jaxb-v2)
4. [REST-assured: полезные советы](https://habr.com/ru/post/421005/)


## Miscellaneous
___
1. [How to disable code formatting for some part of the code using comments?](https://stackoverflow.com/questions/3375307/how-to-disable-code-formatting-for-some-part-of-the-code-using-comments)
```
// @formatter:off
...
// @formatter:on
```
2. POJO (англ. Plain Old Java Object)
3. [IDEA.Groovy Interactive Console](https://www.jetbrains.com/help/idea/launching-groovy-interaction-console.html)





