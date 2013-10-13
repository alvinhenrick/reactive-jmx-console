'use strict'

class Routes
  constructor: ($routeProvider) ->
    $routeProvider
      .when '/view1',
        templateUrl: 'partials/partial1.html'
        controller: 'MyCtrl1'
      .when '/view2',
        templateUrl: 'partials/partial2.html'
        controller: 'MyCtrl2'
      .otherwise
        redirectTo: '/index.html'

define ['app'], (app) -> app.config ['$routeProvider', Routes]