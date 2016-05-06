# CoCoME Cloud JEE Service-Adapter

This repository contains the service-adapter implementing the evolution
scenario adding a service-adapter.

## Prerequisites

- Running Glassfish 4.x
- A database supported by JPA (e.g., PostgreSQL)

## Compilation

Before compilation you must copy the `settings.xml.template` file to
`settings.xml` and adjust the properties defined in the XML file.

The properties correspond with values of your running Glassfish instance.
In a default Glassfish installation the domain is called `domain1` if you
did not provide a different name. However, we recommend to create your own
domain, e.g., `cocome` or `service-adapter`.

## Deployment

Before deployment make sure you have added a data (re)source called `jdbc/CoCoMEDB`.
This can be done on command line or via the Glassfish UI. You need:
- A JDBC connection pool (please consult the Glassfish documentation for that)
- A JDBC resource named `jdbc/CoCoMEDB` using the connection pool you defined

