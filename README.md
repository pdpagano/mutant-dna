# DNA REST API

API made with java and Spring Boot, that determines if a DNA belongs to a human or a mutant.

## Getting Started
First of all, you need to have Java (at least 1.8), git to clone this repo and the last Maven for compiling it. And last but not least, MongoDB on your local machine (if you want to have the database there) or an account on a DBaaS like MongoDB Atlas.


## Installation & Running

Move to the folder where you are going download the source code and execute this git command:
```bash
git clone https://github.com/pdpagano/mutant-dna.git
```
This will leave you with a folder called "dna" with the code inside of it. Next, you have to configure your database. For that go to:

```
/[your-root-path]/dna/src/main/resources
```
In this folder you will find three *.properties files: 

* application.properties: here you configure the used profile.
* application-local.properties: api path and local mongodb config.
* application-prod.properties: api path and DBaaS mongodb config.

Once you have configured the profile and the mongo related things.
You can install the API by running this:

```bash
##in dna folder
mvn clean install
```
And finally to get the api to run, you need to perform the next Maven command:
```bash
mvn spring-boot:run
```

## API Resources
The API has a UI that documents it, implemented with Swagger. You can access it here: [Swagger-UI](https://dnachecker.cfapps.io/api/v1/swagger-ui.html#/).

If you have the API running in your local machine, the UI will be in [Local-Swagger-UI](http://localhost:8080/api/v1/swagger-ui.html#/)

There you will see two endpoint:
* **POST** **/dnachains/mutant**

  Determines if a given DNA belongs to a human or a mutant.
* **GET** **/dnachains/stats**

  Returns statistics about the different DNA that have been analysed.

For more extensive details please refer to [DNA REST API Docs](https://dnachecker.cfapps.io/api/v1/swagger-ui.html#/)

## Authors
* **Pablo Pagano**

## License
This project is licensed under [Apache License Version 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Acknowledgments
To all Spring's programmers that make our life easier
