# CoCoME Cloud JEE Service-Adapter

This repository contains the service-adapter implementing the evolution
scenario adding a service-adapter.

## Prerequisites

- Running Glassfish 4.x
- A database supported by JPA (e.g., PostgreSQL)

## Compilation

Before compilation you must copy the `settings.xml.template` located in
the project root directory file to `settings.xml` and adjust the
properties defined in the XML file.

The properties correspond with values of your running Glassfish
instance. In a default Glassfish installation the domain is called
`domain1` if you did not provide a different name. However, we recommend
to create your own domain, e.g., `cocome` or `service-adapter`.

To build the service-adapter with Maven type in the project root
directory:

`mvn -s settings.xml compile`

If the compilation is successful you can proceed and type

`mvn -s settings.xml package`

to produce deployable packages. This last step can be omitted when you
choose to directly deploy your build with Maven. However, it is helpful
to make sure packaging works before trying to deploy. Therefore, we
recommend to test packaging before deploying.

## Deployment

Before you can deploy the service-adapter make sure you have added a
data (re)source called `jdbc/CoCoMEDB`. This can be done on command line
or via the Glassfish UI. You need:
- A JDBC connection pool (please consult the Glassfish documentation)
- A JDBC resource named `jdbc/CoCoMEDB` using the connection pool you
  defined

You can directly deploy them via the Cargo Maven plug-in with

`mvn -s settings.xml install`

Alternatively, you can deploy the application via `asadmin` or the
Glassfish administration interface.

