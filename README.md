# Membership Manager

This project has been started to create a simple web application which allows memberships/subscriptions to a thing to be managed online. The overaching aim is to make this a simple as possible to use and run hence the plan to make it compile down to an executable jar with Jetty embedded within it and database/store which will be just a file system.

In future json api's might be exposed to provide a membership checking service for other applications, possibly using oAuth for security.

Checkout the Milestones and issues for the current plan and thiings to contribute too and the project for details of where we are at.

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

To allow the application to send emails you will need to create a profiles.clj file in the root directory, the file should look something like this

```clojure
{:email {:env {
              :enabled true
              :email-user "blah"
              :email-password "blah"
              :email-host "smtp.gmail.com"
              :email-port 465
              :email-use-ssl true}}}
```

Then run the server with the profile:

     lein with-profile default,email ring server

You can of course add any profiles you like to overide properties in the default profile for instance. For more information on using profiles see [leiningen profiiles]: https://github.com/technomancy/leiningen/blob/stable/doc/PROFILES.md

## License

Copyright © 2017 Akoolla Digital Solutions
