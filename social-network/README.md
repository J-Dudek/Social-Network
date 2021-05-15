# Module FRONT

Module front de l'application Social-network. Dans le cas d'une execution du projet en local il est nécessaire de vérifier que la variables ```REACT_APP_SERVER_URL``` dans le fichier [.env](https://github.com/J-Dudek/Social-Network/blob/main/social-network/.env) est bien définie sur ```http://localhost:8080``` .

Cette application s'authentifie auprés de auth0 afin d'obtenir un token permettant de contacter le back-office.

Utilisation de ```react-routeur```ainsi qu'une personnalisation du routage afin de rendre les routes protégées. Ainsi, la seule route public, ne nécessitant aucun enregistrement est ```/```
On retrouve donc :
```javascript

<QueryClientProvider client={queryClient}>
      <div id="app" className="d-flex flex-column h-100">
        <HeaderComponent />
        <div className="ui center aligned grid">
          <div>
            <Switch>
              <Route exact path="/" component={Home} />
              <ProtectedRoute path="/profile" exact component={Profile} />
              <ProtectedRoute path="/user/:id" component={UserPage} />
              <ProtectedRoute
                path="/myfriendpost"
                exact
                component={MyFriendPost}
              />
              <ProtectedRoute path="/users" exact component={User} />
              <ProtectedRoute path="/posts" exact component={Post} />
              <Route component={FourOfour} />
            </Switch>
          </div>
        </div>
        <Footer />
      </div>
    </QueryClientProvider>

```
Utilisatio de ```hooks``` .

Utilisation de [Semantic UI React](https://react.semantic-ui.com/)

Utilisation de [React Query](https://react-query.tanstack.com/)

Utilisation de  [axios](https://yarnpkg.com/package/axios)

Utilisation de [Typescript](https://www.typescriptlang.org/)

Utilisation de [Auth0](https://auth0.com/)

Tests with ```@testing-library/react``` 

Un ```dockerfile```est également présent, ce qui permet de génerer une image docker du projet assez facilement. Nous avons ici choisi de passer la commande ```yarn start```et non ```yarn build```afin d'avoir une version assez verbeuse en console mais nous permettant d'avoir un maximum d'information.

Dans le cas d'un livrable de production cela n'aurait pas été le cas. 

Nous remarquons d'ailleurs une erreur remontée en console à l'heure actuelle, après recherche elle est la conséquence d'un composant de chez semantic-ui. Cette erreur est relevée uniquement car nous utilisons le ```<React.StrictMode>```.

De même que pour la partie Back nous avons décidé de laisser toutes les clés en clair afin qu'un utilisateur lambda puisse se servir de cette application. Le compte auth0 associé à cette application estprésent uniquement pour cette application et n'est pas provisionné.


# Getting Started with Create React App

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

## Available Scripts

In the project directory, you can run:

### `yarn start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.\
You will also see any lint errors in the console.

### `yarn test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `yarn build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `yarn eject`

**Note: this is a one-way operation. Once you `eject`, you can’t go back!**

If you aren’t satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you’re on your own.

You don’t have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn’t feel obligated to use this feature. However we understand that this tool wouldn’t be useful if you couldn’t customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).
