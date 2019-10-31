# Ubifeed Mobile application

### Summary of the mobile application

This app allows visitors of sports venues to order food and drinks via smartphone. The order then gets deliverd to a selected pickup station.
<br><br>

### Technical information

The app is built with the Ionic Framework, version 5.4.4. Additionally to this mobile app, there is a webserver with a MySQL Database and a REST-API in Java.
<br><br>

### Structure of the app

In the `src/app` directory of the project are the general files needed for the app to work:

```
app-routing.module.ts
app.component.html
app.component.ts
app.module.ts
```

The `app-routing.module.ts` file is the root routing component for the Angular router.
`app.component.html` contains the router-outlet.
The `app.component.ts` initializes the app and `app.module.ts` imports features needed for the app.

For every screen in the mobile application there is a page directory in the `src/app/` directory. Each screen consists of multiple documents within the corresponding subdirectory, e.g. the `login` page has these components:
```
login.module.ts
login.page.html
login.page.scss
login.page.spec.ts
login.page.ts
```

The `login.module.ts` file contains data related to the routing (lazy loading).

The `login.page.html` and the `login.page.scss` files define the structure and the design for the screen.

The `login.page.ts` file contains all the functionality of the screen.


