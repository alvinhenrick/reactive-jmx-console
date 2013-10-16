'use strict';

define "angular", ["webjars!angular-locale_en-us.js"], ->
  angular

require ["angular", "app", "./controllers", "./routes", "./directives", "./filters", "./services"], (angular) ->
  angular.bootstrap(document, ["jmxconsole"])
